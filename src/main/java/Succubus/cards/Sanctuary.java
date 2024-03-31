package Succubus.cards;

import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.cards.interfaces.PreventExhaustIfHeldCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.optionCards.LiveForever;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class Sanctuary extends AbstractEasyCard implements PreventExhaustIfHeldCard {
    public final static String ID = makeID(Sanctuary.class.getSimpleName());

    public Sanctuary() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = block = 8;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    public void upp() {
        upgradeBlock(3);
    }

    @Override
    public String cardArtCopy() {
        return LiveForever.ID;
    }

    @Override
    public boolean preventExhaust(AbstractCard card) {
        return true;
    }
}