package Succubus.cards;

import Succubus.actions.DamageFollowupAction;
import Succubus.cards.abstracts.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.DeepBreath;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class LifeEssence extends AbstractEasyCard {
    public final static String ID = makeID(LifeEssence.class.getSimpleName());

    public LifeEssence() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = damage = 6;
        tags.add(CardTags.HEALING);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageFollowupAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.POISON, false, mon -> {
            if (mon.lastDamageTaken > 0 && mon instanceof AbstractMonster) {
                addToTop(new HealAction(p, p, mon.lastDamageTaken));
            }
        }));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
    }

    @Override
    public String cardArtCopy() {
        return DeepBreath.ID;
    }

}