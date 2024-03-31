package Succubus.actions;

import Succubus.MainModfile;
import Succubus.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.function.Predicate;

public class ExhaustByPredAction extends AbstractGameAction {
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(MainModfile.makeID("Exhaust")).TEXT;
    private static final float DUR = Settings.ACTION_DUR_FASTER;
    private final CardGroup cardGroup;
    private final Predicate<AbstractCard> filter;
    private final AbstractGameAction followUpAction;
    private final boolean anyNumber;
    public static final ArrayList<AbstractCard> exhaustedCards = new ArrayList<>();

    public ExhaustByPredAction(CardGroup cardGroup) {
        this(cardGroup, 1, false, c -> true, null);
    }

    public ExhaustByPredAction(CardGroup cardGroup, int amount) {
        this(cardGroup, amount, false, c -> true, null);
    }

    public ExhaustByPredAction(CardGroup cardGroup, int amount, Predicate<AbstractCard> filter) {
        this(cardGroup, amount, false, filter, null);
    }

    public ExhaustByPredAction(CardGroup cardGroup, int amount, Predicate<AbstractCard> filter, AbstractGameAction followUpAction) {
        this(cardGroup, amount, false, filter, followUpAction);
    }

    public ExhaustByPredAction(CardGroup cardGroup, int amount, boolean anyNumber, Predicate<AbstractCard> filter, AbstractGameAction followUpAction) {
        this.cardGroup = cardGroup;
        this.amount = amount;
        this.filter = filter;
        this.anyNumber = anyNumber;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = DUR;
        this.followUpAction = followUpAction;
    }

    @Override
    public void update() {
        exhaustedCards.clear();
        if (AbstractDungeon.getCurrRoom().isBattleEnding() || amount == 0) {
            this.isDone = true;
        }
        ArrayList<AbstractCard> validCards = new ArrayList<>();
        for (AbstractCard c : cardGroup.group) {
            if (filter.test(c)) {
                validCards.add(c);
            }
        }
        if (validCards.isEmpty()) {
            this.isDone = true;
            return;
        }

        if (amount >= validCards.size() && !anyNumber) {
            for (AbstractCard card : validCards) {
                cardGroup.moveToExhaustPile(card);
                exhaustedCards.add(card);
            }
            if (this.followUpAction != null) {
                this.addToTop(this.followUpAction);
            }
        } else {
            if (cardGroup == Wiz.adp().hand) {
                Wiz.att(new BetterSelectCardsInHandAction(this.amount, TEXT[0], anyNumber, anyNumber, validCards::contains, cards -> {
                    for (AbstractCard c : cards) {
                        cardGroup.moveToExhaustPile(c);
                        exhaustedCards.add(c);
                    }
                    cards.clear(); // Remove from selection, so they don't get added back to hand
                    if (this.followUpAction != null) {
                        this.addToTop(this.followUpAction);
                    }
                }));
            } else {
                String displayText;
                if (anyNumber) {
                    if (amount == 1) {
                        displayText = TEXT[4] + amount + TEXT[5];
                    } else {
                        displayText = TEXT[4] + amount + TEXT[6];
                    }
                } else {
                    if (amount == 1) {
                        displayText = TEXT[1];
                    } else {
                        displayText = TEXT[2] + amount + TEXT[3];
                    }
                }
                //Pre-filtered so just pass c -> true
                Wiz.att(new BetterSelectCardsCenteredAction(validCards, this.amount, displayText, anyNumber, c -> true, cards -> {
                    for (AbstractCard c : cards) {
                        cardGroup.moveToExhaustPile(c);
                        exhaustedCards.add(c);
                    }
                    if (this.followUpAction != null) {
                        this.addToTop(this.followUpAction);
                    }
                }));
            }
        }
        this.isDone = true;
    }
}
