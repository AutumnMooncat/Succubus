package Succubus.patches;

import Succubus.stances.interfaces.OnUCAStance;
import Succubus.util.Wiz;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import javassist.CtBehavior;

public class UCAPatches {
    @SpirePatch2(clz = UseCardAction.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class, AbstractCreature.class})
    public static class StanceHook {
        @SpireInsertPatch(locator = Locator.class)
        public static void plz(UseCardAction __instance, AbstractCard card) {
            if (Wiz.adp().stance instanceof OnUCAStance) {
                ((OnUCAStance) Wiz.adp().stance).onUseCardAction(card, __instance);
            }
        }
    }

    public static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher m = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "hand");
            return LineFinder.findInOrder(ctBehavior, m);
        }
    }
}
