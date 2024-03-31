package Succubus.ui;

import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;

public class BetterCustomEnergyOrb extends CustomEnergyOrb {
    protected static final float ORB_IMG_SCALE = 1.15F * Settings.scale;
    protected int[] layerSizes;
    
    public BetterCustomEnergyOrb(String[] orbTexturePaths, String orbVfxPath, float[] layerSpeeds, int[] layerSizes) {
        super(orbTexturePaths, orbVfxPath, layerSpeeds);
        this.layerSizes = layerSizes;
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
        sb.setColor(Color.WHITE);// 106
        int i;
        if (enabled) {// 107
            for(i = 0; i < this.energyLayers.length; ++i) {// 108
                sb.draw(this.energyLayers[i], current_x - layerSizes[i]/2f, current_y - layerSizes[i]/2f, layerSizes[i]/2f, layerSizes[i]/2f, layerSizes[i], layerSizes[i], ORB_IMG_SCALE, ORB_IMG_SCALE, this.angles[i], 0, 0, layerSizes[i], layerSizes[i], false, false);// 109
            }
        } else {
            for(i = 0; i < this.noEnergyLayers.length; ++i) {// 120
                sb.draw(this.noEnergyLayers[i], current_x - layerSizes[i]/2f, current_y - layerSizes[i]/2f, layerSizes[i]/2f, layerSizes[i]/2f, layerSizes[i], layerSizes[i], ORB_IMG_SCALE, ORB_IMG_SCALE, this.angles[i], 0, 0, layerSizes[i], layerSizes[i], false, false);// 121
            }
        }
        i = layerSizes.length-1;
        sb.draw(this.baseLayer, current_x - layerSizes[i]/2f, current_y - layerSizes[i]/2f, layerSizes[i]/2f, layerSizes[i]/2f, layerSizes[i], layerSizes[i], ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, layerSizes[i], layerSizes[i], false, false);// 132
    }
}
