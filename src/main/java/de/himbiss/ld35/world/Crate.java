package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.HasHitbox;
import de.himbiss.ld35.engine.ResourceManager;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Vincent on 16.04.2016.
 */
public class Crate extends Entity {

    public Crate(float posX, float posY) {
        this.coordX = posX;
        this.coordY = posY;
        this.width = 60;
        this.height = 60;
    }

    @Override
    public void update(int delta) {

    }

    @Override
    public void collideWith(HasHitbox object, float deltaX, float deltaY) {
        this.deltaX += deltaX;
        this.deltaY += deltaY;
    }

    @Override
    public void applyGravity(float gravity) {
        super.applyGravity(gravity/2);
    }

    @Override
    public Texture getTexture() {
        return ResourceManager.getInstance().getTexture("crate");
    }
}
