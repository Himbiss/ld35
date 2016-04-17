package de.himbiss.ld35.world.fightsystem;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.HasHitbox;
import de.himbiss.ld35.engine.ResourceManager;
import de.himbiss.ld35.world.Entity;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Vincent on 17.04.2016.
 */
public class Tear extends Entity implements DoesDamage {

    float dX;
    float dY;

    public Tear(float posX, float posY, float dX, float dY) {
        this.deltaX = dX;
        this.deltaY = dY;
        this.dX = dX;
        this.dY = dY;
        this.coordX = posX;
        this.coordY = posY;
        this.width = 16;
        this.height = 16;
    }

    @Override
    public Texture getTexture() {
        return ResourceManager.getInstance().getTexture("tear");
    }

    @Override
    public void collideWith(HasHitbox object, float deltaX, float deltaY) {
        Engine.getInstance().getWorld().getEntities().remove(this);
        if ((! object.equals(Engine.getInstance().getWorld().getPlayer())) && object instanceof HasHealth) {
            ((HasHealth) object).applyDamage(this);
        }
    }

    @Override
    public void update(int delta) {
        this.deltaX = dX;
        this.deltaY = dY;
    }

    @Override
    public int getBaseDamage() {
        return 1;
    }
}
