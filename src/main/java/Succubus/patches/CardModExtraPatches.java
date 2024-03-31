package Succubus.patches;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class CardModExtraPatches {
    public static AbstractCreature onOtherCardPlayedTarget = null;
    @SpirePatch2(clz = CardModifierPatches.CardModifierOnUseCard.class, method = "Insert")
    public static class GetTarget {
        @SpirePrefixPatch
        public static void plz(AbstractCreature target) {
            onOtherCardPlayedTarget = target;
        }

        @SpirePostfixPatch
        public static void plz() {
            onOtherCardPlayedTarget = null;
        }
    }
}
