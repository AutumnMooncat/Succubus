package Succubus.actions;

import Succubus.MainModfile;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RearrangeAction extends AbstractGameAction {
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(MainModfile.makeID("RearrangeAction")).TEXT;

    public RearrangeAction() {
        this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (AbstractDungeon.player.hand.group.size() != 0) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], AbstractDungeon.player.hand.group.size(), false, false);
                this.tickDuration();
            } else {
                this.isDone = true;
            }
        } else if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            AbstractDungeon.player.hand.group.addAll(AbstractDungeon.handCardSelectScreen.selectedCards.group);
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.applyPowers();
            this.isDone = true;
        } else {
            this.tickDuration();
        }
    }
}