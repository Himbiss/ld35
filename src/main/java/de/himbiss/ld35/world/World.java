package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.world.fightsystem.MovingDecorator;
import de.himbiss.ld35.world.fightsystem.ShootingDecorator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Oneidavar on 16/04/2016.
 */
public class World implements Updatable {

    private final int sizeX;
    private final int sizeY;
    private final Tile[][] worldArray;
    private final Set<Entity> entities;
    private Entity player;
    private int startX;
    private int startY;
    private int bossX;
    private int bossY;
    private List<RoomStrukt> roomlist;
    private List<RoomStrukt> roomlist_end;

    public World(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.worldArray = new Tile[sizeX][sizeY];
        this.entities = new HashSet<Entity>();
        this.roomlist = new ArrayList<>();
        this.roomlist_end = new ArrayList<>();
        this.startX = 0;
        this.startY = 0;
        this.bossX = 0;
        this.bossY = 0;
        initArray();
    }

    public void populate() {
        Player entity = new Player();
        player = new MovingDecorator(new ShootingDecorator(entity, null, 1000), 5f, .1f, entity.buildAnimationMap());
        entities.add(player);
        player.setCoordX(getStartX());
        player.setCoordY(getStartY());
        entities.add(new Enemy_Spider(player.getCoordX(),player.getCoordY()-200));
        entities.add(new Crate(player.getCoordX() + 100 , player.getCoordY()));
        entities.add(new Enemy(player.getCoordX() - 100 , player.getCoordY()));
    }

    public void update(int delta) {
        Set<Entity> set = new HashSet<>(entities);
        for (Entity entity : set) {
            entity.update(delta);
        }

        //TODO player on button
        Engine.getInstance().getOffsetX();
        int cx = (int)(player.coordX+25)/50;
        int cy = (int)(player.coordY+25)/50;
        if(cx>=0 && cy>=0 && cx<sizeX && cy<sizeY) {
            if (worldArray[cx][cy] instanceof Tile_Button) {
                Tile_Button btn = ((Tile_Button) worldArray[cx][cy]);
                btn.doStuff();
            }
        }
    }

    public Entity getPlayer() {
        return player;
    }

    public Tile[][] getWorldArray() {
        return worldArray;
    }

    public Set<Entity> getEntities() {
        return entities;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setTile(int x, int y, Tile tile){
        if(x<0 || y < 0 || x>=this.getSizeX() || y>= this.getSizeY()) return;
        worldArray[x][y] = tile;
    }

    private void initArray() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                worldArray[i][j] = new Tile_Void(i,j);
            }
        }
    }

    public void setStart(int x, int y){
        this.startX = x;
        this.startY = y;
    }

    public void setBoss(int x, int y){
        this.bossX = x;
        this.bossY = y;
    }
    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setRoomlist(List<RoomStrukt> roomlist) {
        this.roomlist = roomlist;
    }

    public void setRoomlist_end(List<RoomStrukt> roomlist_end) {
        this.roomlist_end = roomlist_end;
    }

    public int getBossX() {
        return bossX;
    }

    public int getBossY() {
        return bossY;
    }


    public RoomStrukt getRoom(int x, int y){ //Return room in Worldarray coords

        for(RoomStrukt r: roomlist){
            if(r.posx <= x && r.posx+r.width>x ){
                if(r.posy <= y && r.posy+r.height>y ){
                    return r;
                }
            }
        }
        return null;
    }
}
