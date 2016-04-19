package de.himbiss.ld35.world.generator;

import de.himbiss.ld35.world.Switchable;

/**
 * Created by Oneidavar on 17/04/2016.
 */
public class Tile_Button extends Tile{
    Switchable[] children = new Switchable[0];
    public Tile_Button(int x, int y) {
        textureKey = "button";
        coordx = x;
        coordy = y;
    }

    public void setChildren(Switchable[] children) {
        this.children = children;
    }

    public void doStuff(){
        for(Switchable t:children){
            t.trigger();
        }
    }
}
