package Succubus.cards;

import Succubus.actions.DoAction;
import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.stances.FlameStance;
import Succubus.stances.ShadowStance;
import Succubus.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Tantrum;
import com.megacrit.cardcrawl.cards.red.Havoc;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

import static Succubus.MainModfile.makeID;

public class FireBolt extends AbstractEasyCard {
    public final static String ID = makeID(FireBolt.class.getSimpleName());

    public FireBolt() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 7;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        addToBot(new DoAction(() -> {
            if (p.stance instanceof FlameStance) {
                dmgTop(m, AbstractGameAction.AttackEffect.FIRE);
            }
        }));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (Wiz.adp().stance instanceof FlameStance) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upp() {
        //upgradeBaseCost(1);
        upgradeDamage(3);
    }

    @Override
    public String cardArtCopy() {
        return Tantrum.ID;
    }

}