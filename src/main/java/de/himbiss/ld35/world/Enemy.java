package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.HasHitbox;
import de.himbiss.ld35.engine.Renderable;
import de.himbiss.ld35.engine.ResourceManager;
import de.himbiss.ld35.world.fightsystem.DoesDamage;
import de.himbiss.ld35.world.fightsystem.HasHealth;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Vincent on 16.04.2016.
 */
public class Enemy extends Entity implements Renderable, HasHealth {
    private float speed = .05f;
    private float DELTA_MAX = 5f;
    protected String textureKey;
    private int health = 3;


    public Enemy(float posX, float posY) {
        textureKey = "crate";
        width = 50;
        height = 100;
        coordX = posX;
        coordY = posY;
    }

    public void update(int delta) {

    }

    @Override
    public void collideWith(HasHitbox object, float deltaX, float deltaY) {
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Texture getTexture() {
        //TODO Animations
        return ResourceManager.getInstance().getTexture(textureKey);
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
    public void applyDamage(DoesDamage damageObject, float dX, float dY) {
        this.deltaX += deltaX;
        this.deltaY += deltaY;
        health -= damageObject.getBaseDamage();
        if (health <= 0) {
            System.out.println("Killed enemy: " + this.toString());
            Engine.getInstance().getWorld().getEntities().remove(this);
        }
    }
}
