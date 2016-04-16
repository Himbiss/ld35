package de.himbiss.ld35.world;

/**
 * Created by Oneidavar on 16/04/2016.
 */
public class RoomStrukt{
    public int width;
    public int height;
    public int posx;
    public int posy;

    public int midX(){
        return (2*posx+width)/2;
    }

    public int midY(){
        return (2*posy+height)/2;
    }
}