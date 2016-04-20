package de.himbiss.ld35.world;

import com.sun.javafx.collections.ObservableListWrapper;
import de.himbiss.ld35.editor.Editor;
import de.himbiss.ld35.engine.AudioManager;
import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.HasHitbox;
import de.himbiss.ld35.world.entity.*;
import de.himbiss.ld35.world.fightsystem.BulletFactory;
import de.himbiss.ld35.world.fightsystem.EntityDecorator;
import de.himbiss.ld35.world.fightsystem.MovingDecorator;
import de.himbiss.ld35.world.fightsystem.ShootingDecorator;
import de.himbiss.ld35.world.generator.*;
import javafx.collections.ObservableList;
import org.lwjgl.input.Keyboard;

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
    private final ObservableList<Entity> entities;
    private Entity player;
    private int startX;
    private int startY;
    private int bossX;
    private int bossY;
    private List<RoomStrukt> roomlist;
    private List<RoomStrukt> roomlist_end;
    private List<HasHitbox> tilesWithHitboxes;

    public World(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.worldArray = new Tile[sizeX][sizeY];
        this.entities = new ObservableListWrapper<Entity>(new ArrayList<>());
        this.tilesWithHitboxes = new ArrayList<>();
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
        entity.unlock_next_slot();
        EntityDecorator tmp = new MovingDecorator(new ShootingDecorator(entity, BulletFactory.BulletType.TEAR));
        entity.swap_to_slot(tmp,1);
        player = new MovingDecorator(new ShootingDecorator(entity, BulletFactory.BulletType.TEAR));
        entity.swap_to_slot((EntityDecorator)player,0);
        entities.add(player);
        player.setCoordX(getStartX());
        player.setCoordY(getStartY());

        Guard guard = new Guard(player.getCoordX(), player.getCoordY() - 200);
        EntityDecorator guardDecorator = new MovingDecorator(new ShootingDecorator(guard, BulletFactory.BulletType.TEAR));
        guard.setDecorator(guardDecorator);

        entities.add(guardDecorator);
        entities.add(new Crate(player.getCoordX() + 100 , player.getCoordY()));
        entities.add(new Enemy(player.getCoordX() - 100 , player.getCoordY()));
        AudioManager.getInstance().getAudio("dummy").playAsMusic(1.0f,1.0f,true);
    }

    public void update(int delta) {
        Set<Entity> set = new HashSet<>(entities);
        for (Entity entity : set) {
            entity.update(delta);
        }

        //TODO player on button
        Engine.getInstance().getOffsetX();
        int cx = (int)(player.getCoordX()-Engine.getInstance().getOffsetX()+25)/50;
        int cy = (int)(player.getCoordY()-Engine.getInstance().getOffsetY()+25)/50;
        if(cx>=0 && cy>=0 && cx<sizeX && cy<sizeY) {
            if (worldArray[cx][cy] instanceof Tile_Button) {
                Tile_Button btn = ((Tile_Button) worldArray[cx][cy]);
                btn.doStuff();
            }
            if (worldArray[cx][cy] instanceof Tile_SpawnButton) {
                Tile_SpawnButton btn = ((Tile_SpawnButton) worldArray[cx][cy]);
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

    public ObservableList<Entity> getEntities() {
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
        if (tile instanceof HasHitbox) {
            tilesWithHitboxes.add(((HasHitbox) tile));
        }
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

    public void setPlayer(Entity player) {
        this.player = player;
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

    public List<HasHitbox> getTilesWithHitbox() {
        return tilesWithHitboxes;
    }
}
