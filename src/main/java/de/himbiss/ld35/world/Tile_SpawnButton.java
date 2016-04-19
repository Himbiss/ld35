package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.ResourceManager;
import de.himbiss.ld35.world.generator.RoomStrukt;
import de.himbiss.ld35.world.generator.Tile;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Oneidavar on 18/04/2016.
 */
public class Tile_SpawnButton extends Tile {
    private RoomStrukt myroom;
    private boolean isUsed;
    public Tile_SpawnButton(int x, int y, RoomStrukt room) {
        textureKey = "spawnbutton";
        coordx = x;
        coordy = y;
        myroom = room;
        isUsed = false;
    }

    public void doStuff(){
        if(!isUsed){
            isUsed = true;
            myroom.closeDoors();
            //TODO Spawn
        }
    }

    public Texture getTexture() {
        if(isUsed) return ResourceManager.getInstance().getTexture("floor");
        return ResourceManager.getInstance().getTexture(textureKey);
    }

}
