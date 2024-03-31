package Succubus.cardmods;

import Succubus.MainModfile;
import Succubus.actions.QueueCardInHandAction;
import Succubus.patches.CustomTags;
import Succubus.util.FormatHelper;
import Succubus.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardBorderGlowManager;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.ArrayList;

public class GrabSmallerMod extends AbstractCardModifier implements GlowAdjacentModifier {
    public static final String ID = MainModfile.makeID(GrabSmallerMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;
    private static final Color glowColor = Color.GOLD.cpy();
    private static final ArrayList<AbstractCard> justFlashed = new ArrayList<>();
    private static final ArrayList<AbstractCard> checkedCards = new ArrayList<>();
    boolean inherent;

    static {
        CardBorderGlowManager.addGlowInfo(new CardBorderGlowManager.GlowInfo() {
            private final Color c = Color.RED.cpy();
            @Override
            public boolean test(AbstractCard card) {
                if (Wiz.adp().hoveredCard != null && CardModifierManager.hasModifier(Wiz.adp().hoveredCard, ID)) {
                    GrabSmallerMod mod = (GrabSmallerMod) CardModifierManager.getModifiers(Wiz.adp().hoveredCard, ID).get(0);
                    if (mod.glowAdjacent(Wiz.adp().hoveredCard, card)) {
                        if (!justFlashed.contains(card)) {
                            justFlashed.add(card);
                            if (mod.flashAdjacent(Wiz.adp().hoveredCard, card)) {
                                card.superFlash(mod.getGlowColor(card));
                            }
                        }
                        return true;
                    }
                    return false;
                }
                justFlashed.clear();
                return false;
            }

            @Override
            public Color getColor(AbstractCard card) {
                if (CardModifierManager.hasModifier(Wiz.adp().hoveredCard, ID)) {
                    return ((GrabSmallerMod)CardModifierManager.getModifiers(Wiz.adp().hoveredCard, ID).get(0)).getGlowColor(card);
                }
                return c;
            }

            @Override
            public String glowID() {
                return ID+"Glow";
            }
        });
    }

    public GrabSmallerMod(boolean inherent) {
        this.inherent = inherent;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.tags.add(CustomTags.SUCCUBUS_GRAB);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (target != null && !Wiz.isBigger(target)) {
            for (AbstractCard c : Wiz.getAdjacentCards(card)) {
                if (c.type == AbstractCard.CardType.ATTACK) {
                    Wiz.atb(new QueueCardInHandAction(c, target));
                }
            }
        }
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return FormatHelper.insertAfterText(rawDescription , TEXT[0]);
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return inherent;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new GrabSmallerMod(inherent);
    }

    @Override
    public boolean glowAdjacent(AbstractCard thisCard, AbstractCard otherCard) {
        checkedCards.clear();
        return chainCheck(thisCard, otherCard);
    }

    @Override
    public Color getGlowColor(AbstractCard card) {
        return glowColor;
    }

    private boolean chainCheck(AbstractCard currentCard, AbstractCard cardToCheck) {
        if (cardToCheck.type == AbstractCard.CardType.ATTACK) {
            if (Wiz.getAdjacentCards(currentCard).contains(cardToCheck)) {
                return true;
            }
            for (AbstractCard c : Wiz.getAdjacentCards(currentCard)) {
                if (c.hasTag(CustomTags.SUCCUBUS_GRAB) && c.type == AbstractCard.CardType.ATTACK && !checkedCards.contains(c)) {
                    checkedCards.add(c);
                    return chainCheck(c, cardToCheck);
                }
            }
        }
        return false;
    }
}
