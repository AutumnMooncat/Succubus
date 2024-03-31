package Succubus.damageMods;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class BloodRiteDamage extends AbstractDamageModifier {

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCreature target, AbstractCard card) {
        if (target != null && target.hasPower(VulnerablePower.POWER_ID)) {
            damage += target.getPower(VulnerablePower.POWER_ID).amount;
        }
        return damage;
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new BloodRiteDamage();
    }
}
