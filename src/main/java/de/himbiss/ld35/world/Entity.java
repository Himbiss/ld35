package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.Renderable;

/**
 * Created by Vincent on 16.04.2016.
 */
public abstract class Entity implements Renderable, Updatable {

    protected float coordX;
    protected float coordY;
    protected int height;
    protected int width;

    public Entity() {
    }

    public float getCoordX() {
        return coordX;
    }

    public float getCoordY() {
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
}
