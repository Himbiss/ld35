package de.himbiss.ld35.world.fightsystem;

/**
 * Created by Vincent on 17.04.2016.
 */
public interface HasHealth {
    void setHealth(int health);
    int getHealth();
    void applyDamage(DoesDamage damageObject);
}
