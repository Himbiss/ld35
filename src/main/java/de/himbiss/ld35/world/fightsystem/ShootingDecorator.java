package de.himbiss.ld35.world.fightsystem;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.Vector2D;
import de.himbiss.ld35.world.entity.Entity;

/**
 * Created by Vincent on 17.04.2016.
 */
public class ShootingDecorator extends EntityDecorator {

    private final BulletFactory.BulletType bulletType;
    private long lastShot;

    public ShootingDecorator(Entity entity, BulletFactory.BulletType bulletType) {
        super(entity);
        this.bulletType = bulletType;
        this.lastShot = 0;
    }

    @Override
    public void update(int delta) {
        super.update(delta);
        Entity entity = getEntity();
        if (entity instanceof ShootingStrategy) {
            ShootingStrategy strategy = ((ShootingStrategy) entity);
            if (strategy.isShooting() && (System.currentTimeMillis() - lastShot) > strategy.getShotDelayInMillis()) {
                lastShot = System.currentTimeMillis();
                Vector2D shotDirection = strategy.calcDirectionOfShot();
                float mult = strategy.getMaxBulletSpeed() / shotDirection.abs().max();
                float dX = shotDirection.getX() * mult;
                float dY = shotDirection.getY() * mult;
                float coordX = getCoordX() - Engine.getInstance().getOffsetX();
                float coordY = getCoordY() - Engine.getInstance().getOffsetY();
                Entity bullet = BulletFactory.createBullet(bulletType, this, coordX + (getWidth() / 2), coordY + (getHeight() / 2), dX, dY);
                Engine.getInstance().getWorld().getEntities().add(bullet);
            }
        }
    }
}
