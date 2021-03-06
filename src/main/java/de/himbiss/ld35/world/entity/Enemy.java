package de.himbiss.ld35.world.entity;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.HasHitbox;
import de.himbiss.ld35.engine.Renderable;
import de.himbiss.ld35.engine.ResourceManager;
import de.himbiss.ld35.world.fightsystem.*;
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
        super(posX, posY, 50, 50);
        textureKey = "crate";
    }

    public void update(int delta) {

    }

    @Override
    public void collideWith(HasHitbox object, float deltaX, float deltaY) {
        /*
        if(object instanceof MovingDecorator) System.out.print("Player ");
        if(object instanceof Tear) System.out.print("Tear ");
        System.out.println("colided: " + getHitBoxCoordX() + " " + getHitBoxCoordY()
                + " : " + getHitboxWidth() + " " + getHitboxHeight()
                + " | " + object.getHitBoxCoordX() + " " + object.getHitBoxCoordY()
                + " : " + object.getHitboxWidth() + " " + object.getHitboxHeight());
                */
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Texture getTexture() {
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
        health -= damageObject.getBaseDamage();
        if (health <= 0) {
            System.out.println("Killed enemy: " + this.toString());
            Engine.getInstance().getWorld().getEntities().remove(this);
            Engine.getInstance().getWorld().getEntities().add(new Crystal(getDecorator(), coordX + (getWidth()/2), coordY + (getHeight()/2)));
        }
    }

    @Override
    public String toString() {
        return "Enemy";
    }
}
