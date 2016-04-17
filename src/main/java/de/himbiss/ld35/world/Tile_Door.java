package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.HasHitbox;
import de.himbiss.ld35.engine.ResourceManager;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Oneidavar on 17/04/2016.
 */
public class Tile_Door extends Tile implements Switchable, HasHitbox {
    boolean state = false;
    public Tile_Door(int x, int y) {
        textureKey = "door";
        coordx = getWidth() * x;
        coordy = getHeight() * y;
        state = false;
    }

    public Texture getTexture() {
        if(state) return ResourceManager.getInstance().getTexture(textureKey+"_open");
        return ResourceManager.getInstance().getTexture(textureKey);
    }

    @Override
    public void trigger() {
        state =true;
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
        if(!state) {
            System.out.print(object + "collided " + coordx + " - " + getHeight() + " - ");
            System.out.println(getHitBoxCoordX());
            if (object instanceof Entity) {
                Entity entity = (Entity) object;
                float hitboxOffsetX = Math.abs(entity.getHitBoxCoordX() - entity.getCoordX());
                if (deltaX < 0) {
                    entity.setCoordX(getCoordx() + getWidth() - hitboxOffsetX);
                } else if (deltaX > 0) {
                    entity.setCoordX(getCoordx() - entity.getHitboxWidth() - hitboxOffsetX);
                }

                float hitboxOffsetY = Math.abs(entity.getHitBoxCoordY() - entity.getCoordY());
                if (deltaY < 0) {
                    entity.setCoordY(getCoordy() + getHeight() - hitboxOffsetY);
                } else if (deltaY > 0) {
                    entity.setCoordY(getCoordy() - entity.getHitboxHeight() - hitboxOffsetY);
                }

                entity.setDeltas(0f, 0f);

            }
        }
    }
}
