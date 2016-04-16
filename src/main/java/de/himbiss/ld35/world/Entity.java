package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.Hitbox;
import de.himbiss.ld35.engine.Renderable;

/**
 * Created by Vincent on 16.04.2016.
 */
public abstract class Entity implements Renderable, Updatable, Hitbox {

    protected float coordX;
    protected float coordY;
    protected int height;
    protected int width;

    public Entity() {
    }

    public float getHitBoxCoordX() {
        return coordX;
    }

    public float getHitBoxCoordY() {
        return coordY;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getTextureKey() {
        return "dummy";
    }

    public void setCoordX(float coordX) {
        this.coordX = coordX;
    }

    public void setCoordY(float coordY) {
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
}
