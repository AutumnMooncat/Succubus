package Succubus.cards;

import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.powers.CharmPower;
import Succubus.stances.ShadowStance;
import Succubus.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.green.Bane;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class GlowingEyes extends AbstractEasyCard {
    public final static String ID = makeID(GlowingEyes.class.getSimpleName());

    public GlowingEyes() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToEnemy(m, new CharmPower(m, magicNumber));
    }

    @Override
    public void upp() {
        //upgradeBaseCost(1);
        upgradeMagicNumber(2);
    }

    @Override
    public String cardArtCopy() {
        return Bane.ID;
    }

}