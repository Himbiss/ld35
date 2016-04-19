package de.himbiss.ld35.world.fightsystem;

import de.himbiss.ld35.world.entity.Entity;

/**
 * Created by Vincent on 19.04.2016.
 */
public class BulletFactory {
    public enum BulletType { TEAR }

    public static Entity createBullet(BulletType type, Entity shotBy, float posX, float posY, float dX, float dY) {
        Bullet bullet = null;

        switch (type) {
            case TEAR:
                bullet = new Tear(shotBy, posX, posY, dX, dY);
                break;
            default:
                throw new IllegalArgumentException("invalid bullet type!");
        }

        return new MovingDecorator(bullet);
    }
}
