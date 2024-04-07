package Succubus.cards;

import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.stances.ShadowStance;
import Succubus.util.Wiz;
import com.megacrit.cardcrawl.cards.purple.Meditate;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class ShadowWithin extends AbstractEasyCard {
    public final static String ID = makeID(ShadowWithin.class.getSimpleName());

    public ShadowWithin() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 5;
        baseBlock = block = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(2);
    }

    @Override
    protected void applyPowersToBlock() {
        int base = baseBlock;
        if (Wiz.adp().stance instanceof ShadowStance) {
            baseBlock += magicNumber;
        }
        super.applyPowersToBlock();
        baseBlock = base;
        isBlockModified = baseBlock != block;
    }

    @Override
    public String cardArtCopy() {
        return Meditate.ID;
    }
}