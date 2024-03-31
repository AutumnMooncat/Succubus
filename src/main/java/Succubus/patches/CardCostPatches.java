package Succubus.patches;

import Succubus.powers.interfaces.CheatCostPower;
import Succubus.stances.interfaces.FreeCardStance;
import Succubus.util.Wiz;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class CardCostPatches {
    @SpirePatch2(clz = AbstractCard.class, method = "hasEnoughEnergy")
    public static class CardCostPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<?> bePlayable(AbstractCard __instance) {
            for (AbstractPower p : Wiz.adp().powers) {
                if (p instanceof CheatCostPower && ((CheatCostPower) p).canAfford(__instance)) {
                    return SpireReturn.Return(true);
                }
            }
            if (AbstractDungeon.player.stance instanceof FreeCardStance && ((FreeCardStance) AbstractDungeon.player.stance).isFree(__instance)) {
                return SpireReturn.Return(true);
            }
            return SpireReturn.Continue();
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.FieldAccessMatcher(EnergyPanel.class, "totalCount");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "useCard")
    public static class ConsumePowerPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void useSnow(AbstractPlayer __instance, AbstractCard c) {
            if (c.costForTurn > EnergyPanel.getCurrentEnergy() && c.costForTurn > 0) {
                for (AbstractPower p : __instance.powers) {
                    if (p instanceof CheatCostPower) {
                        ((CheatCostPower) p).onActivate(c);
                        break;
                    }
                }
            }
        }
        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(EnergyManager.class, "use");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }

    public static boolean spendCost(AbstractCard card) {
        if (AbstractDungeon.player.stance instanceof FreeCardStance && ((FreeCardStance) AbstractDungeon.player.stance).isFree(card)) {
            ((FreeCardStance) AbstractDungeon.player.stance).onTrigger(card);
            return false;
        }
        return true;
    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "useCard")
    public static class DontSpendEnergy {
        @SpireInstrumentPatch
        public static ExprEditor plz() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(EnergyManager.class.getName()) && m.getMethodName().equals("use")) {
                        m.replace("if("+CardCostPatches.class.getName()+".spendCost(c)) {$_ = $proceed($$);}");
                    }
                }
            };
        }
    }
}
