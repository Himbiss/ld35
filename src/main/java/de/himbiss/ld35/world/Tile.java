package de.himbiss.ld35.world;

import org.newdawn.slick.opengl.Texture;

/**
 * Created by Oneidavar on 16/04/2016.
 */
public class Tile {
    public static int TILE_SIZE = 50;
    private String textureKey;

    public Tile() {
        textureKey = "crate";
    }

    public String getTextureKey() {
        return textureKey;
    }
}

