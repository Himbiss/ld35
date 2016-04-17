package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.ResourceManager;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Oneidavar on 17/04/2016.
 */
public class Tile_Door extends Tile implements Switchable {
    boolean state = false;
    public Tile_Door(int x, int y) {
        textureKey = "door";
        coordx = x;
        coordy = y;
        state = false;
    }

    public Texture getTexture() {
        if(state) return ResourceManager.getInstance().getTexture(textureKey+"_open");
        return ResourceManager.getInstance().getTexture(textureKey);
    }

    @Override
    public void trigger() {
        state =true;
    }
}
