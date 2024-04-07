package Succubus.cards;

import Succubus.actions.AttackDamageRandomEnemyFollowupAction;
import Succubus.cards.abstracts.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.blue.MeteorStrike;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

import static Succubus.MainModfile.makeID;

public class MeteorShower extends AbstractEasyCard {
    public final static String ID = makeID(MeteorShower.class.getSimpleName());

    public MeteorShower() {
        super(ID, 5, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = damage = 10;
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0 ; i < magicNumber ; i++) {
            addToBot(new AttackDamageRandomEnemyFollowupAction(this, AbstractGameAction.AttackEffect.NONE, mon -> {
                addToTop(new WaitAction(0.8F));
                addToTop(new VFXAction(new WeightyImpactEffect(mon.hb.cX, mon.hb.cY)) );
            }));
        }
        addToBot(new GainEnergyAction(magicNumber));
    }

    @Override
    public void upp() {
        upgradeDamage(4);
        //upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return MeteorStrike.ID;
    }
}