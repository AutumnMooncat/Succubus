package Succubus.vfx;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;

public class ColoredStanceAuraEffect extends StanceAuraEffect {
    public ColoredStanceAuraEffect(Color color) {
        super("");
        this.color.set(color);
        this.color.a = 0;
    }
}
