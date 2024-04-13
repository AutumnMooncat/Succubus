package Succubus.powers;

import Succubus.MainModfile;
import Succubus.actions.DoAction;
import Succubus.cardmods.FlatDamageMod;
import Succubus.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import java.util.ArrayList;

public class TemptationPower extends AbstractEasyPower {
    public static final String POWER_ID = MainModfile.makeID(TemptationPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public TemptationPower(AbstractCreature owner, int amount) {
        super(NAME, PowerType.BUFF, false, owner, amount);
        this.ID = POWER_ID;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onExhaust(AbstractCard c) {
        flash();
        addToBot(new DoAction(() -> {
            ArrayList<AbstractCard> cards = Wiz.getAllCardsInCardGroups(true, true);
            for (AbstractCard card : cards) {
                CardModifierManager.addModifier(card, new FlatDamageMod(amount));
            }
        }));
    }
}