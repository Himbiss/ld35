package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.Hitbox;

/**
 * Created by Oneidavar on 16/04/2016.
 */
public class Tile_Wall extends Tile implements Hitbox {
    public Tile_Wall(int x, int y) {
        textureKey = "wall";
        coordx = x;
        coordy = y;
    }

    @Override
    public float getHitboxWidth() {
        return getWidth();
    }

    @Override
    public float getHitboxHeight() {
        return getHeight();
    }

    @Override
    public float getHitBoxCoordX() {
        return Engine.getInstance().getOffsetX() + coordx*getWidth();
    }

    @Override
    public float getHitBoxCoordY() {
        return Engine.getInstance().getOffsetX() + coordy*getHeight();
    }

    @Override
    public void collideWith(Hitbox object, float deltaX, float deltaY) {

        System.out.print(object + "collided " + coordx + " - " + getHeight() + " - ");
        System.out.println(getHitBoxCoordX());
    }

}
