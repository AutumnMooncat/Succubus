package Succubus.cards;

import Succubus.actions.EasyXCostAction;
import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.util.Wiz;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Unload;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class BoxOfStrikes extends AbstractEasyCard {
    public final static String ID = makeID(BoxOfStrikes.class.getSimpleName());

    public BoxOfStrikes() {
        super(ID, -1, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        baseMagicNumber = magicNumber = 0;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EasyXCostAction(this, (base, params) -> {
            int effect = base;
            for (int i : params) {
                effect += i;
            }
            if (effect > 0) {
                for (int i = 0 ; i < effect ; i++) {
                    AbstractCard c = Wiz.returnTrulyRandomPrediCardInCombat(card -> card.hasTag(CardTags.STRIKE));
                    if (c.cost > 0) {
                        c.cost = 0;
                        c.costForTurn = 0;
                        c.isCostModified = true;
                    }
                    addToTop(new MakeTempCardInDrawPileAction(c, 1, true, true));
                }
            }
            return true;
        }, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return Unload.ID;
    }
}