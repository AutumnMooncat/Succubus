package Succubus.powers;

import Succubus.MainModfile;
import Succubus.actions.DoAction;
import Succubus.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import java.util.ArrayList;

public class StalkerPower extends AbstractEasyPower {
    public static final String POWER_ID = MainModfile.makeID(StalkerPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public StalkerPower(AbstractCreature owner, int amount) {
        super(NAME, PowerType.BUFF, false, owner, amount);
        this.ID = POWER_ID;
        priority = 21;
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        addToBot(new DoAction(() -> {
            for (int i = 0 ; i < amount ; i++) {
                ArrayList<AbstractCard> validCards = new ArrayList<>();
                for (AbstractCard c : Wiz.adp().hand.group) {
                    if (c.costForTurn > 0) {
                        validCards.add(c);
                    }
                }
                if (validCards.isEmpty()) {
                    return;
                }
                AbstractCard card = Wiz.getRandomItem(validCards);
                card.setCostForTurn(card.costForTurn - 1);
            }
        }));
    }
}