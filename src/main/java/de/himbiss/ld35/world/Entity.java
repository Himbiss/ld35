package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.*;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Vincent on 16.04.2016.
 */
public abstract class Entity implements Renderable, Updatable, HasHitbox, HasPhysics {

    protected float coordX;
    protected float coordY;
    protected float prevCoordX;
    protected float prevCoordY;
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

    @Override
    public float getDeltaX() {
        return deltaX;
    }

    @Override
    public float getDeltaY() {
        return deltaY;
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

    public void setPrevCoordY(float prevCoordY) {
        this.prevCoordY = prevCoordY;
    }

    public void setPrevCoordX(float prevCoordX) {
        this.prevCoordX = prevCoordX;
    }
}
