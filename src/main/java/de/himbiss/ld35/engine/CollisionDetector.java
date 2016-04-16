package de.himbiss.ld35.engine;

import de.himbiss.ld35.world.Entity;

import java.util.Set;

/**
 * Created by Vincent on 16.04.2016.
 */
public class CollisionDetector {

    private final Set<Entity> entities;
    private final Set<Hitbox> tileHitboxes;

    public CollisionDetector(Set<Entity> entities, Set<Hitbox> tileHitboxes) {
        this.entities = entities;
        this.tileHitboxes = tileHitboxes;
    }

    public void doCollision() {

        for (Entity entity1 : entities) {
            for (Entity entity2 : entities) {
                if (entity1 != entity2) {
                    calculateCollision(entity1, entity2);
                }
            }
            for (Hitbox tileHitbox : tileHitboxes) {
                calculateCollision(entity1, tileHitbox);
            }
        }
    }

    private void calculateCollision(Hitbox hb1, Hitbox hb2) {
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
            float deltaX = (hb1_X + hb1_W) - hb2_X;
            float deltaY = (hb1_Y + hb1_H) - hb2_Y;

            hb1.collideWith(hb2, deltaX, deltaY);
        }
    }
}
