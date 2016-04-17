package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.HasHitbox;
import de.himbiss.ld35.engine.ResourceManager;
import de.himbiss.ld35.world.fightsystem.DoesDamage;
import de.himbiss.ld35.world.fightsystem.HasHealth;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Vincent on 16.04.2016.
 */
public class Crate extends Entity implements HasHealth {

    private int health = 3;

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
    public void collideWith(HasHitbox object, float deltaX, float deltaY) {
        if (object instanceof Entity) {
            this.deltaX += deltaX;
            this.deltaY += deltaY;
            ((Entity) object).setCoordX(((Entity) object).getPrevCoordX() - Engine.getInstance().getOffsetX());
            ((Entity) object).setCoordY(((Entity) object).getPrevCoordY() - Engine.getInstance().getOffsetY());
        }
        else {
            this.deltaX = 0f;
            this.deltaY = 0f;
        }
    }

    @Override
    public void applyGravity(float gravity) {
        super.applyGravity(gravity/2);
    }

    @Override
    public Texture getTexture() {
        return ResourceManager.getInstance().getTexture("crate");
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void applyDamage(DoesDamage damageObject) {
        health -= damageObject.getBaseDamage();
        if (health <= 0) {
            Engine.getInstance().getWorld().getEntities().remove(this);
        }
    }
}
