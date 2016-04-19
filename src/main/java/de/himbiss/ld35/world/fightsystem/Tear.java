package de.himbiss.ld35.world.fightsystem;

import de.himbiss.ld35.engine.ResourceManager;
import de.himbiss.ld35.world.entity.Entity;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Vincent on 17.04.2016.
 */
public class Tear extends Bullet {

    Tear(Entity shotBy, float posX, float posY, float dX, float dY) {
        super(shotBy, posX, posY, dX, dY);
    }

    @Override
    public Texture getTexture() {
        return ResourceManager.getInstance().getTexture("tear");
    }

    @Override
    public int getBaseDamage() {
        return 1;
    }

    @Override
    public float getDeltaMax() {
        return Float.MAX_VALUE;
    }
}
