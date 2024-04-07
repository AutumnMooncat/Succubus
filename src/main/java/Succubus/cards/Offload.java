package Succubus.cards;

import Succubus.actions.BetterSelectCardsInHandAction;
import Succubus.cards.abstracts.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.ToolsOfTheTrade;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class Offload extends AbstractEasyCard {
    public final static String ID = makeID(Offload.class.getSimpleName());

    public Offload() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        addToBot(new BetterSelectCardsInHandAction(magicNumber, DiscardAction.TEXT[0], true, true, c -> true, cards -> {
            for (AbstractCard c : cards) {
                addToTop(new GainEnergyAction(1));
                addToTop(new DiscardSpecificCardAction(c));
            }
        }));
    }

    @Override
    public void upp() {
        //upgradeMagicNumber(1);
        upgradeBaseCost(0);
    }

    @Override
    public String cardArtCopy() {
        return ToolsOfTheTrade.ID;
    }
}