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

public class GrabMod extends AbstractCardModifier implements GlowAdjacentModifier {
    public static final String ID = MainModfile.makeID(GrabMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;
    private static final Color glowColor = Color.GOLD.cpy();
    private static final ArrayList<AbstractCard> justFlashed = new ArrayList<>();
    private static final ArrayList<AbstractCard> checkedCards = new ArrayList<>();
    boolean inherent;
    boolean manualText;

    static {
        CardBorderGlowManager.addGlowInfo(new CardBorderGlowManager.GlowInfo() {
            private final Color c = Color.RED.cpy();
            @Override
            public boolean test(AbstractCard card) {
                if (Wiz.adp().hoveredCard != null && CardModifierManager.hasModifier(Wiz.adp().hoveredCard, ID)) {
                    GrabMod mod = (GrabMod) CardModifierManager.getModifiers(Wiz.adp().hoveredCard, ID).get(0);
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
                    return ((GrabMod)CardModifierManager.getModifiers(Wiz.adp().hoveredCard, ID).get(0)).getGlowColor(card);
                }
                return c;
            }

            @Override
            public String glowID() {
                return ID+"Glow";
            }
        });
    }

    public GrabMod(boolean inherent) {
        this.inherent = inherent;
    }

    public GrabMod(boolean inherent, boolean manualText) {
        this.inherent = inherent;
        this.manualText = manualText;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.tags.add(CustomTags.SUCCUBUS_GRAB);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        for (AbstractCard c : Wiz.getAdjacentCards(card)) {
            if (c.type == AbstractCard.CardType.ATTACK) {
                if (target != null) {
                    Wiz.atb(new QueueCardInHandAction(c, target));
                } else {
                    Wiz.atb(new QueueCardInHandAction(c));
                }
            }
        }
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (manualText) {
            return rawDescription;
        }
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
        return new GrabMod(inherent);
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
