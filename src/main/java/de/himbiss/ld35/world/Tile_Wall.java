package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.HasHitbox;

/**
 * Created by Oneidavar on 16/04/2016.
 */
public class Tile_Wall extends Tile implements HasHitbox {
    public Tile_Wall(int x, int y) {
        textureKey = "wall";
        coordx = getWidth() * x;
        coordy = getHeight() * y;
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
        return Engine.getInstance().getOffsetX() + coordx;
    }

    @Override
    public float getHitBoxCoordY() {
        return Engine.getInstance().getOffsetY() + coordy;
    }

    @Override
    public void collideWith(HasHitbox object, float deltaX, float deltaY) {
        System.out.print(object + "collided " + coordx + " - " + getHeight() + " - ");
        System.out.println(getHitBoxCoordX());
        if (object instanceof Entity) {
            ((Entity) object).setDeltas(0f, 0f);
        }
    }

}
