package Succubus.cards;

import Succubus.actions.DoAction;
import Succubus.cardmods.FlatDamageMod;
import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.SecondWind;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class Sheathe extends AbstractEasyCard {
    public final static String ID = makeID(Sheathe.class.getSimpleName());

    public Sheathe() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = block = 9;
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    public void triggerOnManualDiscard() {
        superFlash();
        addToBot(new DoAction(() -> {
            for (AbstractCard card : Wiz.adp().hand.group) {
                if (card.type == CardType.ATTACK) {
                    CardModifierManager.addModifier(card, new FlatDamageMod(magicNumber));
                }
            }
        }));

    }

    @Override
    public void upp() {
        upgradeBlock(3);
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return SecondWind.ID;
    }
}