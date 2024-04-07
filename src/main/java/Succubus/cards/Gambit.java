package Succubus.cards;

import Succubus.actions.DoAction;
import Succubus.cardmods.FlatDamageMod;
import Succubus.cards.abstracts.AbstractEasyCard;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.SecretWeapon;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Succubus.MainModfile.makeID;

public class Gambit extends AbstractEasyCard {
    public final static String ID = makeID(Gambit.class.getSimpleName());

    public Gambit() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> copy = new ArrayList<>(p.hand.group);
        addToBot(new DrawPileToHandAction(1, CardType.ATTACK));
        addToBot(new DoAction(() -> {
            for (AbstractCard c : p.hand.group) {
                if (!copy.contains(c)) {
                    CardModifierManager.addModifier(c, new FlatDamageMod(magicNumber));
                }
            }
        }));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }

    @Override
    public String cardArtCopy() {
        return SecretWeapon.ID;
    }
}