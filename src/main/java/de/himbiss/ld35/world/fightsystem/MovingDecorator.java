package de.himbiss.ld35.world.fightsystem;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.IsAnimated;
import de.himbiss.ld35.engine.Vector2D;
import de.himbiss.ld35.world.entity.Entity;
import org.newdawn.slick.Animation;

import java.util.Map;

/**
 * Created by Vincent on 17.04.2016.
 */
public class MovingDecorator extends EntityDecorator {

    protected float deltaX = 0;
    protected float deltaY = 0;
    protected float deltaMax = 2f;
    private Map<String,Animation> animationMap;
    private Animation currentAnimation;

    public MovingDecorator(Entity entity) {
        super(entity);
        if (! (getEntity() instanceof MovingStrategy)) {
            throw new IllegalArgumentException("entity has to implement MovingStrategy");
        }
        if (getEntity() instanceof IsAnimated) {
            this.animationMap = ((IsAnimated) getEntity()).getAnimationMap();
            this.currentAnimation = animationMap.get("walk_down");
            this.currentAnimation.stop();
        }
        this.deltaMax = ((MovingStrategy) getEntity()).getDeltaMax();
    }

    @Override
    public void update(int delta) {
        super.update(delta);

        Entity entity = getEntity();
        if (entity instanceof MovingStrategy) {
            MovingStrategy strategy = ((MovingStrategy) entity);

            // handle animation
            if (renderMyself() && animationMap != null) {
                String animation = strategy.getAnimation();
                if (animation.equals("freeze")) {
                    if (!currentAnimation.isStopped()) {
                        currentAnimation.stop();
                    }
                } else {
                    handleAnimation(animation);
                }
            }

            // handle deltas
            Vector2D direction = strategy.calcDirection(deltaX, deltaY, delta);
            float deltaX = direction.getX();
            float deltaY = direction.getY();

            if (Math.abs(deltaX) > deltaMax) {
                deltaX = deltaX < 0 ? -deltaMax : deltaMax;
            }
            if (Math.abs(deltaY) > deltaMax) {
                deltaY = deltaY < 0 ? -deltaMax : deltaMax;
            }
            setDeltas(deltaX, deltaY);
        }
    }

    private void handleAnimation(String animation) {
        currentAnimation = animationMap.get(animation);
        if (currentAnimation.isStopped()) {
            currentAnimation.start();
        }
    }

    @Override
    public boolean renderMyself() {
        return super.renderMyself() || animationMap != null;
    }

    @Override
    public void render(Engine engine) {
        super.render(engine);
        if (currentAnimation != null) {
            currentAnimation.draw(entity.getCoordX(), entity.getCoordY(), entity.getWidth(), entity.getHeight());
        }
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
}
