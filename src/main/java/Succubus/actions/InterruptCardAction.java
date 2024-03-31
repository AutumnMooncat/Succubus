package Succubus.actions;

import Succubus.ui.SheathedCardManager;
import Succubus.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class InterruptCardAction extends AbstractGameAction {
    private AbstractCard card;
    private static final float DUR = 0.15F;

    public InterruptCardAction(AbstractCard card) {
        this.card = card;
        this.duration = this.startDuration = DUR;
        this.actionType = ActionType.CARD_MANIPULATION;
    }
    @Override
    public void update() {
        if (duration == DUR) {
            Wiz.adp().hand.moveToDiscardPile(card);
            SheathedCardManager.renderQueue.removeCard(card);
            SheathedCardManager.SheathedCardFields.sheathedField.set(card, false);
            SheathedCardManager.SheathedCardFields.interruptedField.set(card, false);
        }
        tickDuration();
        if (isDone) {
            SheathedCardManager.playNextCard();
        }
    }
}
