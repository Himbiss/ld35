package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.HasHitbox;
import de.himbiss.ld35.engine.Renderable;
import de.himbiss.ld35.engine.ResourceManager;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Vincent on 16.04.2016.
 */
public class Enemy extends Entity implements Renderable {
    private float speed = .05f;
    private float DELTA_MAX = 5f;
    protected String textureKey;


    public Enemy(float posX, float posY) {
        textureKey = "crate";
        width = 50;
        height = 100;
        coordX = posX;
        coordY = posY;
    }

    public void update(int delta) {

    }

    @Override
    public void collideWith(HasHitbox object, float deltaX, float deltaY) {
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Texture getTexture() {
        //TODO Animations
        return ResourceManager.getInstance().getTexture(textureKey);
    }
}
