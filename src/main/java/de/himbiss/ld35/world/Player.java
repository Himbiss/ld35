package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.Engine;
import org.lwjgl.input.Keyboard;

import java.security.Key;

/**
 * Created by Vincent on 16.04.2016.
 */
public class Player extends Entity {

    private float speed;

    public Player() {
        width = 50;
        height = 100;
        speed = 3f;
        coordX =  (Engine.getInstance().getDisplayMode().getWidth() / 2) - (width / 2);
        coordY =  (Engine.getInstance().getDisplayMode().getHeight() / 2) - (height / 2);
    }

    public String getTextureKey() {
        return "dummy";
    }

    public void update() {
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            coordX -= speed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            coordX += speed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            coordY -= speed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            coordY += speed;
        }
    }
}
