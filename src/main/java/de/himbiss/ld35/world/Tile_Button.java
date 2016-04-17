package de.himbiss.ld35.world;

/**
 * Created by Oneidavar on 17/04/2016.
 */
public class Tile_Button extends Tile{
    Switchable[] children;
    public Tile_Button(int x, int y, int c) {
        textureKey = "button";
        coordx = x;
        coordy = y;
        children = new Switchable[c];
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
