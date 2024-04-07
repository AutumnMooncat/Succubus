package Succubus.cards;

import Succubus.actions.BetterSelectCardsInHandAction;
import Succubus.cards.abstracts.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.BladeDance;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class Jettison extends AbstractEasyCard {
    public final static String ID = makeID(Jettison.class.getSimpleName());

    public Jettison() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = block = 7;
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        addToBot(new BetterSelectCardsInHandAction(magicNumber, DiscardAction.TEXT[0], true, true, c -> true, cards -> {
            for (AbstractCard c : cards) {
                addToTop(new DiscardSpecificCardAction(c));
            }
        }));
    }

    @Override
    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return BladeDance.ID;
    }
}