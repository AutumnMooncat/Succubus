package Succubus.cards;

import Succubus.cards.abstracts.AbstractEasyCard;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.cards.blue.AutoShields;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class AdaptiveAegis extends AbstractEasyCard {
    public final static String ID = makeID(AdaptiveAegis.class.getSimpleName());

    public AdaptiveAegis() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF_AND_ENEMY);
        baseBlock = block = 0;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        baseBlock = mo.getIntentDmg();
        if (ReflectionHacks.<Boolean>getPrivate(mo, AbstractMonster.class, "isMultiDmg")) {
            baseBlock *= ReflectionHacks.<Integer>getPrivate(mo, AbstractMonster.class, "intentMultiAmt");
        }
        if (baseBlock < 0) {
            baseBlock = 0;
        }
        super.calculateCardDamage(mo);
        rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public void applyPowers() {
        baseBlock = 0;
        super.applyPowers();
        rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void upp() {
        upgradeBaseCost(1);
    }

    @Override
    public String cardArtCopy() {
        return AutoShields.ID;
    }
}