package de.himbiss.ld35.world.animation;

import org.newdawn.slick.*;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Vincent on 16.04.2016.
 */
public class Animator {
    private final SpriteSheet spriteSheet;
    private String animation;

    public Animator(SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public void setAnimation(String animation) {
        this.animation = animation;
    }

    public Texture getTexture() {
        return spriteSheet.getSprite(3, 4).getTexture();
    }

    public void step(int delta) {

    }
}
