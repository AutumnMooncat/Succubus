package Succubus.cards;

import Succubus.cards.abstracts.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.GrandFinale;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;

import static Succubus.MainModfile.makeID;

public class SwanSong extends AbstractEasyCard {
    public final static String ID = makeID(SwanSong.class.getSimpleName());

    public SwanSong() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = damage = 20;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new GrandFinalEffect(), 0.7F));
        allDmg(AbstractGameAction.AttackEffect.SLASH_HEAVY);
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else if (p.hand.group.stream().anyMatch(c -> c != this)) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        } else {
            return true;
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (AbstractDungeon.player.hand.group.stream().noneMatch(c -> c != this)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upp() {
        upgradeDamage(5);
    }

    @Override
    public String cardArtCopy() {
        return GrandFinale.ID;
    }
}