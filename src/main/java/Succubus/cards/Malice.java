package Succubus.cards;

import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.powers.MalicePower;
import Succubus.util.Wiz;
import com.megacrit.cardcrawl.cards.green.Nightmare;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class Malice extends AbstractEasyCard {
    public final static String ID = makeID(Malice.class.getSimpleName());

    public Malice() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new MalicePower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }

    @Override
    public String cardArtCopy() {
        return Nightmare.ID;
    }
}