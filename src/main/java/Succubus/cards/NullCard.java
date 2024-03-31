package Succubus.cards;

import Succubus.cards.abstracts.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.NotStanceCheckAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.EmptyStanceEffect;

import static Succubus.MainModfile.makeID;

public class NullCard extends AbstractEasyCard {
    public final static String ID = makeID(NullCard.class.getSimpleName());

    public NullCard() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        selfRetain = true;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new NotStanceCheckAction("Neutral", new VFXAction(new EmptyStanceEffect(p.hb.cX, p.hb.cY), 0.1F)));
        addToBot(new ChangeStanceAction("Neutral"));
    }

    @Override
    public void upp() {
        uDesc();
        exhaust = false;
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }
}