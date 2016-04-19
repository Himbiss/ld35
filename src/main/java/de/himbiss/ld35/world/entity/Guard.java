package de.himbiss.ld35.world.entity;

import de.himbiss.ld35.engine.*;
import de.himbiss.ld35.world.World;
import de.himbiss.ld35.world.fightsystem.MovingStrategy;
import de.himbiss.ld35.world.fightsystem.ShootingStrategy;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.Texture;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oneidavar on 17/04/2016.
 */
public class Guard extends Enemy implements HasScript, IsAnimated, MovingStrategy, ShootingStrategy {
    public float speed = .1f;
    public static final float DELTA_MAX = 3f;
    public long lastShot;
    public int attackSpeed;
    public int dist_move;
    public int dist_attack;
    public int dist_ignore;
    public String script;
    private String animationKey = "freeze";

    public Guard(float posX, float posY) {
        super(posX, posY);
        textureKey="crate";
        width = 50;
        height = 50;
        attackSpeed = 2000;
        dist_move = 5;
        dist_attack = 7;
        dist_ignore = 10;
        script = ResourceManager.getInstance().getScript("spider");
        Engine.getInstance().invokeScript(this);
    }

    public void update(int delta) {
    //TODO Pathing towards player
        /*
        World w = Engine.getInstance().getWorld();

        float tX = getCoordX()-Engine.getInstance().getOffsetX();
        float tY = getCoordY()-Engine.getInstance().getOffsetY();
        float pX = w.getPlayer().getCoordX()-Engine.getInstance().getOffsetX();
        float pY = w.getPlayer().getCoordY()-Engine.getInstance().getOffsetY();

        float dx = Math.abs(tX-pX);
        float dy = Math.abs(tY-pY);


        if(Math.sqrt(dx*dx+dy*dy)>dist_move*50 && Math.sqrt(dx*dx+dy*dy)<dist_ignore*50 ) {
            float grade = (dx / (dx + dy));

            if (tX < pX ) {
                //TODO set animation
                deltaX += speed * delta * grade;
            } else if (tX > pX ) {
                //TODO set animation
                deltaX -= speed * delta * grade;
            }
            if (tY < pY) {
                //TODO set animation
                deltaY += speed * delta * (1-grade);
            } else if (tY > pY) {
                //TODO set animation
                deltaY -= speed * delta * (1-grade);
            }

            if (Math.abs(deltaX) > DELTA_MAX) {
                deltaX = deltaX < 0 ? -DELTA_MAX : DELTA_MAX;
            }
            if (Math.abs(deltaY) > DELTA_MAX) {
                deltaY = deltaY < 0 ? -DELTA_MAX : DELTA_MAX;
            }

        }
        if(Math.sqrt(dx*dx+dy*dy)<dist_attack*50){

            if ((System.currentTimeMillis() - lastShot) > attackSpeed) {
                lastShot = System.currentTimeMillis();
                dx *= -0.1f;
                dy *= -0.1f;
                //System.out.println("spider shooting tear: " + dx + "," + dy);
                //Engine.getInstance().getWorld().getEntities().add(new Tear(this, coordX + (getWidth() / 2), coordY + (getHeight() / 2), dx, dy));
            }
        }
    */

    }

    public Texture getTexture() {
        return ResourceManager.getInstance().getTexture(textureKey);
    }

    @Override
    public String toString() {
        return "Guard";
    }

    @Override
    public String getScript() {
        return script;
    }

    @Override
    public void setScript(String script) {
        this.script = script;
    }


    @Override
    public Map<String, Animation> getAnimationMap() {
        Map<String, Animation> animationMap = new HashMap<>();
        SpriteSheet spriteSheet = ResourceManager.getInstance().getSpriteSheet("Guard", 16, 16);
        animationMap.put("walk_up", new Animation(spriteSheet, new int[] {0,0, 0,1, 1,1}, new int[] {100, 100, 100}));
        animationMap.put("walk_down", new Animation(spriteSheet, new int[] {2,0, 0,3, 1,3}, new int[] {100, 100, 100}));
        animationMap.put("walk_right", new Animation(spriteSheet, new int[] {1,0, 0,2, 1,2}, new int[] {100, 100, 100}));
        animationMap.put("walk_left", new Animation(spriteSheet, new int[] {3,0, 0,4, 1,4}, new int[] {100, 100, 100}));
        return animationMap;
    }

    @Override
    public Vector2D calcDirection(float deltaX, float deltaY, int deltaT) {
        World w = Engine.getInstance().getWorld();

        float tX = getCoordX()-Engine.getInstance().getOffsetX();
        float tY = getCoordY()-Engine.getInstance().getOffsetY();
        float pX = w.getPlayer().getCoordX()-Engine.getInstance().getOffsetX();
        float pY = w.getPlayer().getCoordY()-Engine.getInstance().getOffsetY();

        float dx = Math.abs(tX-pX);
        float dy = Math.abs(tY-pY);


        if(Math.sqrt(dx*dx+dy*dy)>dist_move*50 && Math.sqrt(dx*dx+dy*dy)<dist_ignore*50 ) {
            float grade = (dx / (dx + dy));

            if (tX < pX ) {
                animationKey = "walk_left";
                deltaX += speed * deltaT * grade;
            } else if (tX > pX ) {
                animationKey = "walk_right";
                deltaX -= speed * deltaT * grade;
            }

            if (tY < pY) {
                animationKey = "walk_down";
                deltaY += speed * deltaT * (1-grade);
            } else if (tY > pY) {
                animationKey = "walk_up";
                deltaY -= speed * deltaT * (1-grade);
            }
        }
        else {
            animationKey = "freeze";
        }
        return new Vector2D(deltaX, deltaY);
    }

    @Override
    public String getAnimation() {
        return animationKey;
    }

    @Override
    public float getDeltaMax() {
        return 3f;
    }

    @Override
    public int getShotDelayInMillis() {
        return 500;
    }

    @Override
    public float getMaxBulletSpeed() {
        return 4f;
    }

    @Override
    public Vector2D calcDirectionOfShot() {
        World w = Engine.getInstance().getWorld();
        float x = w.getPlayer().getCoordX()-Engine.getInstance().getOffsetX();
        float y = w.getPlayer().getCoordY()-Engine.getInstance().getOffsetY();
        float pX = x - coordX;
        float pY = y - coordY;
        return new Vector2D(pX, pY);
    }

    @Override
    public boolean isShooting() {
        World w = Engine.getInstance().getWorld();

        float tX = getCoordX()-Engine.getInstance().getOffsetX();
        float tY = getCoordY()-Engine.getInstance().getOffsetY();
        float pX = w.getPlayer().getCoordX()-Engine.getInstance().getOffsetX();
        float pY = w.getPlayer().getCoordY()-Engine.getInstance().getOffsetY();

        float dx = Math.abs(tX-pX);
        float dy = Math.abs(tY-pY);

        return Math.sqrt(dx*dx+dy*dy)<dist_attack*50;
    }
}
