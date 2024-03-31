package Succubus.actions;

import Succubus.util.Wiz;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class QueueCardInHandAction extends NewQueueCardAction {
    private final AbstractCard card;

    public QueueCardInHandAction(AbstractCard card, AbstractCreature target) {
        super(card, target, true, true);
        this.card = card;
    }

    public QueueCardInHandAction(AbstractCard card) {
        super(card, true, true, true);
        this.card = card;
    }

    @Override
    public void update() {
        if (!Wiz.adp().hand.contains(card)) {
            this.isDone = true;
            return;
        }
        super.update();
    }
}
