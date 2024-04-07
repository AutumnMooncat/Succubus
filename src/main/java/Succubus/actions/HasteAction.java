package Succubus.actions;

import Succubus.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class HasteAction extends AbstractGameAction {

    public HasteAction(int energySum) {
        this.amount = energySum;
        this.actionType = ActionType.DRAW;
    }

    @Override
    public void update() {
        int sum = 0;
        for (AbstractCard c : Wiz.adp().hand.group) {
            if (c.costForTurn > 0) {
                sum += c.costForTurn;
            }
        }
        if (sum < amount && !(Wiz.adp().drawPile.isEmpty() && Wiz.adp().discardPile.isEmpty())) {
            addToTop(new HasteAction(amount));
            addToTop(new DrawCardAction(1));
        }
        this.isDone = true;
    }
}
