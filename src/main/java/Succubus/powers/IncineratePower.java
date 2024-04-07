package Succubus.powers;

import Succubus.MainModfile;
import Succubus.actions.DoAction;
import Succubus.actions.ExhaustByPredAction;
import Succubus.stances.FlameStance;
import Succubus.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.stances.AbstractStance;

public class IncineratePower extends AbstractEasyPower {
    public static final String POWER_ID = MainModfile.makeID(IncineratePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public IncineratePower(AbstractCreature owner, int amount) {
        super(NAME, PowerType.BUFF, false, owner, amount);
        this.ID = POWER_ID;
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance) {
        if (!oldStance.ID.equals(newStance.ID) && newStance instanceof FlameStance) {
            flash();
            addToBot(new ExhaustByPredAction(Wiz.adp().hand, amount, true, c -> true, new DoAction(() -> {
                if (!ExhaustByPredAction.exhaustedCards.isEmpty()) {
                    addToTop(new DrawCardAction(ExhaustByPredAction.exhaustedCards.size()));
                }
            })));
        }
    }
}