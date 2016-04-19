package de.himbiss.ld35.world.fightsystem;

import de.himbiss.ld35.engine.AudioManager;
import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.HasHitbox;
import de.himbiss.ld35.engine.Vector2D;
import de.himbiss.ld35.world.entity.Entity;
import de.himbiss.ld35.world.generator.Tile_Door;

/**
 * Created by Vincent on 17.04.2016.
 */
public abstract class Bullet extends Entity implements DoesDamage, MovingStrategy {
    private Entity shotBy;
    private Vector2D direction;

    public Bullet(Entity shotBy, float posX, float posY, float dX, float dY) {
        direction = new Vector2D(dX, dY);
        this.coordX = posX;
        this.coordY = posY;
        this.width = 16;
        this.height = 16;
        this.shotBy = shotBy;
    }

    @Override
    public void collideWith(HasHitbox object, float deltaX, float deltaY) {
        if (! object.equals(shotBy)) {
            if (object instanceof HasHealth) {
                ((HasHealth) object).applyDamage(this, deltaX, deltaY);
                AudioManager.getInstance().getEffect("dummy").playAsSoundEffect(1.0f,1.0f,false);
            }
            else if (object instanceof Tile_Door && ((Tile_Door) object).isOpen()) {
                return;
            }
            Engine.getInstance().getWorld().getEntities().remove(this);
        }
    }

    @Override
    public void update(int delta) {
    }

    @Override
    public int getBaseDamage() {
        return 1;
    }

    @Override
    public Vector2D calcDirection(float deltaX, float deltaY, int deltaT) {
        return direction;
    }

    @Override
    public String getAnimation() {
        return null;
    }

}
