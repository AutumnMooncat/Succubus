package Succubus.cards;

import Succubus.actions.DoAction;
import Succubus.cardmods.FlatDamageMod;
import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Slice;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Succubus.MainModfile.makeID;

public class Sharpen extends AbstractEasyCard {
    public final static String ID = makeID(Sharpen.class.getSimpleName());

    public Sharpen() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 6;
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        addToBot(new DoAction(() -> {
            ArrayList<AbstractCard> cards = Wiz.getAllCardsInCardGroups(true, true);
            for (AbstractCard card : cards) {
                if (card.hasTag(CardTags.STRIKE)) {
                    CardModifierManager.addModifier(card, new FlatDamageMod(magicNumber));
                }
            }
        }));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return Slice.ID;
    }
}