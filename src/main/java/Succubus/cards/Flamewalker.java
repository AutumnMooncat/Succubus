package Succubus.cards;

import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.stances.FlameStance;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.purple.Eruption;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class Flamewalker extends AbstractEasyCard {
    public final static String ID = makeID(Flamewalker.class.getSimpleName());

    public Flamewalker() {
        super(ID, 2, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseBlock = block = 8;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        addToBot(new ChangeStanceAction(new FlameStance()));
    }

    @Override
    public void upp() {
        upgradeBaseCost(1);
    }

    @Override
    public String cardArtCopy() {
        return Eruption.ID;
    }
}