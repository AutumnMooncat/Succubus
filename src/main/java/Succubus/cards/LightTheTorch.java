package Succubus.cards;

import Succubus.actions.DoAction;
import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.stances.FlameStance;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.purple.ReachHeaven;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class LightTheTorch extends AbstractEasyCard {
    public final static String ID = makeID(LightTheTorch.class.getSimpleName());

    public LightTheTorch() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DoAction(() -> {
            if (p.stance instanceof FlameStance) {
                addToTop(new DrawCardAction(magicNumber));
            } else {
                addToTop(new ChangeStanceAction(new FlameStance()));
            }
        }));

    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return ReachHeaven.ID;
    }
}