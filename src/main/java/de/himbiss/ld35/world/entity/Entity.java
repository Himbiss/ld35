package de.himbiss.ld35.world.entity;

import de.himbiss.ld35.engine.*;
import de.himbiss.ld35.world.Updatable;
import de.himbiss.ld35.world.fightsystem.DoesDamage;
import de.himbiss.ld35.world.fightsystem.EntityDecorator;
import de.himbiss.ld35.world.fightsystem.Tear;
import org.newdawn.slick.opengl.Texture;

import javax.script.ScriptException;

/**
 * Created by Vincent on 16.04.2016.
 */
public abstract class Entity implements Renderable, Updatable, HasHitbox {

    protected float coordX;
    protected float coordY;
    protected float prevCoordX;
    protected float prevCoordY;
    protected int height;
    protected int width;

    public Entity() {
    }

    public float getHitBoxCoordX() {
        return getCoordX();
    }

    public float getHitBoxCoordY() {
        return getCoordY();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Texture getTexture() {
        return ResourceManager.getInstance().getTexture("dummy");
    }

    public void setCoordX(float coordX) {
        this.prevCoordX = this.coordX;
        this.coordX = coordX;
    }

    public void setCoordY(float coordY) {
        this.prevCoordY = this.coordY;
        this.coordY = coordY;
    }

    @Override
    public float getHitboxHeight() {
        return getHeight();
    }

    @Override
    public float getHitboxWidth() {
        return getWidth();
    }

    public float getCoordX() {
        return Engine.getInstance().getOffsetX() + coordX;
    }

    public float getCoordY() {
        return Engine.getInstance().getOffsetY() + coordY;
    }

    public float getPrevCoordX() {
        return Engine.getInstance().getOffsetX() + prevCoordX;
    }

    public float getPrevCoordY() {
        return Engine.getInstance().getOffsetY() + prevCoordY;
    }


    public void setPrevCoordY(float prevCoordY) {
        this.prevCoordY = prevCoordY;
    }

    public void setPrevCoordX(float prevCoordX) {
        this.prevCoordX = prevCoordX;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EntityDecorator) {
            return equals(((EntityDecorator) obj).getEntity());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Entity";
    }
}
