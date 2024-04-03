package Succubus.stances;

import Succubus.MainModfile;
import Succubus.stances.interfaces.OnUCAStance;
import Succubus.stances.interfaces.ShaderOnPlayerStance;
import Succubus.util.Wiz;
import Succubus.vfx.ColoredStanceAuraEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.NotStanceCheckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.EmptyStanceEffect;
import com.megacrit.cardcrawl.vfx.combat.SanctityEffect;
import com.megacrit.cardcrawl.vfx.stance.CalmParticleEffect;

import java.nio.charset.StandardCharsets;

public class IllusionStance extends AbstractStance implements ShaderOnPlayerStance, OnUCAStance {
    public static final String STANCE_ID = MainModfile.makeID(IllusionStance.class.getSimpleName());
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(STANCE_ID).TEXT;
    private static final ShaderProgram sp = new ShaderProgram(SpriteBatch.createDefaultShader().getVertexShaderSource(), Gdx.files.internal(MainModfile.makePath("shaders/mirage.frag")).readString(String.valueOf(StandardCharsets.UTF_8)));
    private static final Color COLOR = new Color(0.5f, 0.5f, 0.5f, 1f);

    public IllusionStance() {
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
    public void onUseCardAction(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse) {
            AbstractMonster m = null;
            if (action.target != null) {
                m = (AbstractMonster)action.target;
            }

            for (int i = 0 ; i < 2 ; i++) {
                AbstractCard tmp = card.makeSameInstanceOf();
                AbstractDungeon.player.limbo.addToBottom(tmp);
                tmp.current_x = card.current_x;
                tmp.current_y = card.current_y;
                tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                tmp.target_y = (float)Settings.HEIGHT / 2.0F;
                if (m != null) {
                    tmp.calculateCardDamage(m);
                }

                tmp.purgeOnUse = true;
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
            }

            AbstractDungeon.actionManager.addToTop(new ChangeStanceAction("Neutral"));
            AbstractDungeon.actionManager.addToTop(new NotStanceCheckAction("Neutral", new VFXAction(new EmptyStanceEffect(Wiz.adp().hb.cX, Wiz.adp().hb.cY), 0.1F)));
        }
    }

    @Override
    public void updateAnimation() {
        particleTimer -= Gdx.graphics.getDeltaTime();
        if (particleTimer <= 0) {
            particleTimer = 0.5f;
            for(int i = 0; i < 1; ++i) {// 24
                AbstractDungeon.effectsQueue.add(new CalmParticleEffect());
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
