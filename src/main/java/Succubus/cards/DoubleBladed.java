package Succubus.cards;

import Succubus.actions.BetterSelectCardsInHandAction;
import Succubus.cardmods.ExhaustMod;
import Succubus.cardmods.FlatDamageMod;
import Succubus.cards.abstracts.AbstractEasyCard;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.DualWield;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Succubus.MainModfile.makeID;

public class DoubleBladed extends AbstractEasyCard {
    public final static String ID = makeID(DoubleBladed.class.getSimpleName());

    public DoubleBladed() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new BetterSelectCardsInHandAction(1, cardStrings.EXTENDED_DESCRIPTION[0], false, false, c -> c.type == CardType.ATTACK, cards -> {
            for (AbstractCard c : cards) {
                int effect = c.baseDamage;
                ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(c, FlatDamageMod.ID);
                if (!mods.isEmpty()) {
                    FlatDamageMod fdm = (FlatDamageMod) mods.get(0);
                    effect += fdm.amount;
                }
                CardModifierManager.addModifier(c, new FlatDamageMod(effect));
                CardModifierManager.addModifier(c, new ExhaustMod());
            }
        }));
    }

    @Override
    public void upp() {
        upgradeBaseCost(0);
    }

    @Override
    public String cardArtCopy() {
        return DualWield.ID;
    }
}