package Succubus.cards;

import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.powers.PheromonesPower;
import Succubus.util.Wiz;
import com.megacrit.cardcrawl.cards.green.CripplingPoison;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class Pheromones extends AbstractEasyCard {
    public final static String ID = makeID(Pheromones.class.getSimpleName());

    public Pheromones() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new PheromonesPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeBaseCost(2);
    }

    @Override
    public String cardArtCopy() {
        return CripplingPoison.ID;
    }
}