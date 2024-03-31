package Succubus.powers.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface OnSheathePower {
    void onSheathe(AbstractCard card, boolean isEndTurn);
}
