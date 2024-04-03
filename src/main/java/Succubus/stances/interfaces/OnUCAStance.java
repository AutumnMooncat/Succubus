package Succubus.stances.interfaces;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public interface OnUCAStance {
    void onUseCardAction(AbstractCard card, UseCardAction action);
}
