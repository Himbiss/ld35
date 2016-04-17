package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.HasHitbox;
import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.ResourceManager;
import de.himbiss.ld35.world.animation.Animator;
import de.himbiss.ld35.world.fightsystem.DoesDamage;
import de.himbiss.ld35.world.fightsystem.HasHealth;
import de.himbiss.ld35.world.fightsystem.Tear;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.Texture;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vincent on 16.04.2016.
 */
public class Player extends Entity implements HasHealth {

    private static final float DELTA_MAX = 5f;
    private float speed = .1f;
    private SpriteSheet spriteSheet;
    private Map<String,Animation> animationMap;
    private Animation currentAnimation;
    private int health = 10;

    public Player() {
        width = 50;
        height = 50;
        coordX =  (Engine.getInstance().getDisplayMode().getWidth() / 2) - (width / 2);
        coordY =  (Engine.getInstance().getDisplayMode().getHeight() / 2) - (height / 2);
        animationMap = new HashMap<>();
        spriteSheet = ResourceManager.getInstance().getSpriteSheet("Albert", 16, 16);
        animationMap.put("walk_up", new Animation(spriteSheet, new int[] {0,0, 0,1, 1,1}, new int[] {100, 100, 100}));
        animationMap.put("walk_down", new Animation(spriteSheet, new int[] {2,0, 0,3, 1,3}, new int[] {100, 100, 100}));
        animationMap.put("walk_right", new Animation(spriteSheet, new int[] {1,0, 0,2, 1,2}, new int[] {100, 100, 100}));
        animationMap.put("walk_left", new Animation(spriteSheet, new int[] {3,0, 0,4, 1,4}, new int[] {100, 100, 100}));
        currentAnimation = animationMap.get("walk_down");
        currentAnimation.stop();
    }

    @Override
    public float getHitBoxCoordY() {
        return getCoordY() + (height / 2);
    }

    @Override
    public float getHitBoxCoordX() {
        return super.getHitBoxCoordX() + (width / 4);
    }

    @Override
    public float getHitboxHeight() {
        return super.getHitboxHeight() / 2;
    }

    @Override
    public float getHitboxWidth() {
        return super.getHitboxWidth() / 2;
    }

    public void update(int delta) {
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            deltaX -= speed * delta;
            handleAnimation("walk_left");
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            deltaX += speed * delta;
            handleAnimation("walk_right");
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            deltaY += speed * delta;
            handleAnimation("walk_down");
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            deltaY -= speed * delta;
            handleAnimation("walk_up");
        }
        else {
            if (! currentAnimation.isStopped()) {
                currentAnimation.stop();
            }
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            Engine.getInstance().getWorld().getEntities().add(new Tear(coordX, coordY, deltaX, deltaY));
        }

        if (Math.abs(deltaX) > DELTA_MAX) {
            deltaX = deltaX < 0 ? -DELTA_MAX : DELTA_MAX;
        }
        if (Math.abs(deltaY) > DELTA_MAX) {
            deltaY = deltaY < 0 ? -DELTA_MAX : DELTA_MAX;
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
        return true;
    }

    @Override
    public void render(Engine engine) {
        currentAnimation.draw(getCoordX(), getCoordY(), width, height);
    }

    @Override
    public void collideWith(HasHitbox object, float deltaX, float deltaY) {
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void applyDamage(DoesDamage damageObject) {
        this.health -= damageObject.getBaseDamage();
        if (this.health < 0) {
            System.out.println("Game Over");
            System.exit(0);
        }
    }
}
