package Succubus.powers;

import Succubus.MainModfile;
import Succubus.stances.ShadowStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class MalicePower extends AbstractEasyPower {
    public static final String POWER_ID = MainModfile.makeID(MalicePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public MalicePower(AbstractCreature owner, int amount) {
        super(NAME, PowerType.BUFF, false, owner, amount);
        this.ID = POWER_ID;
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        if (isPlayer) {
            AbstractPlayer p = (AbstractPlayer)this.owner;
            if (p.stance instanceof ShadowStance) {
                flash();
                addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.POISON));
            }
        }

    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}