package de.himbiss.ld35.world.fightsystem;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.world.Entity;
import org.lwjgl.input.Mouse;

/**
 * Created by Vincent on 17.04.2016.
 */
public class ShootingDecorator extends EntityDecorator {

    private final Bullet bullet;
    private int shotSpeedInMillis;
    private long lastShot;
    private float maxBulletSpeed;

    public ShootingDecorator(Entity entity, Bullet bullet, int shotSpeedInMillis) {
        super(entity);
        this.bullet = bullet;
        this.shotSpeedInMillis = shotSpeedInMillis;
        this.lastShot = 0;
        this.maxBulletSpeed = 5f;
    }

    @Override
    public void update(int delta) {
        super.update(delta);
        if (Mouse.isButtonDown(0) && (System.currentTimeMillis() - lastShot) > shotSpeedInMillis) {
            lastShot = System.currentTimeMillis();
            int x = Mouse.getX();
            int y = Engine.getInstance().getDisplayMode().getHeight() - Mouse.getY();
            float pX = getCoordX();
            float pY = getCoordY();
            float dX = x - pX;
            float dY = y - pY;
            float mult = maxBulletSpeed / Math.max(Math.abs(dX), Math.abs(dY));
            dX *= mult;
            dY *= mult;
            float coordX = getCoordX() - Engine.getInstance().getOffsetX();
            float coordY = getCoordY() - Engine.getInstance().getOffsetY();
            Engine.getInstance().getWorld().getEntities().add(new Tear(this, coordX + (getWidth()/2), coordY + (getHeight()/2), dX, dY));
        }
    }
}
