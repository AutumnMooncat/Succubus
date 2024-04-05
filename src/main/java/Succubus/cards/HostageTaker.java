package Succubus.cards;

import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.powers.HostagePower;
import Succubus.util.Wiz;
import com.megacrit.cardcrawl.cards.green.Outmaneuver;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class HostageTaker extends AbstractEasyCard {
    public final static String ID = makeID(HostageTaker.class.getSimpleName());

    public HostageTaker() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF_AND_ENEMY);
        baseBlock = block = 12;
        baseMagicNumber = magicNumber = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        Wiz.applyToEnemy(m, new HostagePower(m, p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeBlock(4);
        upgradeMagicNumber(2);
    }

    @Override
    public String cardArtCopy() {
        return Outmaneuver.ID;
    }
}