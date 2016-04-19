package de.himbiss.ld35.world.generator;

import de.himbiss.ld35.engine.Renderable;
import de.himbiss.ld35.engine.ResourceManager;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Oneidavar on 16/04/2016.
 */
public abstract class Tile implements Renderable {
    public static int TILE_SIZE = 50;
    public float coordx;
    public float coordy;

    protected String textureKey;

    public float getCoordy() {
        return coordy;
    }

    public float getCoordx() {
        return coordx;
    }

    public Tile() {
        textureKey = "crate";
        coordx = 0;
        coordy = 0;
    }

    public int getWidth() {
        return TILE_SIZE;
    }

    public int getHeight() {
        return TILE_SIZE;
    }

    public Texture getTexture() {
        return ResourceManager.getInstance().getTexture(textureKey);
    }
}

