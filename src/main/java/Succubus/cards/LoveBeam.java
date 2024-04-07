package Succubus.cards;

import Succubus.actions.DoIfAction;
import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.powers.CharmPower;
import Succubus.vfx.ColoredSmallLaserEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.BeamCell;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class LoveBeam extends AbstractEasyCard {
    public final static String ID = makeID(LoveBeam.class.getSimpleName());

    public LoveBeam() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 8;
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
        addToBot(new VFXAction(new ColoredSmallLaserEffect(m.hb.cX, m.hb.cY, p.hb.cX, p.hb.cY, Color.PINK)));
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        addToBot(new DoIfAction(() -> m.hasPower(CharmPower.POWER_ID), () -> {
            addToTop(new DrawCardAction(magicNumber));
            addToTop(new GainEnergyAction(magicNumber));
        }));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDeadOrEscaped() && m.hasPower(CharmPower.POWER_ID)) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                break;
            }
        }
    }

    @Override
    public void upp() {
        upgradeDamage(3);
        //upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return BeamCell.ID;
    }
}