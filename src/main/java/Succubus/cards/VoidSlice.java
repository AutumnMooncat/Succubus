package Succubus.cards;

import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.stances.ShadowStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Havoc;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class VoidSlice extends AbstractEasyCard {
    public final static String ID = makeID(VoidSlice.class.getSimpleName());

    public VoidSlice() {
        super(ID, 2, CardType.ATTACK, AbstractCard.CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = damage = 9;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.POISON);
        addToBot(new ChangeStanceAction(new ShadowStance()));
    }

    @Override
    public void upp() {
        //upgradeBaseCost(1);
        upgradeDamage(4);
    }

    @Override
    public String cardArtCopy() {
        return Havoc.ID;
    }

}