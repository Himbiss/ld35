package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.*;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Vincent on 16.04.2016.
 */
public abstract class Entity implements Renderable, Updatable, HasHitbox, HasPhysics {

    protected float coordX;
    protected float coordY;
    protected int height;
    protected int width;
    protected float deltaX;
    protected float deltaY;

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

    @Override
    public float getDeltaX() {
        return Math.abs(deltaX) > .1f ? deltaX : 0f;
    }

    @Override
    public float getDeltaY() {
        return Math.abs(deltaY) > .1f ? deltaY : 0f;
    }

    @Override
    public void setDeltas(float dX, float dY) {
        this.deltaX = dX;
        this.deltaY = dY;
    }

    @Override
    public void applyGravity(float gravity) {
        if (deltaX < 0) {
            deltaX = (deltaX + gravity) > -.1 ? 0 : (deltaX + gravity);
        }
        else {
            deltaX = (deltaX - gravity) < .1 ? 0 : (deltaX - gravity);
        }

        if (deltaY < 0) {
            deltaY = (deltaY + gravity) > -.1 ? 0 : (deltaY + gravity);
        }
        else {
            deltaY = (deltaY - gravity) < .1 ? 0 : (deltaY - gravity);
        }
    }
}
