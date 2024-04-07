package Succubus.cards;

import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.powers.CharmPower;
import Succubus.util.Wiz;
import com.megacrit.cardcrawl.cards.red.SeeingRed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class Bewitch extends AbstractEasyCard {
    public final static String ID = makeID(Bewitch.class.getSimpleName());

    public Bewitch() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseMagicNumber = magicNumber = 10;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.forAllMonstersLiving(mon -> Wiz.applyToEnemy(mon, new CharmPower(mon, magicNumber)));
    }

    @Override
    public void upp() {
        upgradeBaseCost(1);
        //upgradeMagicNumber(3);
    }

    @Override
    public String cardArtCopy() {
        return SeeingRed.ID;
    }

}