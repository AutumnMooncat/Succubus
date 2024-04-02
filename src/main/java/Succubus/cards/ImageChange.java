package Succubus.cards;

import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.powers.ImageChangePower;
import Succubus.util.Wiz;
import com.megacrit.cardcrawl.cards.green.Doppelganger;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class ImageChange extends AbstractEasyCard {
    public final static String ID = makeID(ImageChange.class.getSimpleName());

    public ImageChange() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new ImageChangePower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeBaseCost(0);
    }

    @Override
    public String cardArtCopy() {
        return Doppelganger.ID;
    }
}