package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.Engine;

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
    private Player player;
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
        player = new Player();
        entities.add(player);
        player.setCoordX(getStartX());
        player.setCoordY(getStartY());
        entities.add(new Crate(player.getCoordX() + 100 , player.getCoordY()));
        entities.add(new Enemy(player.getCoordX() - 100 , player.getCoordY()));
    }

    public void update(int delta) {
        // TODO: 16.04.2016 implement!
        player.update(delta);

        //TODO player on button
        Engine.getInstance().getOffsetX();
        int cx = (int)(player.coordX+25)/50;
        int cy = (int)(player.coordY+25)/50;
        if(worldArray[cx][cy] instanceof Tile_Button) {
            Tile_Button btn = ((Tile_Button) worldArray[cx][cy]);
            btn.doStuff();
        }
    }

    public Player getPlayer() {
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
}
