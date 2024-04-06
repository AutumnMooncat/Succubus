package Succubus.cards;

import Succubus.cards.abstracts.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.SwordBoomerang;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class ReturnSweep extends AbstractEasyCard {
    public final static String ID = makeID(ReturnSweep.class.getSimpleName());

    public ReturnSweep() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = damage = 8;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
    }

    @Override
    public void triggerOnManualDiscard() {
        superFlash();
        AbstractCard copy = makeSameInstanceOf();
        copy.purgeOnUse = true;
        addToBot(new NewQueueCardAction(copy, true, true, true));
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }

    @Override
    public String cardArtCopy() {
        return SwordBoomerang.ID;
    }
}