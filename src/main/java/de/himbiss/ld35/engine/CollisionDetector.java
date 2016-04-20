package de.himbiss.ld35.engine;

import de.himbiss.ld35.world.World;
import de.himbiss.ld35.world.entity.Entity;
import de.himbiss.ld35.world.generator.Tile;
import javafx.collections.ObservableList;
import org.newdawn.slick.geom.Rectangle;

import java.util.*;
import java.util.List;

/**
 * Created by Vincent on 16.04.2016.
 */
public class CollisionDetector {

    private final World world;
    private final QuadTree quadTree;

    public CollisionDetector(World world) {
        this.world = world;
        this.quadTree = new QuadTree(0, new Rectangle(0, 0, Tile.TILE_SIZE * world.getSizeX(), Tile.TILE_SIZE * world.getSizeY()));
    }

    public void doCollision() {
        quadTree.clear();
        ObservableList<Entity> entities = world.getEntities();
        for (int i = 0; i < entities.size(); i++) {
            quadTree.insert(entities.get(i));
        }

        List<HasHitbox> tilesWithHitbox = world.getTilesWithHitbox();
        for (int i = 0; i < tilesWithHitbox.size(); i++) {
            quadTree.insert(tilesWithHitbox.get(i));
        }

        List<HasHitbox> returnObjects = new ArrayList<>();
        for (int i = 0; i < entities.size(); i++) {
            returnObjects.clear();
            Entity entity1 = entities.get(i);
            quadTree.retrieve(returnObjects, entity1);

            for (int x = 0; x < returnObjects.size(); x++) {
                HasHitbox hasHitbox = returnObjects.get(x);
                if (!entity1.equals(hasHitbox)) {
                    calculateCollision(entity1, hasHitbox);
                }
            }
        }
    }

    private void calculateCollision(HasHitbox hb1, HasHitbox hb2) {
        float hb1_X = hb1.getHitBoxCoordX();
        float hb1_Y = hb1.getHitBoxCoordY();
        float hb1_W = hb1.getHitboxWidth();
        float hb1_H = hb1.getHitboxHeight();

        float hb2_X = hb2.getHitBoxCoordX();
        float hb2_Y = hb2.getHitBoxCoordY();
        float hb2_W = hb2.getHitboxWidth();
        float hb2_H = hb2.getHitboxHeight();

        if (hb1_X < hb2_X + hb2_W &&
                hb1_X + hb1_W > hb2_X &&
                hb1_Y < hb2_Y + hb2_H &&
                hb1_Y + hb1_H > hb2_Y) {
            // collision detected!

            float hb1_dX = 0f, hb1_dY = 0f;
            float hb2_dX = 0f, hb2_dY = 0f;

            if (hb1 instanceof Movement) {
                hb1_dX = ((Movement) hb1).getDeltaX();
                hb1_dY = ((Movement) hb1).getDeltaY();
            }
            if (hb2 instanceof Movement) {
                hb2_dX = ((Movement) hb2).getDeltaX();
                hb2_dY = ((Movement) hb2).getDeltaY();
            }

            hb1.collideWith(hb2, hb2_dX, hb2_dY);
            hb2.collideWith(hb1, hb1_dX, hb1_dY);
        }
    }

    public static boolean doesCollide(HasHitbox hb1, HasHitbox hb2) {
        float hb1_X = hb1.getHitBoxCoordX();
        float hb1_Y = hb1.getHitBoxCoordY();
        float hb1_W = hb1.getHitboxWidth();
        float hb1_H = hb1.getHitboxHeight();

        float hb2_X = hb2.getHitBoxCoordX();
        float hb2_Y = hb2.getHitBoxCoordY();
        float hb2_W = hb2.getHitboxWidth();
        float hb2_H = hb2.getHitboxHeight();

        if (hb1_X < hb2_X + hb2_W &&
                hb1_X + hb1_W > hb2_X &&
                hb1_Y < hb2_Y + hb2_H &&
                hb1_Y + hb1_H > hb2_Y) {
            // collision detected!
            return true;
        }
        return false;
    }
}
