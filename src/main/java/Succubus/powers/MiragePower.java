package Succubus.powers;

import Succubus.MainModfile;
import Succubus.stances.IllusionStance;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class MiragePower extends AbstractEasyPower {
    public static final String POWER_ID = MainModfile.makeID(MiragePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public MiragePower(AbstractCreature owner, int amount) {
        super(NAME, PowerType.BUFF, false, owner, amount);
        this.ID = POWER_ID;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onInitialApplication() {
        checkStacks();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        checkStacks();
    }

    private void checkStacks() {
        if (amount >= 10) {
            addToTop(new ChangeStanceAction(new IllusionStance()));
            this.amount -= 10;
            if (this.amount <= 0) {
                this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            }
        }
    }
}