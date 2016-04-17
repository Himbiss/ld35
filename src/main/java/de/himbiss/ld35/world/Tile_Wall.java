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
            //deltaX = deltaX == 0 ? 0 : deltaX < 0 ? deltaX - 1f : deltaX + 1f;
            //deltaY = deltaY == 0 ? 0 : deltaY < 0 ? deltaY - 1f : deltaY + 1f;
            ((Entity) object).setDeltas(0, 0);
            ((Entity) object).setCoordX(((Entity) object).getCoordX() - Engine.getInstance().getOffsetX() - deltaX);
            ((Entity) object).setCoordY(((Entity) object).getCoordY() - Engine.getInstance().getOffsetY()- deltaY);
        }
    }

}
