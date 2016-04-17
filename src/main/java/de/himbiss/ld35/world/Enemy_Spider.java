package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.HasHitbox;
import de.himbiss.ld35.engine.Renderable;
import de.himbiss.ld35.engine.ResourceManager;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Oneidavar on 17/04/2016.
 */
public class Enemy_Spider extends Enemy implements Renderable {
    private float speed = .05f;
    private static final float DELTA_MAX = 3f;

    public Enemy_Spider(float posX, float posY) {
        super(posX, posY);
        textureKey="crate";
        width = 100;
        height = 50;
    }

    public void update(int delta) {
    //TODO Pathing towards player

        World w = Engine.getInstance().getWorld();
        if(getCoordX() < w.getPlayer().getCoordX()) {
            //TODO set animation
            deltaX += speed * delta;
        }
        if(getCoordX() > w.getPlayer().getCoordX()) {
            //TODO set animation
            deltaX -= speed * delta;
        }
        if(getCoordY() < w.getPlayer().getCoordY()) {
            //TODO set animation
            deltaY += speed * delta;
        }
        if(getCoordY() > w.getPlayer().getCoordY()) {
            //TODO set animation
            deltaY -= speed * delta;
        }
        if (Math.abs(deltaX) > DELTA_MAX) {
            deltaX = deltaX < 0 ? -DELTA_MAX : DELTA_MAX;
        }
        if (Math.abs(deltaY) > DELTA_MAX) {
            deltaY = deltaY < 0 ? -DELTA_MAX : DELTA_MAX;
        }
    }

    public Texture getTexture() {
        //TODO Animations
        return ResourceManager.getInstance().getTexture(textureKey);
    }

}
