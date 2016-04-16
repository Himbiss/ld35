package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.Hitbox;

/**
 * Created by Vincent on 16.04.2016.
 */
public class Crate extends Entity {

    public Crate(float posX, float posY) {
        this.coordX = posX;
        this.coordY = posY;
        this.width = 60;
        this.height = 60;
    }

    @Override
    public void update(int delta) {

    }

    @Override
    public void collideWith(Hitbox object, float deltaX, float deltaY) {
        coordX -= deltaX;
        coordY -= deltaY;
    }
}
