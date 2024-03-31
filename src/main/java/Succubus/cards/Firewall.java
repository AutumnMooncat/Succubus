package Succubus.cards;

import Succubus.actions.DoAction;
import Succubus.actions.ExhaustByPredAction;
import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.stances.FlameStance;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Eruption;
import com.megacrit.cardcrawl.cards.red.FlameBarrier;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class Firewall extends AbstractEasyCard {
    public final static String ID = makeID(Firewall.class.getSimpleName());

    public Firewall() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = block = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ExhaustByPredAction(p.hand, p.hand.size(), true, c -> true, new DoAction(() -> {
            for (AbstractCard ignored : ExhaustByPredAction.exhaustedCards) {
                addToTop(new GainBlockAction(p, p, block));
            }
        })));
        addToBot(new ChangeStanceAction(new FlameStance()));
    }

    @Override
    public void upp() {
        upgradeBlock(2);
    }

    @Override
    public String cardArtCopy() {
        return FlameBarrier.ID;
    }
}