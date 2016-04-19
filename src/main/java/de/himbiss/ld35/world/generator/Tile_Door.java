package de.himbiss.ld35.world.generator;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.HasHitbox;
import de.himbiss.ld35.engine.Movement;
import de.himbiss.ld35.engine.ResourceManager;
import de.himbiss.ld35.world.entity.Entity;
import de.himbiss.ld35.world.Switchable;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Oneidavar on 17/04/2016.
 */
public class Tile_Door extends Tile implements Switchable, HasHitbox {
    boolean isOpen = false;
    public Tile_Door(int x, int y) {
        textureKey = "door";
        coordx = getWidth() * x;
        coordy = getHeight() * y;
        isOpen = false;
    }

    public Texture getTexture() {
        if(isOpen) return ResourceManager.getInstance().getTexture("corridor");
        return ResourceManager.getInstance().getTexture(textureKey);
    }

    @Override
    public void trigger() {
        isOpen =true;
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

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void collideWith(HasHitbox object, float deltaX, float deltaY) {
        if(!isOpen) {

            if (object instanceof Entity) {
                Entity entity = (Entity) object;

                if (object instanceof Movement) {
                    Movement hasMovement = ((Movement) object);
                    entity.setCoordX((entity.getCoordX() - Engine.getInstance().getOffsetX()) - hasMovement.getDeltaX());
                    entity.setCoordY((entity.getCoordY() - Engine.getInstance().getOffsetY()) - hasMovement.getDeltaY());
                    hasMovement.setDeltas(0f, 0f);
                }
            }
        }
    }

    public void close(){
        isOpen = false;
    }
}
