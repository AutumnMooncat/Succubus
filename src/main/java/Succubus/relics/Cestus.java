package Succubus.relics;

import Succubus.TheSuccubus;
import Succubus.powers.DoubleAttackPower;
import Succubus.util.Wiz;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static Succubus.MainModfile.makeID;

public class Cestus extends AbstractEasyRelic {
    public static final String ID = makeID(Cestus.class.getSimpleName());
    HashMap<String, Integer> stats = new HashMap<>();
    private final String SAVED_STAT = DESCRIPTIONS[1];
    private final String PER_TURN = DESCRIPTIONS[2];
    private final String PER_COMBAT = DESCRIPTIONS[3];
    private boolean triggered;

    public Cestus() {
        super(ID, RelicTier.STARTER, LandingSound.MAGICAL, TheSuccubus.Enums.SUCCUBUS_ROSE_COLOR);
        resetStats();
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        Wiz.applyToSelf(new DoubleAttackPower(Wiz.adp(), 1));
        triggered = false;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.type == AbstractCard.CardType.ATTACK && !triggered) {
            triggered = true;
            if (c.cost == -1) {
                incrementStat(EnergyPanel.getCurrentEnergy());
            } else if (c.costForTurn > 0) {
                incrementStat(c.costForTurn);
            }
        }
    }

    public int getStat() {
        return stats.get(SAVED_STAT);
    }

    public void incrementStat(int amount) {
        stats.put(SAVED_STAT, stats.get(SAVED_STAT) + amount);
    }

    public String getStatsDescription() {
        return SAVED_STAT + stats.get(SAVED_STAT);
    }

    public String getExtendedStatsDescription(int totalCombats, int totalTurns) {
        // You would just return getStatsDescription() if you don't want to display per-combat and per-turn stats
        StringBuilder builder = new StringBuilder();
        builder.append(getStatsDescription());

        // Relic Stats truncates these extended stats to 3 decimal places, so we do the same
        DecimalFormat perTurnFormat = new DecimalFormat("#.###");

        float stat = (float)stats.get(SAVED_STAT);
        builder.append(PER_TURN);
        builder.append(perTurnFormat.format(stat / Math.max(totalTurns, 1)));
        builder.append(PER_COMBAT);
        builder.append(perTurnFormat.format(stat / Math.max(totalCombats, 1)));

        return builder.toString();
    }

    public void resetStats() {
        stats.put(SAVED_STAT, 0);
    }

    public JsonElement onSaveStats() {
        // An array makes more sense if you want to store more than one stat
        Gson gson = new Gson();
        ArrayList<Integer> statsToSave = new ArrayList<>();
        statsToSave.add(stats.get(SAVED_STAT));
        return gson.toJsonTree(statsToSave);
    }

    public void onLoadStats(JsonElement jsonElement) {
        if (jsonElement != null) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            stats.put(SAVED_STAT, jsonArray.get(0).getAsInt());
        } else {
            resetStats();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        // Relic Stats will always query the stats from the instance passed to BaseMod.addRelic()
        // Therefore, we make sure all copies share the same stats by copying the HashMap.
        Cestus newRelic = new Cestus();
        newRelic.stats = this.stats;
        return newRelic;
    }
}
