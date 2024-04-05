package Succubus.cards;

import Succubus.actions.DamageFollowupAction;
import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.powers.CharmPower;
import Succubus.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.SashWhip;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class TailWhip extends AbstractEasyCard {
    public final static String ID = makeID(TailWhip.class.getSimpleName());

    public TailWhip() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageFollowupAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT, false, mon -> {
            if (mon.lastDamageTaken > 0 && mon instanceof AbstractMonster) {
                Wiz.applyToEnemyTop((AbstractMonster) mon, new CharmPower(mon, mon.lastDamageTaken));
            }
        }));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
    }

    @Override
    public String cardArtCopy() {
        return SashWhip.ID;
    }

}