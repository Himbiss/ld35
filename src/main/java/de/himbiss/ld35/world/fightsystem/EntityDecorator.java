package de.himbiss.ld35.world.fightsystem;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.HasHitbox;
import de.himbiss.ld35.engine.Movement;
import de.himbiss.ld35.world.entity.Entity;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Vincent on 17.04.2016.
 */
public abstract class EntityDecorator extends Entity implements HasHealth, Movement {
    protected Entity entity;

    protected EntityDecorator(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void setHealth(int health) {
        if (entity instanceof HasHealth) {
            ((HasHealth)entity).setHealth(health);
        }
    }

    @Override
    public int getHealth() {
        if (entity instanceof HasHealth) {
            return ((HasHealth)entity).getHealth();
        }
        return -1;
    }

    @Override
    public void setCoordX(float coordX) {
        entity.setCoordX(coordX);
    }

    @Override
    public void setCoordY(float coordY) {
        entity.setCoordY(coordY);
    }

    @Override
    public void setDeltas(float dX, float dY) {
        if (entity instanceof Movement) {
            ((Movement) entity).setDeltas(dX, dY);
        }
    }

    @Override
    public void setPrevCoordX(float prevCoordX) {
        entity.setPrevCoordX(prevCoordX);
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void setEntityR(Entity entity) {
        if (this.entity instanceof EntityDecorator) {
            ((EntityDecorator) this.entity).setEntityR(entity);
            return;
        }
        this.entity = entity;
        return;
    }

    @Override
    public void setPrevCoordY(float prevCoordY) {
        entity.setCoordY(prevCoordY);
    }

    @Override
    public void applyDamage(DoesDamage damageObject, float dX, float dY) {
        if (entity instanceof HasHealth) {
            ((HasHealth) entity).applyDamage(damageObject, dX, dY);
        }
    }

    @Override
    public float getCoordX() {
        return entity.getCoordX();
    }

    @Override
    public float getCoordY() {
        return entity.getCoordY();
    }

    @Override
    public float getDeltaX() {
        if (entity instanceof Movement) {
            return ((Movement) entity).getDeltaX();
        }
        return 0f;
    }

    @Override
    public float getDeltaY() {
        if (entity instanceof Movement) {
            return ((Movement) entity).getDeltaY();
        }
        return 0f;
    }

    @Override
    public float getPrevCoordX() {
        return entity.getPrevCoordX();
    }

    @Override
    public float getPrevCoordY() {
        return entity.getPrevCoordY();
    }

    public Entity getEntity() {
        if (entity instanceof EntityDecorator) {
            return ((EntityDecorator) entity).getEntity();
        }
        return entity;
    }

    @Override
    public float getHitboxWidth() {
        return entity.getHitboxWidth();
    }

    @Override
    public float getHitboxHeight() {
        return entity.getHitboxHeight();
    }

    @Override
    public float getHitBoxCoordX() {
        return entity.getHitBoxCoordX();
    }

    @Override
    public float getHitBoxCoordY() {
        return entity.getHitBoxCoordY();
    }

    @Override
    public void collideWith(HasHitbox object, float deltaX, float deltaY) {
        entity.collideWith(object, deltaX, deltaY);
    }

    @Override
    public int getWidth() {
        return entity.getWidth();
    }

    @Override
    public int getHeight() {
        return entity.getHeight();
    }

    @Override
    public Texture getTexture() {
        return entity.getTexture();
    }

    @Override
    public boolean renderMyself() {
        return entity.renderMyself();
    }

    @Override
    public void render(Engine engine) {
        entity.render(engine);
    }

    @Override
    public void update(int delta) {
        entity.update(delta);
    }

    @Override
    public void applyGravity(float gravity) {
        if (entity instanceof Movement) {
            ((Movement) entity).applyGravity(gravity);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return entity.equals(obj);
    }

    @Override
    public String toString() {
        return entity.toString();
    }
}
