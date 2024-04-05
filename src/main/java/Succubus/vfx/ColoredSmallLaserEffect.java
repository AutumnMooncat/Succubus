package Succubus.vfx;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

public class ColoredSmallLaserEffect extends SmallLaserEffect {
    public ColoredSmallLaserEffect(float sX, float sY, float dX, float dY, Color c) {
        super(sX, sY, dX, dY);
        this.color.set(c);
    }
}
