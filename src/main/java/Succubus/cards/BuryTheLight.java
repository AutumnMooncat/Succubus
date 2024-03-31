package Succubus.cards;

import Succubus.actions.DoAction;
import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.stances.ShadowStance;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.blue.Aggregate;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class BuryTheLight extends AbstractEasyCard {
    public final static String ID = makeID(BuryTheLight.class.getSimpleName());

    public BuryTheLight() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DoAction(() -> {
            if (p.stance instanceof ShadowStance) {
                addToTop(new GainEnergyAction(magicNumber));
            } else {
                addToTop(new ChangeStanceAction(new ShadowStance()));
            }
        }));
    }

    @Override
    public void upp() {
        upgradeBaseCost(0);
    }

    @Override
    public String cardArtCopy() {
        return Aggregate.ID;
    }
}