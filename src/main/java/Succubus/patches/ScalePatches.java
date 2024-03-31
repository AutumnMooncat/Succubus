package Succubus.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class ScalePatches {
    @SpirePatch2(clz = AbstractCreature.class, method = SpirePatch.CLASS)
    public static class ScaleField {
        public static SpireField<Float> finalScale = new SpireField<>(() -> 1f);
    }

    @SpirePatch2(clz = AbstractCreature.class, method = "loadAnimation")
    public static class GetScale {
        static float initialScale;

        @SpirePrefixPatch
        public static void getInitial(AbstractCreature __instance, float scale) {
            initialScale = scale;
        }

        @SpirePostfixPatch
        public static void getFinal(AbstractCreature __instance, float scale) {
            ScaleField.finalScale.set(__instance, initialScale/scale);
        }
    }
}
