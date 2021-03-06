package de.himbiss.ld35.world.fightsystem;

/**
 * Created by Vincent on 17.04.2016.
 */
public interface HasHealth {
    void setHealth(int health);
    int getHealth();
    void applyDamage(DoesDamage damageObject, float dX, float dY);

    default void applyDamage(int damage, double dX, double dY) {
        applyDamage(() -> damage, (float) dX, (float) dY);
    }
}
