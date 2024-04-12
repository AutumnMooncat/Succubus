/*
package Succubus.patches;

import Succubus.cards.Strike;
import Succubus.powers.TemptationPower;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireRawPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.annotation.Annotation;

public class TestPatch {
    @SpirePatch2(clz = CardCrawlGame.class, method = SpirePatch.CONSTRUCTOR)
    public static class Test {
        @SpireRawPatch
        public static void test(CtBehavior ctb) {
            try {
                CtClass strike = ctb.getDeclaringClass().getClassPool().get(Strike.class.getName());
                ClassFile cf = strike.getClassFile();
                AnnotationsAttribute attribute = new AnnotationsAttribute(cf.getConstPool(), AnnotationsAttribute.visibleTag);
                Annotation annotation = new Annotation(NoCompendium.class.getName(), cf.getConstPool());
                attribute.setAnnotation(annotation);
                cf.addAttribute(attribute);
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
*/
