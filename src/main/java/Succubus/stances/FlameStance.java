package Succubus.stances;

import Succubus.MainModfile;
import Succubus.stances.interfaces.FreeCardStance;
import Succubus.util.Wiz;
import Succubus.vfx.ColoredStanceAuraEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.ExhaustEmberEffect;
import com.megacrit.cardcrawl.vfx.combat.FlameParticleEffect;

public class FlameStance extends AbstractStance implements FreeCardStance {
    public static final String STANCE_ID = MainModfile.makeID(FlameStance.class.getSimpleName());
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(STANCE_ID).TEXT;

    public FlameStance() {
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
        CardCrawlGame.sound.play("ATTACK_FIRE");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.FIREBRICK, true));
    }

    @Override
    public boolean isFree(AbstractCard card) {
        return card.type == AbstractCard.CardType.ATTACK;
    }

    @Override
    public void onTrigger(AbstractCard card) {
        if (card.costForTurn > 0) {
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(Wiz.adp(), Wiz.adp(), card.costForTurn, AbstractGameAction.AttackEffect.FIRE));
        } else if (card.cost == -1 && card.energyOnUse > 0) {
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(Wiz.adp(), Wiz.adp(), card.energyOnUse, AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void updateAnimation() {
        particleTimer -= Gdx.graphics.getDeltaTime();
        if (particleTimer <= 0) {
            particleTimer = 0.1f;
            for(int i = 0; i < 4; ++i) {// 21
                AbstractDungeon.effectsQueue.add(new FlameParticleEffect(Wiz.adp().hb.cX, Wiz.adp().hb.cY));
            }

            for(int i = 0; i < 1; ++i) {// 24
                AbstractDungeon.effectsQueue.add(new ExhaustEmberEffect(Wiz.adp().hb.cX, Wiz.adp().hb.cY));
            }
        }

        this.particleTimer2 -= Gdx.graphics.getDeltaTime();// 56
        if (this.particleTimer2 < 0.0F) {// 57
            this.particleTimer2 = MathUtils.random(0.3F, 0.4F);// 58
            AbstractDungeon.effectsQueue.add(new ColoredStanceAuraEffect(Color.FIREBRICK));
        }
    }
}
