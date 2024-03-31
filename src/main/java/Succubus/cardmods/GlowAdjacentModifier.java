
package Succubus.cardmods;

import Succubus.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;

public interface GlowAdjacentModifier {
    default boolean glowAdjacent(AbstractCard thisCard, AbstractCard otherCard) {
        return Wiz.getAdjacentCards(thisCard).contains(otherCard);
    }

    default boolean flashAdjacent(AbstractCard thisCard, AbstractCard otherCard) {
        return glowAdjacent(thisCard, otherCard);
    }

    Color getGlowColor(AbstractCard card);
}
