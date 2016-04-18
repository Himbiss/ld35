package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.HasHitbox;
import de.himbiss.ld35.engine.Renderable;
import de.himbiss.ld35.engine.ResourceManager;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Oneidavar on 17/04/2016.
 */
public class Enemy_Spider extends Enemy implements Renderable{
    private float speed = .1f;
    private static final float DELTA_MAX = 3f;
    private long lastShot;
    private int attackSpeed;
    private int dist_move;
    private int dist_attack;
    private int dist_ignore;

    public Enemy_Spider(float posX, float posY) {
        super(posX, posY);
        textureKey="crate";
        width = 50;
        height = 50;



        attackSpeed = 2000;
        dist_move = 5;
        dist_attack = 7;
        dist_ignore = 10;
    }

    public void update(int delta) {
    //TODO Pathing towards player

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


    }

    public Texture getTexture() {
        //TODO Animations
        return ResourceManager.getInstance().getTexture(textureKey);
    }

}
