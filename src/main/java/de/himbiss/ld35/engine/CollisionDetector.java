package de.himbiss.ld35.engine;

import de.himbiss.ld35.world.Entity;

import java.util.*;

/**
 * Created by Vincent on 16.04.2016.
 */
public class CollisionDetector {

    private final List<Entity> entities;
    private final Set<HasHitbox> tileHitboxes;

    public CollisionDetector(List<Entity> entities, Set<HasHitbox> tileHitboxes) {
        this.entities = new ArrayList<>(entities);
        this.tileHitboxes = new HashSet<>(tileHitboxes);
    }

    public void doCollision() {
        for (Entity entity1 : entities) {
            for (Entity entity2 : entities) {
                if (entity1 != entity2) {
                    calculateCollision(entity1, entity2);
                }
            }
            for (HasHitbox tileHitbox : tileHitboxes) {
                calculateCollision(entity1, tileHitbox);
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

            if (hb1 instanceof HasPhysics) {
                hb1_dX = ((HasPhysics) hb1).getDeltaX();
                hb1_dY = ((HasPhysics) hb1).getDeltaY();
            }
            if (hb2 instanceof HasPhysics) {
                hb2_dX = ((HasPhysics) hb2).getDeltaX();
                hb2_dY = ((HasPhysics) hb2).getDeltaY();
            }

            hb1.collideWith(hb2, hb2_dX, hb2_dY);
            hb2.collideWith(hb1, hb1_dX, hb1_dY);
        }
    }
}
