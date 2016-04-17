package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.Renderable;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Oneidavar on 16/04/2016.
 */
public abstract class Tile implements Renderable {
    public static int TILE_SIZE = 5;
    public int coordx;
    public int coordy;

    protected String textureKey;

    public int getCoordy() {
        return coordy;
    }

    public int getCoordx() {
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

    public String getTextureKey() {
        return textureKey;
    }
}

