package Succubus.cards;

import Succubus.actions.EasyXCostAction;
import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.stances.FlameStance;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.blue.Overclock;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class Overheat extends AbstractEasyCard {
    public final static String ID = makeID(Overheat.class.getSimpleName());

    public Overheat() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        selfRetain = true;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChangeStanceAction(new FlameStance()));
        addToBot(new EasyXCostAction(this, (base, params) -> {
            int effect = base;
            for (int i : params) {
                effect += i;
            }
            if (effect >= 1) {
                addToTop(new DrawCardAction(effect));
            }
            return true;
        }));
    }

    @Override
    public void upp() {
        uDesc();
        exhaust = false;
    }

    @Override
    public String cardArtCopy() {
        return Overclock.ID;
    }
}