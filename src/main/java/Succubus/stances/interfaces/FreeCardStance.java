package Succubus.stances.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface FreeCardStance {
    boolean isFree(AbstractCard card);
    void onTrigger(AbstractCard card);
}
