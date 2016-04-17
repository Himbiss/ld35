package de.himbiss.ld35.world.fightsystem;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.world.Entity;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Animation;

import java.util.Map;

/**
 * Created by Vincent on 17.04.2016.
 */
public class MovingDecorator extends EntityDecorator {

    private float deltaMax = 5f;
    private float speed = .1f;
    private Map<String,Animation> animationMap;
    private Animation currentAnimation;

    public MovingDecorator(Entity entity, float deltaMax, float speed, Map<String, Animation> animationMap) {
        super(entity);
        this.deltaMax = deltaMax;
        this.speed = speed;
        this.animationMap = animationMap;
        this.currentAnimation = animationMap.get("walk_down");
        this.currentAnimation.stop();
    }

    @Override
    public void update(int delta) {
        super.update(delta);
        float acceleration = speed * delta;
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            setDeltas(getDeltaX() - acceleration, getDeltaY());
            handleAnimation("walk_left");
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            setDeltas(getDeltaX() + acceleration, getDeltaY());
            handleAnimation("walk_right");
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            setDeltas(getDeltaX(), getDeltaY() + acceleration);
            handleAnimation("walk_down");
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            setDeltas(getDeltaX(), getDeltaY() - acceleration);
            handleAnimation("walk_up");
        }
        else {
            if (! currentAnimation.isStopped()) {
                currentAnimation.stop();
            }
        }

        float deltaX = getDeltaX();
        float deltaY = getDeltaY();
        if (Math.abs(deltaX) > deltaMax) {
            deltaX = deltaX < 0 ? -deltaMax : deltaMax;
        }
        if (Math.abs(deltaY) > deltaMax) {
            deltaY = deltaY < 0 ? -deltaMax : deltaMax;
        }
        setDeltas(deltaX, deltaY);
    }

    private void handleAnimation(String animation) {
        currentAnimation = animationMap.get(animation);
        if (currentAnimation.isStopped()) {
            currentAnimation.start();
        }
    }

    @Override
    public boolean renderMyself() {
        return true;
    }

    @Override
    public void render(Engine engine) {
        super.render(engine);
        currentAnimation.draw(entity.getCoordX(), entity.getCoordY(), entity.getWidth(), entity.getHeight());
    }
}
