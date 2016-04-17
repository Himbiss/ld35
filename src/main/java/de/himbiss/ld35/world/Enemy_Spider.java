package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.HasHitbox;
import de.himbiss.ld35.engine.Renderable;
import de.himbiss.ld35.engine.ResourceManager;
import de.himbiss.ld35.world.fightsystem.HasHealth;
import de.himbiss.ld35.world.fightsystem.Tear;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Oneidavar on 17/04/2016.
 */
public class Enemy_Spider extends Enemy implements Renderable {
    private float speed = .05f;
    private static final float DELTA_MAX = 3f;
    private long lastShot;
    private int shotSpeed;

    public Enemy_Spider(float posX, float posY) {
        super(posX, posY);
        textureKey="crate";
        width = 50;
        height = 50;
    }

    public void update(int delta) {
    //TODO Pathing towards player

        World w = Engine.getInstance().getWorld();


        float dx = (getCoordX()-w.getPlayer().getCoordX());
        float dy = (getCoordY()-w.getPlayer().getCoordY());

        if(Math.sqrt(dx*dx+dy*dy)>5*25 ) {
            float grade = Math.abs(dx / (dx + dy));
            if (grade < 0) System.out.println("kleiner null");
            if (getCoordX() < w.getPlayer().getCoordX()) {
                //TODO set animation
                deltaX += speed * delta * grade;
            }
            if (getCoordX() > w.getPlayer().getCoordX()) {
                //TODO set animation
                deltaX -= speed * delta * grade;
            }
            if (getCoordY() < w.getPlayer().getCoordY()) {
                //TODO set animation
                deltaY += speed * delta * (1 - grade);
            }
            if (getCoordY() > w.getPlayer().getCoordY()) {
                //TODO set animation
                deltaY -= speed * delta * (1 - grade);
            }
            if (Math.abs(deltaX) > DELTA_MAX) {
                deltaX = deltaX < 0 ? -DELTA_MAX : DELTA_MAX;
            }
            if (Math.abs(deltaY) > DELTA_MAX) {
                deltaY = deltaY < 0 ? -DELTA_MAX : DELTA_MAX;
            }

        }
        if(Math.sqrt(dx*dx+dy*dy)<7*25 ){
            shotSpeed = 1000;
            if ((System.currentTimeMillis() - lastShot) > shotSpeed) {
                lastShot = System.currentTimeMillis();
                dx *= -0.1f;
                dy *= -0.1f;
                System.out.println("spider shooting tear: " + dx + "," + dy);
                Engine.getInstance().getWorld().getEntities().add(new Tear(this, coordX + (getWidth() / 2), coordY + (getHeight() / 2), dx, dy));
            }
        }


    }

    public Texture getTexture() {
        //TODO Animations
        return ResourceManager.getInstance().getTexture(textureKey);
    }

}
