package de.himbiss.ld35.world;

/**
 * Created by Oneidavar on 16/04/2016.
 */
import de.himbiss.ld35.world.World;
import de.himbiss.ld35.world.Tile;
import de.himbiss.ld35.world.RoomStrukt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class WorldGenerator {


    public World generate(World w,int param, int seed){
        Random rnd = new Random(seed);
        int x = w.getSizeX();
        int y = w.getSizeY();

        List<RoomStrukt> roomStruktList = new ArrayList<RoomStrukt>();
        for(int i = 0; i<(rnd.nextInt()%param)+param;i++){
            RoomStrukt r = new RoomStrukt();
            r.width = (rnd.nextInt()%param)+1;
            r.height = (rnd.nextInt()%param)+1;
            r.posx = (rnd.nextInt()%param-param/2);
            r.posy = (rnd.nextInt()%param-param/2);
            roomStruktList.add(r);
        }
        while(hasCollision(roomStruktList));

        return w;
    }

    public boolean hasCollision(List<RoomStrukt> list){
        for (RoomStrukt r1:list){
            for(RoomStrukt r2:list){
                if(r1!=r2){
                    if((r1.posx < r2.posx && r1.posx+r1.width > r2.posx)
                            || (r1.posx < r2.posx+r2.width && r1.posx+r1.width > r2.posx+r2.width)){
                        if((r1.posy < r2.posy && r1.posy+r1.height > r2.posy)
                                || (r1.posy < r2.posy+r2.height && r1.posy+r1.height > r2.posy+r2.height)){
                            move(r1,r2);
                            return true;
                        }
                    }
                }
            }
        }
        return true;
    }

    public void move(RoomStrukt r1, RoomStrukt r2){
        int m1x = (r1.posx+r1.width)/2;
        int m2x = (r2.posx+r2.width)/2;
        int m1y = (r1.posy+r1.height)/2;
        int m2y = (r2.posy+r2.height)/2;

        int p1 = m1x^2+m1y^2;
        int p2 = m2x^2+m2y^2;

        if(p1 > p2){
            r1.posx+=m1x/abs(m1x);
            r1.posy+=m1y/abs(m1y);
        } else {
            r2.posx+=m1x/abs(m2x);
            r2.posy+=m1y/abs(m2y);
        }
    }
}

