package Succubus.cards;

import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.util.Wiz;
import com.megacrit.cardcrawl.cards.purple.MasterReality;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

import static Succubus.MainModfile.makeID;

public class SmokeAndMirrors extends AbstractEasyCard {
    public final static String ID = makeID(SmokeAndMirrors.class.getSimpleName());

    public SmokeAndMirrors() {
        super(ID, 3, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public void triggerOnExhaust() {
        superFlash();
        Wiz.applyToSelf(new IntangiblePlayerPower(Wiz.adp(), magicNumber));
    }

    @Override
    public void upp() {
        upgradeBaseCost(2);
    }

    @Override
    public String cardArtCopy() {
        return MasterReality.ID;
    }
}