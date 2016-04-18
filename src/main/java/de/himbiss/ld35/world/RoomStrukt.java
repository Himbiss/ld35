package de.himbiss.ld35.world;

/**
 * Created by Oneidavar on 16/04/2016.
 */
public class RoomStrukt{
    public int width;
    public int height;
    public int posx;
    public int posy;
    public int type = 0; //0-default, 1-start, 2-end
    public boolean room_cleared = false;
    public Switchable[] doors = new Switchable[0];


    public int midX(){
        return (2*posx+width)/2;
    }

    public int midY(){
        return (2*posy+height)/2;
    }

    public void setDoors(Switchable[] doorlist){
        //this.doors = new Switchable[doorlist.length];
        doors = doorlist;
    }

    public boolean isRoom_cleared() {
        return room_cleared;
    }

    public void setRoom_cleared() {
        this.room_cleared = true;
    }

    public void openDoors(){
        for(Switchable s:doors){
            s.trigger();
        }
    }
    public void closeDoors(){
        for(Switchable s:doors){
            if(s instanceof Tile_Door)
                ((Tile_Door) s).close();

        }
    }
}