package Succubus.cards;

import Succubus.MainModfile;
import Succubus.actions.BetterSelectCardsInHandAction;
import Succubus.cards.abstracts.AbstractEasyCard;
import Succubus.util.TextureSniper;
import Succubus.vfx.VFXContainer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.DaggerThrow;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Succubus.MainModfile.makeID;

public class PrecisionStrike extends AbstractEasyCard {
    public final static String ID = makeID(PrecisionStrike.class.getSimpleName());

    public PrecisionStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 5;
        tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        addToBot(new BetterSelectCardsInHandAction(1, DiscardAction.TEXT[0], false, false, c -> true, cards -> {
            for (AbstractCard c : cards) {
                if (m != null) {
                    damage *= c.costForTurn;
                    dmgTop(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
                    addToTop(new VFXAction(VFXContainer.throwEffect(TextureSniper.snipeCard(c), 0.25f, m.hb, MainModfile.SUCCUBUS_ROSE_COLOR, true, true)));
                    //addToTop(new SFXAction("APPEAR"));
                    addToTop(new DiscardSpecificCardAction(c));
                }
            }
        }));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
    }

    @Override
    public String cardArtCopy() {
        return DaggerThrow.ID;
    }
}