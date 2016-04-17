package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.HasHitbox;
import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.ResourceManager;
import de.himbiss.ld35.world.fightsystem.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vincent on 16.04.2016.
 */
public class Player extends Entity implements HasHealth {

    private int health = 10;

    public Player() {
        width = 50;
        height = 50;
        coordX =  (Engine.getInstance().getDisplayMode().getWidth() / 2) - (width / 2);
        coordY =  (Engine.getInstance().getDisplayMode().getHeight() / 2) - (height / 2);
    }

    public Map<String, Animation> buildAnimationMap() {
        Map<String, Animation> animationMap = new HashMap<>();
        SpriteSheet spriteSheet = ResourceManager.getInstance().getSpriteSheet("Albert", 16, 16);
        animationMap.put("walk_up", new Animation(spriteSheet, new int[] {0,0, 0,1, 1,1}, new int[] {100, 100, 100}));
        animationMap.put("walk_down", new Animation(spriteSheet, new int[] {2,0, 0,3, 1,3}, new int[] {100, 100, 100}));
        animationMap.put("walk_right", new Animation(spriteSheet, new int[] {1,0, 0,2, 1,2}, new int[] {100, 100, 100}));
        animationMap.put("walk_left", new Animation(spriteSheet, new int[] {3,0, 0,4, 1,4}, new int[] {100, 100, 100}));
        return animationMap;
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
    public void applyDamage(DoesDamage damageObject, float dX, float dY) {
        this.deltaX += deltaX;
        this.deltaY += deltaY;
        this.health -= damageObject.getBaseDamage();
        if (this.health < 0) {
            System.out.println("Game Over");
            System.exit(0);
        }
    }
}
