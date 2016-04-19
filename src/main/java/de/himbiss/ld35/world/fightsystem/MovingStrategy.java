package de.himbiss.ld35.world.fightsystem;

import de.himbiss.ld35.engine.Vector2D;

/**
 * Created by Vincent on 18.04.2016.
 */
public interface MovingStrategy {

    /**
     * @return The animation key. Only used if the entity implements IsAnimated
     */
    default String getAnimation() {
        return "freeze";
    }

    Vector2D calcDirection(float deltaX, float deltaY, int deltaT);

    float getDeltaMax();
}
