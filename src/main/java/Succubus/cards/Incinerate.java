package Succubus.cards;

import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.powers.IncineratePower;
import Succubus.util.Wiz;
import com.megacrit.cardcrawl.cards.red.Combust;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class Incinerate extends AbstractEasyCard {
    public final static String ID = makeID(Incinerate.class.getSimpleName());

    public Incinerate() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new IncineratePower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return Combust.ID;
    }
}