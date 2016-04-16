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


    public static World generate(int spread, int scale, int seed){
        Random rnd = new Random();//seed);
        int world_x = 0;
        int world_y = 0;
        int z = rnd.nextInt();
        z = abs(z);
        List<RoomStrukt> roomStruktList = new ArrayList<RoomStrukt>();
        for(int i = 0; i<(z%spread)+spread;i++){
            RoomStrukt r = new RoomStrukt();
            z = rnd.nextInt();
            z = abs(z);
            r.width = (z%scale)+1;
            z = rnd.nextInt();
            z = abs(z);
            r.height = (z%scale)+1;
            z = rnd.nextInt();
            z = abs(z);
            r.posx = (z%spread-spread/2-1);
            z = rnd.nextInt();
            z = abs(z);
            r.posy = (z%spread-spread/2-1);
            roomStruktList.add(r);
        }


        int sx = 0;
        int sy = 0;
        int count = 0;

        for (RoomStrukt r:roomStruktList){
            sx += r.width;
            sy += r.height;
            count ++;
        }
        sx /= count;
        sy /= count;

        List<RoomStrukt> deleteList = new ArrayList<RoomStrukt>();

        for (RoomStrukt r:roomStruktList){
            if(r.width < sx || r.height < sy){
                deleteList.add(r);
            }
        }

        for(RoomStrukt r: deleteList){
            roomStruktList.remove(r);
        }

        while(hasCollision(roomStruktList));
        System.out.println("done shifting");

        int minx = 0;
        int miny = 0;
        int maxx = 0;
        int maxy = 0;
        for(RoomStrukt r: roomStruktList){
            if(r.posx < minx) minx = r.posx;
            if(r.posy < miny) miny = r.posy;
            if(r.posx+r.width > maxx) maxx = r.posx+r.width;
            if(r.posy+r.height > maxy) maxy = r.posy+r.height;
        }
        world_x = abs(maxx) + abs(minx) +2;
        world_y = abs(maxy) + abs(miny) +2;
        for(RoomStrukt r: roomStruktList){
            r.posx += abs(minx) +1;
            r.posy += abs(miny) +1;
        }

        for(RoomStrukt r: roomStruktList){
            System.out.println(toText(r));
        }
        World w = new World(world_x,world_y);
        for(RoomStrukt r: roomStruktList){
            for(int rx = 0;rx<r.width;rx++){
                for(int ry = 0;ry<r.height;ry++){
                    if(r.midX() == rx+r.posx && r.midY() == ry+r.posy){

                        w.setTile(r.posx+rx, r.posy+ry,new Tile_Corridor());
                    } else
                    w.setTile(r.posx+rx, r.posy+ry,new Tile_Floor());
                }
            }
        }

        return w;
    }

    public static boolean hasCollision(List<RoomStrukt> list){
        int offset = 5;
        for (RoomStrukt r1:list){
            for(RoomStrukt r2:list){
                if(r1!=r2){
                    if((r1.posx-offset <= r2.posx && r1.posx+r1.width+offset >= r2.posx)
                            || (r1.posx-offset <= r2.posx+r2.width && r1.posx+r1.width+offset >= r2.posx+r2.width)){
                        if((r1.posy-offset <= r2.posy && r1.posy+r1.height+offset >= r2.posy)
                                || (r1.posy-offset <= r2.posy+r2.height && r1.posy+r1.height+offset >= r2.posy+r2.height)){
                            move(r1,r2);
                            //System.out.println(toText(r1) + "| " + toText(r2));
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void move(RoomStrukt r1, RoomStrukt r2){
        int m1x = r1.midX();
        int m2x = r2.midX();
        int m1y = r1.midY();
        int m2y = r2.midY();

        int p1 = m1x^2+m1y^2;
        int p2 = m2x^2+m2y^2;

        Random r = new Random();
        int z1 = r.nextInt();
        int v1 = z1/abs(z1);
        int z2 = r.nextInt();
        int v2 = z2/abs(z2);

        if(p1 > p2){
            if(abs(m1x)!=0 && abs(m1y)!=0) {
                r1.posx += m1x / abs(m1x) * v1;
                r1.posy += m1y / abs(m1y) * v2;
            } else {
                r1.posy ++;
                r1.posx --;
            }
        } else {
            if(abs(m2x)!=0 && abs(m2y)!=0) {
                r2.posx += m2x / abs(m2x) *v1;
                r2.posy += m2y / abs(m2y) *v2;
            } else {
                r2.posy ++;
                r2.posx --;
            }
        }
    }

    public static String toText(RoomStrukt r){
    String a = "";
    a += r.posx;
    a += ", ";
    a += r.posy;
    a += ", ";
    a += r.width;
    a += ", ";
    a += r.height;
    return a;
}

    public static boolean same(Graph_Edge e1, Graph_Edge e2){
        if(e1.p1 == e2.p1 && e1.p2 == e2.p2) return true;
        if(e1.p2 == e2.p1 && e1.p1 == e2.p2) return true;
        return false;
    }


}

