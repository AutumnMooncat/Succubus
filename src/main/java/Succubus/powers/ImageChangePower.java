package Succubus.powers;

import Succubus.MainModfile;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class ImageChangePower extends AbstractEasyPower {
    public static final String POWER_ID = MainModfile.makeID(ImageChangePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ImageChangePower(AbstractCreature owner, int amount) {
        super(NAME, PowerType.BUFF, false, owner, amount);
        this.ID = POWER_ID;
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0];
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(DESCRIPTIONS[1]).append(amount).append(DESCRIPTIONS[2]);
            for (int i = 0 ; i < amount ; i++) {
                sb.append("[E] ");
            }
            sb.append(LocalizedStrings.PERIOD);
            description = sb.toString();
        }
    }

    @Override
    public void onInitialApplication() {
        AbstractDungeon.player.gameHandSize -= this.amount;
    }

    @Override
    public void stackPower(int stackAmount) {
        AbstractDungeon.player.gameHandSize -= stackAmount;
        super.stackPower(stackAmount);
    }

    @Override
    public void onRemove() {
        AbstractDungeon.player.gameHandSize += this.amount;
    }

    @Override
    public void atStartOfTurn() {
        flash();
        addToBot(new GainEnergyAction(this.amount));
    }
}