package Succubus.cards;

import Succubus.actions.FinaleAction;
import Succubus.cardmods.FlatDamageMod;
import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Finisher;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class MightyStrike extends AbstractEasyCard {
    public final static String ID = makeID(MightyStrike.class.getSimpleName());

    public MightyStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 5;
        baseMagicNumber = magicNumber = 2;
        tags.add(CardTags.STRIKE);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        addToBot(new FinaleAction(() -> {
            CardModifierManager.addModifier(this, new FlatDamageMod(magicNumber));
            for (AbstractCard c : Wiz.adp().masterDeck.group) {
                if (c.uuid.equals(this.uuid)) {
                    CardModifierManager.addModifier(c, new FlatDamageMod(magicNumber));
                }
            }
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
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return Finisher.ID;
    }
}