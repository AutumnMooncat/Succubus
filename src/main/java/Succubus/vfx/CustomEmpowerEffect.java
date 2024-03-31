package Succubus.vfx;

import Succubus.util.CustomSounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.EmpowerCircleEffect;

public class CustomEmpowerEffect extends AbstractGameEffect {
    private static final float SHAKE_DURATION = 0.25F;

    public CustomEmpowerEffect(float x, float y) {
        CardCrawlGame.sound.playAV(CustomSounds.SYNTH_MIX_KEY, 0.1F, 0.6f);// 12

        for(int i = 0; i < 18; ++i) {// 13
            AbstractDungeon.effectList.add(new EmpowerCircleEffect(x, y));// 14
        }

        CardCrawlGame.screenShake.rumble(0.25F);// 17
    }// 18

    public void update() {
        this.isDone = true;// 22
    }// 23

    public void render(SpriteBatch sb) {
    }// 27

    public void dispose() {
    }// 32
}