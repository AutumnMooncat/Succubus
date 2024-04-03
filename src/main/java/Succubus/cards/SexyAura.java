package Succubus.cards;

import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.powers.SexyAuraPower;
import Succubus.util.Wiz;
import com.megacrit.cardcrawl.cards.purple.Indignation;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class SexyAura extends AbstractEasyCard {
    public final static String ID = makeID(SexyAura.class.getSimpleName());

    public SexyAura() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new SexyAuraPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return Indignation.ID;
    }
}