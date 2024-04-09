package Succubus.cards;

import Succubus.actions.DoAction;
import Succubus.cardmods.FlatDamageMod;
import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Scrape;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.RipAndTearEffect;

import java.util.ArrayList;

import static Succubus.MainModfile.makeID;

public class TwinClaw extends AbstractEasyCard {
    public final static String ID = makeID(TwinClaw.class.getSimpleName());

    public TwinClaw() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 3;
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot(new VFXAction(new RipAndTearEffect(m.hb.cX, m.hb.cY, Color.RED, Color.GOLD)));
            addToBot(new VFXAction(new RipAndTearEffect(m.hb.cX, m.hb.cY, Color.RED, Color.GOLD)));
        }
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        addToBot(new DoAction(() -> {
            CardModifierManager.addModifier(this, new FlatDamageMod(magicNumber));
            ArrayList<AbstractCard> cards = Wiz.getAllCardsInCardGroups(true, true);
            for (AbstractCard card : cards) {
                if (card instanceof TwinClaw) {
                    CardModifierManager.addModifier(card, new FlatDamageMod(magicNumber));
                }
            }
        }));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
    }

    @Override
    public String cardArtCopy() {
        return Scrape.ID;
    }
}