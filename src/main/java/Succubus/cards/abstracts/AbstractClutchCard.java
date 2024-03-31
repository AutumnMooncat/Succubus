package Succubus.cards.abstracts;

import com.evacipated.cardcrawl.mod.stslib.patches.HitboxRightClick;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public abstract class AbstractClutchCard extends AbstractEasyCard {
    public AbstractClutchCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(cardID, cost, type, rarity, target);
    }

    public AbstractClutchCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(cardID, cost, type, rarity, target, color);
    }

    @Override
    public void update() {
        super.update();
        if (AbstractDungeon.player != null) {
            clickUpdate();
        }
    }

    public void clickUpdate() {
        if (!AbstractDungeon.isScreenUp && HitboxRightClick.rightClicked.get(this.hb) && !AbstractDungeon.actionManager.turnHasEnded && AbstractDungeon.player.hand.contains(this)) {
            addToBot(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand, true));
            onClutch();
        }
    }

    public abstract void onClutch();
}
