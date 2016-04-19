package de.himbiss.ld35.world.fightsystem;

import de.himbiss.ld35.engine.Vector2D;

/**
 * Created by Vincent on 18.04.2016.
 */
public interface ShootingStrategy {

    int getShotDelayInMillis();

    float getMaxBulletSpeed();

    Vector2D calcDirectionOfShot();

    boolean isShooting();

}
