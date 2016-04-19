package de.himbiss.ld35.engine;

/**
 * Created by Vincent on 16.04.2016.
 */
public interface Movement {

    float getDeltaX();

    float getDeltaY();

    void applyGravity(float gravity);

    void setDeltas(float dX, float dY);
}
