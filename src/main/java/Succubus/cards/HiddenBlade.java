package Succubus.cards;

import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.util.Wiz;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.InfernalBlade;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class HiddenBlade extends AbstractEasyCard {
    public final static String ID = makeID(HiddenBlade.class.getSimpleName());

    public HiddenBlade() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public void triggerOnExhaust() {
        superFlash();
        AbstractCard card = Wiz.returnTrulyRandomPrediCardInCombat(c -> c.type == CardType.ATTACK);
        if (upgraded && card.canUpgrade()) {
            card.upgrade();
        }
        if (card.cost > 0) {
            card.cost = 0;
            card.costForTurn = 0;
            card.isCostModified = true;
        }
        addToTop(new MakeTempCardInHandAction(card));
    }

    @Override
    public void upp() {
        uDesc();
    }

    @Override
    public String cardArtCopy() {
        return InfernalBlade.ID;
    }
}