package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.Renderable;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Oneidavar on 16/04/2016.
 */
public abstract class Tile implements Renderable {
    public static int TILE_SIZE = 10;

    protected String textureKey;

    public Tile() {
        textureKey = "crate";
    }

    public int getWidth() {
        return TILE_SIZE;
    }

    public int getHeight() {
        return TILE_SIZE;
    }

    public String getTextureKey() {
        return textureKey;
    }
}

