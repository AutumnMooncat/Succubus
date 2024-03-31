package Succubus.cardmods;

import Succubus.MainModfile;
import Succubus.actions.QueueCardInHandAction;
import Succubus.patches.CardModExtraPatches;
import Succubus.patches.CustomTags;
import Succubus.util.FormatHelper;
import Succubus.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class ComboMod extends AbstractCardModifier {
    public static final String ID = MainModfile.makeID(ComboMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;
    boolean inherent;

    public ComboMod(boolean inherent) {
        this.inherent = inherent;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.tags.add(CustomTags.SUCCUBUS_COMBO);
    }

    @Override
    public void onOtherCardPlayed(AbstractCard card, AbstractCard otherCard, CardGroup group) {
        if (group == Wiz.adp().hand) {
            if (otherCard.type == AbstractCard.CardType.ATTACK && Wiz.getAdjacentCards(card).contains(otherCard)) {
                if (CardModExtraPatches.onOtherCardPlayedTarget != null) {
                    Wiz.atb(new QueueCardInHandAction(card, CardModExtraPatches.onOtherCardPlayedTarget));
                } else {
                    Wiz.atb(new QueueCardInHandAction(card));
                }
            }
        }
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return FormatHelper.insertBeforeText(rawDescription , TEXT[0]);
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
        return new ComboMod(inherent);
    }
}
