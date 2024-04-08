package Succubus.stances;

import Succubus.MainModfile;
import Succubus.stances.interfaces.ShaderOnPlayerStance;
import Succubus.util.Wiz;
import Succubus.vfx.ColoredStanceAuraEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.SanctityEffect;
import com.megacrit.cardcrawl.vfx.stance.DivinityParticleEffect;

import java.nio.charset.StandardCharsets;

public class ShadowStance extends AbstractStance implements ShaderOnPlayerStance {
    public static final String STANCE_ID = MainModfile.makeID(ShadowStance.class.getSimpleName());
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(STANCE_ID).TEXT;
    private static final ShaderProgram sp = new ShaderProgram(SpriteBatch.createDefaultShader().getVertexShaderSource(), Gdx.files.internal(MainModfile.makePath("shaders/shadow.frag")).readString(String.valueOf(StandardCharsets.UTF_8)));
    private static final Color COLOR = new Color(0.2f, 0f, 0.2f, 1f);

    public ShadowStance() {
        this.ID = STANCE_ID;
        this.name = TEXT[0];
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = TEXT[1];
    }

    @Override
    public void onEnterStance() {
        CardCrawlGame.sound.play("APPEAR");
        AbstractDungeon.effectsQueue.add(new SanctityEffect(Wiz.adp().hb.cX, Wiz.adp().hb.cY));
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(COLOR, true));
    }

    @Override
    public void onExitStance() {
        AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(Wiz.adp(), Wiz.adp(), 5));
        /*AbstractDungeon.actionManager.addToBottom(new DoAction(() -> {
            AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(Wiz.adp(), Wiz.adp(), Wiz.adp().currentBlock));
            AbstractDungeon.actionManager.addToTop(new RemoveAllBlockAction(Wiz.adp(), Wiz.adp()));
        }));*/
    }

    @Override
    public void updateAnimation() {
        particleTimer -= Gdx.graphics.getDeltaTime();
        if (particleTimer <= 0) {
            particleTimer = 0.5f;
            for(int i = 0; i < 1; ++i) {// 24
                AbstractDungeon.effectsQueue.add(new DivinityParticleEffect());
            }
        }

        this.particleTimer2 -= Gdx.graphics.getDeltaTime();// 56
        if (this.particleTimer2 < 0.0F) {// 57
            this.particleTimer2 = MathUtils.random(0.3F, 0.4F);// 58
            AbstractDungeon.effectsQueue.add(new ColoredStanceAuraEffect(COLOR));
        }
    }

    @Override
    public ShaderProgram getShader(AbstractPlayer p) {
        return sp;
    }
}
