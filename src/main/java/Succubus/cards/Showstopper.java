package Succubus.cards;

import Succubus.actions.FinaleAction;
import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.patches.DelayedExhaustPatches;
import Succubus.powers.CharmPower;
import Succubus.util.Wiz;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.BulletTime;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;

import static Succubus.MainModfile.makeID;

public class Showstopper extends AbstractEasyCard {
    public final static String ID = makeID(Showstopper.class.getSimpleName());

    public Showstopper() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.ALL);
        baseBlock = block = 9;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        addToBot(new FinaleAction(() -> {
            addToBot(new VFXAction(new GrandFinalEffect(), 0.7F));
            Wiz.forAllMonstersLiving(mon -> Wiz.applyToEnemyTop(mon, new CharmPower(mon, 99)));
            DelayedExhaustPatches.DelayedExhaustField.delayedExhaust.set(this, true);
        }));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (AbstractDungeon.player.hand.group.stream().noneMatch(c -> c != this)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upp() {
        upgradeBlock(3);
    }

    @Override
    public String cardArtCopy() {
        return BulletTime.ID;
    }
}