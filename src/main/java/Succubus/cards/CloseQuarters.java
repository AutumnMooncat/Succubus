package Succubus.cards;

import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.powers.CharmPower;
import Succubus.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.green.HeelHook;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class CloseQuarters extends AbstractEasyCard {
    public final static String ID = makeID(CloseQuarters.class.getSimpleName());

    public CloseQuarters() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 12;
        baseMagicNumber = magicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        Wiz.applyToEnemy(m, new CharmPower(m, magicNumber));
    }

    @Override
    public void upp() {
        upgradeDamage(3);
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return HeelHook.ID;
    }

}