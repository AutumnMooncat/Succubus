package Succubus.cards;

import Succubus.actions.DamageFollowupAction;
import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.stances.ShadowStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.FearNoEvil;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class SoulSeeker extends AbstractEasyCard {
    public final static String ID = makeID(SoulSeeker.class.getSimpleName());

    public SoulSeeker() {
        super(ID, 3, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 20;
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageFollowupAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.POISON, false, mon -> {
            if (mon.isDying || mon.currentHealth <= 0) {
                addToTop(new GainEnergyAction(magicNumber));
            }
        }));
        addToBot(new ChangeStanceAction(new ShadowStance()));
    }

    @Override
    public void upp() {
        upgradeDamage(5);
    }

    @Override
    public String cardArtCopy() {
        return FearNoEvil.ID;
    }

}