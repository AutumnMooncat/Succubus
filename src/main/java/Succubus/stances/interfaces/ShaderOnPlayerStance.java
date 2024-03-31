package Succubus.stances.interfaces;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public interface ShaderOnPlayerStance {
    ShaderProgram getShader(AbstractPlayer p);
}
