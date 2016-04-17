package de.himbiss.ld35.world.fightsystem;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.HasHitbox;
import de.himbiss.ld35.engine.ResourceManager;
import de.himbiss.ld35.world.Entity;
import de.himbiss.ld35.world.Tile_Door;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Vincent on 17.04.2016.
 */
public class Tear extends Bullet {

    public Tear(Entity shotBy, float posX, float posY, float dX, float dY) {
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
}
