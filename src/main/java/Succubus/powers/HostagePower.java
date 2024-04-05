package Succubus.powers;

import Succubus.MainModfile;
import Succubus.powers.interfaces.OnPlayerAttackedPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class HostagePower extends AbstractEasyPower implements OnPlayerAttackedPower {
    public static final String POWER_ID = MainModfile.makeID(HostagePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private final AbstractCreature source;

    public HostagePower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(NAME, PowerType.DEBUFF, false, owner, amount);
        this.ID = POWER_ID;
        this.source = source;
    }

    @Override
    public void atEndOfRound() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onPlayerAttacked(DamageInfo info) {
        if (info.owner != source && info.type == DamageInfo.DamageType.NORMAL) {
            flash();
            addToTop(new DamageAction(owner, new DamageInfo(source, amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
    }
}