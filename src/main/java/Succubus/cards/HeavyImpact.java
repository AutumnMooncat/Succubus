package Succubus.cards;

import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.NotStanceCheckAction;
import com.megacrit.cardcrawl.cards.red.BodySlam;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.vfx.combat.EmptyStanceEffect;

import static Succubus.MainModfile.makeID;

public class HeavyImpact extends AbstractEasyCard {
    public final static String ID = makeID(HeavyImpact.class.getSimpleName());

    public HeavyImpact() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        addToBot(new NotStanceCheckAction("Neutral", new VFXAction(new EmptyStanceEffect(p.hb.cX, p.hb.cY), 0.1F)));
        addToBot(new ChangeStanceAction("Neutral"));
    }

    @Override
    public void applyPowers() {
        int base = baseDamage;
        if (!(Wiz.adp().stance instanceof NeutralStance)) {
            baseDamage *= 2;
        }
        super.applyPowers();
        baseDamage = base;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int base = baseDamage;
        if (!(Wiz.adp().stance instanceof NeutralStance)) {
            baseDamage *= 2;
        }
        super.calculateCardDamage(mo);
        baseDamage = base;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void upp() {
        upgradeDamage(2);
    }

    @Override
    public String cardArtCopy() {
        return BodySlam.ID;
    }
}