package Succubus.powers;

import Succubus.MainModfile;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.DamageModApplyingPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import java.util.Collections;
import java.util.List;

public class PunisherPower extends AbstractEasyPower implements DamageModApplyingPower {
    public static final String POWER_ID = MainModfile.makeID(PunisherPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public PunisherPower(AbstractCreature owner, int amount) {
        super(NAME, PowerType.BUFF, false, owner, amount);
        this.ID = POWER_ID;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount*50 + DESCRIPTIONS[1];
    }

    @Override
    public boolean shouldPushMods(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return o instanceof AbstractCard && ((AbstractCard) o).type == AbstractCard.CardType.ATTACK;
    }

    @Override
    public List<AbstractDamageModifier> modsToPush(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return Collections.singletonList(new PunisherDamage(amount));
    }

    private static class PunisherDamage extends AbstractDamageModifier {
        int stacks;

        public PunisherDamage(int stacks) {
            this.stacks = stacks;
        }

        @Override
        public float atDamageFinalGive(float damage, DamageInfo.DamageType type, AbstractCreature target, AbstractCard card) {
            if (target != null && target.hasPower(CharmPower.POWER_ID)) {
                return damage * (1 + 0.5f*stacks);
            }
            return damage;
        }

        @Override
        public AbstractDamageModifier makeCopy() {
            return new PunisherDamage(stacks);
        }
    }
}