package Succubus.cards;

import Succubus.actions.HasteAction;
import Succubus.cards.abstracts.AbstractEasyCard;
import com.megacrit.cardcrawl.cards.green.Burst;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class Haste extends AbstractEasyCard {
    public final static String ID = makeID(Haste.class.getSimpleName());

    public Haste() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HasteAction(magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }

    @Override
    public String cardArtCopy() {
        return Burst.ID;
    }
}