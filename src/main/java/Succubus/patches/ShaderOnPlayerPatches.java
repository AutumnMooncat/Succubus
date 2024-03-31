package Succubus.patches;

import Succubus.MainModfile;
import Succubus.stances.interfaces.ShaderOnPlayerStance;
import Succubus.util.ImageHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.Hitbox;
import javassist.CtBehavior;

public class ShaderOnPlayerPatches {
    @SpirePatch2(clz = AbstractPlayer.class, method = "render")
    public static class ShaderTime {
        public static FrameBuffer fb = ImageHelper.createBuffer();
        private static boolean capturing = false;

        @SpireInsertPatch(locator = OnLocator.class)
        public static void on(AbstractPlayer __instance, SpriteBatch sb) {
            capturing = false;
            if (__instance.stance instanceof ShaderOnPlayerStance) {
                capturing = true;
                sb.end();
                ImageHelper.beginBuffer(fb);
                sb.begin();
            }
        }

        @SpireInsertPatch(locator = OffLocator.class)
        public static void off(AbstractPlayer __instance, SpriteBatch sb) {
            if (capturing && __instance.stance instanceof ShaderOnPlayerStance) {
                sb.end();
                fb.end();
                sb.begin();
                draw(sb, ((ShaderOnPlayerStance) __instance.stance).getShader(__instance));
            }
        }

        public static void draw(SpriteBatch sb, ShaderProgram sp) {
            TextureRegion r = ImageHelper.getBufferTexture(fb);
            ShaderProgram origShader = sb.getShader();
            Color origColor = sb.getColor();
            sp.begin();
            sb.setShader(sp);
            sp.setUniformf("x_time", MainModfile.time);
            sb.setColor(Color.WHITE);
            sb.draw(r, 0, 0);
            sb.setShader(origShader);
            sp.end();
            sb.setColor(origColor);
            capturing = false;
        }

        public static class OnLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "renderCorpse");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }

        public static class OffLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(Hitbox.class, "render");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }
}
