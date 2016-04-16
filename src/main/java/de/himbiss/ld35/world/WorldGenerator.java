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
import static java.lang.Math.max;
import static java.lang.Math.min;

public class WorldGenerator {


    public static World generate(int spread, int scale, int seed){
        System.out.println("SEED: " + seed);
        Random rnd = new Random(seed);
        int world_x = 0;
        int world_y = 0;
        int z = rnd.nextInt();
        z = abs(z);
        List<RoomStrukt> roomStruktList = new ArrayList<RoomStrukt>();
        for(int i = 0; i<(z%spread)+spread;i++){
            RoomStrukt r = new RoomStrukt();
            z = rnd.nextInt();
            z = abs(z);
            r.width = (z%scale)+3;
            z = rnd.nextInt();
            z = abs(z);
            r.height = (z%scale)+3;
            z = rnd.nextInt();
            z = abs(z);
            r.posx = (z%spread-spread/2-1);
            z = rnd.nextInt();
            z = abs(z);
            r.posy = (z%spread-spread/2-1);
            roomStruktList.add(r);
        }

        List<RoomStrukt> deleteList = new ArrayList<RoomStrukt>();

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

        sx *=1.25;
        sy *=1.25;

        for (RoomStrukt r:roomStruktList){
            if(r.width < sx || r.height < sy){
                deleteList.add(r);
            }
        }

        for(RoomStrukt r: deleteList){
            roomStruktList.remove(r);
        }


        while(hasCollision(roomStruktList,rnd.nextInt()));
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
                    //if(r.midX() == rx+r.posx && r.midY() == ry+r.posy){

                    //    w.setTile(r.posx+rx, r.posy+ry,new Tile_Corridor());
                    //} else

                    w.setTile(r.posx+rx, r.posy+ry,new Tile_Floor());
                }
            }
        }
        List<Graph_Edge> graph_edgeList = MinSpannTree.span(roomStruktList);
        for(Graph_Edge e:graph_edgeList){

            RoomStrukt r1 = roomStruktList.get(e.p1);
            RoomStrukt r2 = roomStruktList.get(e.p2);
            int cu,cl,cd,cf,cs,ct,cv;
            if(abs(r1.midX()-r2.midX())<abs(r1.midY()-r2.midY())){


                if(r1.midY()>r2.midY()){
                    cu = (r2.posy+r2.height);
                    cl = (r1.posy);
                    cd = (cl-cu)/2+cu;
                } else {
                    cu = (r1.posy+r1.height);
                    cl = (r2.posy);
                    cd = (cl-cu)/2+cu;
                }
                if(r1.midX()>r2.midX()){
                    cf = r2.midX();
                    cs = r1.midX();
                } else {
                    cf = r1.midX();
                    cs = r2.midX();
                }
                if(r1.midY()>r2.midY()){
                    ct = r2.midX();
                    cv = r1.midX();
                } else {
                    ct = r1.midX();
                    cv = r2.midX();
                }
                for(int i = cu;i<=cd;i++){
                    w.setTile(ct-1,i,new Tile_Corridor());
                    w.setTile(ct,i,new Tile_Corridor());
                    w.setTile(ct+1,i,new Tile_Corridor());
                }
                for(int i = cf-1;i<cs+2;i++){
                    w.setTile(i,cd-1,new Tile_Corridor());
                    w.setTile(i,cd,new Tile_Corridor());
                    w.setTile(i,cd+1,new Tile_Corridor());
                }
                for(int i = cd;i<cl;i++){
                    w.setTile(cv-1,i,new Tile_Corridor());
                    w.setTile(cv,i,new Tile_Corridor());
                    w.setTile(cv+1,i,new Tile_Corridor());
                }
            } else {
                if(r1.midX()>r2.midX()){
                    cu = (r2.posx+r2.width);
                    cl = (r1.posx);
                    cd = (cl-cu)/2+cu;
                } else {
                    cu = (r1.posx+r1.width);
                    cl = (r2.posx);
                    cd = (cl-cu)/2+cu;
                }
                if(r1.midY()>r2.midY()){
                    cf = r2.midY();
                    cs = r1.midY();
                } else {
                    cf = r1.midY();
                    cs = r2.midY();
                }
                if(r1.midX()>r2.midX()){
                    ct = r2.midY();
                    cv = r1.midY();
                } else {
                    ct = r1.midY();
                    cv = r2.midY();
                }
                for(int i = cu;i<=cd;i++){
                    w.setTile(i,ct-1,new Tile_Corridor());
                    w.setTile(i,ct,new Tile_Corridor());
                    w.setTile(i,ct+1,new Tile_Corridor());
                }
                for(int i = cf-1;i<cs+2;i++){
                    w.setTile(cd-1,i,new Tile_Corridor());
                    w.setTile(cd,i,new Tile_Corridor());
                    w.setTile(cd+1,i,new Tile_Corridor());
                }
                for(int i = cd;i<cl;i++){
                    w.setTile(i,cv-1,new Tile_Corridor());
                    w.setTile(i,cv,new Tile_Corridor());
                    w.setTile(i,cv+1,new Tile_Corridor());
                }
            }


        }

        for(int i = 0; i<w.getSizeX();i++){
            for(int j = 0; j<w.getSizeY();j++){
                if(w.getWorldArray()[i][j].textureKey=="void"){
                    if(i+1<w.getSizeX() && w.getWorldArray()[i+1][j].textureKey!="void" && w.getWorldArray()[i+1][j].textureKey!="wall") {
                        w.setTile(i, j, new Tile_Wall());
                        continue;
                    }
                    if(i-1>=0 && w.getWorldArray()[i-1][j].textureKey!="void"&& w.getWorldArray()[i-1][j].textureKey!="wall") {
                        w.setTile(i, j, new Tile_Wall());
                        continue;
                    }
                    if(j+1<w.getSizeY() && w.getWorldArray()[i][j+1].textureKey!="void"&& w.getWorldArray()[i][j+1].textureKey!="wall") {
                        w.setTile(i, j, new Tile_Wall());
                        continue;
                    }
                    if(j-1>=0 && w.getWorldArray()[i][j-1].textureKey!="void"&& w.getWorldArray()[i][j-1].textureKey!="wall") {
                        w.setTile(i, j, new Tile_Wall());
                        continue;
                    }
                    if(i+1<w.getSizeX() && j+1<w.getSizeY() && w.getWorldArray()[i+1][j+1].textureKey!="void" && w.getWorldArray()[i+1][j+1].textureKey!="wall") {
                        w.setTile(i, j, new Tile_Wall());
                        continue;
                    }
                    if(i-1>=0 && j+1<w.getSizeY() &&  w.getWorldArray()[i-1][j+1].textureKey!="void"&& w.getWorldArray()[i-1][j+1].textureKey!="wall") {
                        w.setTile(i, j, new Tile_Wall());
                        continue;
                    }
                    if(i+1<w.getSizeX() && j-1>=0 &&  w.getWorldArray()[i+1][j-1].textureKey!="void"&& w.getWorldArray()[i+1][j-1].textureKey!="wall") {
                        w.setTile(i, j, new Tile_Wall());
                        continue;
                    }
                    if(i-1>=0 && j-1>=0 && w.getWorldArray()[i-1][j-1].textureKey!="void"&& w.getWorldArray()[i-1][j-1].textureKey!="wall") {
                        w.setTile(i, j, new Tile_Wall());
                        continue;
                    }
                }
            }
        }
        return w;
    }

    public static boolean hasCollision(List<RoomStrukt> list, int seed){
        int offset = 6;
        for (RoomStrukt r1:list){
            for(RoomStrukt r2:list){
                if(r1!=r2){
                    if((r1.posx-offset <= r2.posx && r1.posx+r1.width+offset >= r2.posx)
                            || (r1.posx-offset <= r2.posx+r2.width && r1.posx+r1.width+offset >= r2.posx+r2.width)){
                        if((r1.posy-offset <= r2.posy && r1.posy+r1.height+offset >= r2.posy)
                                || (r1.posy-offset <= r2.posy+r2.height && r1.posy+r1.height+offset >= r2.posy+r2.height)){
                            move(r1,r2,seed);
                            //System.out.println(toText(r1) + "| " + toText(r2));
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void move(RoomStrukt r1, RoomStrukt r2, int seed){
        int m1x = r1.midX();
        int m2x = r2.midX();
        int m1y = r1.midY();
        int m2y = r2.midY();

        int p1 = m1x^2+m1y^2;
        int p2 = m2x^2+m2y^2;

        Random r = new Random(seed);
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





}

