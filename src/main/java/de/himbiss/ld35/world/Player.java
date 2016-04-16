package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.Hitbox;
import de.himbiss.ld35.engine.Engine;
import org.lwjgl.input.Keyboard;

/**
 * Created by Vincent on 16.04.2016.
 */
public class Player extends Entity {

    private static final float DELTA_MAX = 5f;
    private float speed;

    public Player() {
        width = 50;
        height = 100;
        speed = .2f;
        coordX =  (Engine.getInstance().getDisplayMode().getWidth() / 2) - (width / 2);
        coordY =  (Engine.getInstance().getDisplayMode().getHeight() / 2) - (height / 2);
    }

    public String getTextureKey() {
        return "dummy";
    }

    public void update(int delta) {
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            deltaX -= speed * delta;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            deltaX += speed * delta;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            deltaY += speed * delta;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            deltaY -= speed * delta;
        }

        if (Math.abs(deltaX) > DELTA_MAX) {
            deltaX = deltaX < 0 ? -DELTA_MAX : DELTA_MAX;
        }
        if (Math.abs(deltaY) > DELTA_MAX) {
            deltaY = deltaY < 0 ? -DELTA_MAX : DELTA_MAX;
        }
    }

    @Override
    public void collideWith(Hitbox object, float deltaX, float deltaY) {
    }
}
