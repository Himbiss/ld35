package de.himbiss.ld35.engine;

/**
 * Created by Vincent on 16.04.2016.
 */
public interface HasHitbox {

    float getHitboxWidth();

    float getHitboxHeight();

    float getHitBoxCoordX();

    float getHitBoxCoordY();

    void collideWith(HasHitbox object, float deltaX, float deltaY);
}
