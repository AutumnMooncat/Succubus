package Succubus.patches;

import Succubus.powers.interfaces.OnPlayerAttackedPower;
import Succubus.util.Wiz;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

public class OnPlayerAttackedPatches {
    @SpirePatch(clz = AbstractPlayer.class, method = "damage")
    public static class AttackedHook {
        @SpireInsertPatch(locator = Locator.class)
        public static void plz(AbstractPlayer __instance, DamageInfo info) {
            if (info.owner != null) {
                Wiz.forAllMonstersLiving(mon -> {
                    for (AbstractPower p : mon.powers) {
                        if (p instanceof OnPlayerAttackedPower) {
                            ((OnPlayerAttackedPower) p).onPlayerAttacked(info);
                        }
                    }
                });
            }
        }
    }

    public static class Locator extends SpireInsertLocator {

        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher m = new Matcher.MethodCallMatcher(Math.class, "min");
            return LineFinder.findInOrder(ctBehavior, m);
        }
    }
}
