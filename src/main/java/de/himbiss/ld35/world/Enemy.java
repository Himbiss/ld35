package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.HasHitbox;
import org.lwjgl.input.Keyboard;

/**
 * Created by Vincent on 16.04.2016.
 */
public class Enemy extends Entity {
    private float speed = .05f;
    private float DELTA_MAX = 5f;

    public Enemy(float posX, float posY) {
        width = 50;
        height = 100;
        coordX = posX;
        coordY = posY;
    }

    public void update(int delta) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            deltaX -= speed * delta;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            deltaX += speed * delta;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            deltaY += speed * delta;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
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
    public void collideWith(HasHitbox object, float deltaX, float deltaY) {

    }
}
