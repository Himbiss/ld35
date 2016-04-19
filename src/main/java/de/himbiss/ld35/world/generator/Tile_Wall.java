package de.himbiss.ld35.world.generator;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.HasHitbox;
import de.himbiss.ld35.engine.Movement;
import de.himbiss.ld35.world.entity.Entity;

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
        if (object instanceof Entity) {
            Entity entity = (Entity) object;
            if (object instanceof Movement) {
                Movement hasMovement = ((Movement) object);
                entity.setCoordX((entity.getCoordX() - Engine.getInstance().getOffsetX()) - hasMovement.getDeltaX());
                entity.setCoordY((entity.getCoordY() - Engine.getInstance().getOffsetY()) - hasMovement.getDeltaY());
                hasMovement.setDeltas(0f, 0f);
            }

/*
            float hitboxOffsetX = Math.abs(entity.getHitBoxCoordX() - entity.getCoordX());
            if (deltaX < 0) {
                entity.setCoordX(getCoordx() + getWidth() - hitboxOffsetX+1);
            }
            else if (deltaX > 0) {
                entity.setCoordX(getCoordx() - entity.getHitboxWidth() - hitboxOffsetX-1);
            }

            float hitboxOffsetY = Math.abs(entity.getHitBoxCoordY() - entity.getCoordY());
            if (deltaY < 0) {
                entity.setCoordY(getCoordy() + getHeight() - hitboxOffsetY+1);
            }
            else if (deltaY > 0) {
                entity.setCoordY(getCoordy() - entity.getHitboxHeight() - hitboxOffsetY-1);
            }
            */


//            float gravity = Engine.getInstance().getGravity();
//            entity.setDeltas(0f, 0f);
//            float prevCoordX = entity.getPrevCoordX();
//            entity.setCoordX(prevCoordX - offsetX);
//            entity.setPrevCoordX(prevCoordX);
//            float prevCoordY = entity.getPrevCoordY();
//            entity.setCoordY(prevCoordY - Engine.getInstance().getOffsetY());
//            entity.setPrevCoordY(prevCoordY);
        }
    }

}
