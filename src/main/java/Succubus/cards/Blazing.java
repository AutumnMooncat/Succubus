package Succubus.cards;

import Succubus.actions.BetterSelectCardsInHandAction;
import Succubus.cardmods.FlatDamageMod;
import Succubus.cards.abstracts.AbstractEasyCard;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Crescendo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class Blazing extends AbstractEasyCard {
    public final static String ID = makeID(Blazing.class.getSimpleName());

    public Blazing() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new BetterSelectCardsInHandAction(1, cardStrings.EXTENDED_DESCRIPTION[0], false, false, c -> c.type == CardType.ATTACK, cards -> {
            for (AbstractCard c : cards) {
                CardModifierManager.addModifier(c, new FlatDamageMod(magicNumber));
            }
        }));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }

    @Override
    public String cardArtCopy() {
        return Crescendo.ID;
    }
}