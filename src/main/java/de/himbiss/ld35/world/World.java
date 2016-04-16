package de.himbiss.ld35.world;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Oneidavar on 16/04/2016.
 */
public class World {

    private final int sizeX;
    private final int sizeY;
    private final Tile[][] worldArray;
    private final Set<Entity> entities;
    private final Player player;

    public World(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.worldArray = new Tile[sizeX][sizeY];
        this.entities = new HashSet<Entity>();
        this.player = new Player();
        entities.add(player);
        initArray();
    }

    public void updateWorld() {
        // TODO: 16.04.2016 implement!
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

    private void initArray() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                worldArray[i][j] = new Tile();
            }
        }
    }
}
