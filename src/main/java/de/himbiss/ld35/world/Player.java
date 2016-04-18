package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.HasHitbox;
import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.ResourceManager;
import de.himbiss.ld35.world.fightsystem.*;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vincent on 16.04.2016.
 */
public class Player extends Entity implements HasHealth {

    private int health = 10;
    protected boolean[] slot_unlocked;
    protected EntityDecorator[] slots;
    public int currentslot;

    public Player() {
        width = 50;
        height = 50;
        coordX =  (Engine.getInstance().getDisplayMode().getWidth() / 2) - (width / 2);
        coordY =  (Engine.getInstance().getDisplayMode().getHeight() / 2) - (height / 2);

        currentslot = 0;
        slot_unlocked = new boolean[3];
        slots = new EntityDecorator[3];
        slot_unlocked[0] = false;
        slot_unlocked[0] = false;
        slot_unlocked[0] = false;

    }

    public Map<String, Animation> buildAnimationMap() {
        Map<String, Animation> animationMap = new HashMap<>();
        SpriteSheet spriteSheet = ResourceManager.getInstance().getSpriteSheet("Albert", 16, 16);
        animationMap.put("walk_up", new Animation(spriteSheet, new int[] {0,0, 0,1, 1,1}, new int[] {100, 100, 100}));
        animationMap.put("walk_down", new Animation(spriteSheet, new int[] {2,0, 0,3, 1,3}, new int[] {100, 100, 100}));
        animationMap.put("walk_right", new Animation(spriteSheet, new int[] {1,0, 0,2, 1,2}, new int[] {100, 100, 100}));
        animationMap.put("walk_left", new Animation(spriteSheet, new int[] {3,0, 0,4, 1,4}, new int[] {100, 100, 100}));
        return animationMap;
    }

    @Override
    public float getHitBoxCoordY() {
        return getCoordY() + (height / 2);
    }

    @Override
    public float getHitBoxCoordX() {
        return super.getHitBoxCoordX() + (width / 4);
    }

    @Override
    public float getHitboxHeight() {
        return super.getHitboxHeight() / 2;
    }

    @Override
    public float getHitboxWidth() {
        return super.getHitboxWidth() / 2;
    }

    public void update(int delta) {
        if (Keyboard.isKeyDown(Keyboard.KEY_ADD)) {
            index_inc();
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)) {
            index_dec();
        }
    }

    @Override
    public void collideWith(HasHitbox object, float deltaX, float deltaY) {
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void applyDamage(DoesDamage damageObject, float dX, float dY) {
        this.deltaX += deltaX;
        this.deltaY += deltaY;
        this.health -= damageObject.getBaseDamage();
        if (this.health < 0) {
            System.out.println("Game Over");
            System.exit(0);
        }
    }

    public void unlock_next_slot(){
        for(int i = 0; i<slot_unlocked.length; i++){
            if(!slot_unlocked[i]){
                slot_unlocked[i] = true;
                return;
            }
        }
    }

    public EntityDecorator getSlot(int i){
        if(slot_unlocked[i]) {
            if (slots[i] != null) {
                return slots[i];
            }
        }
        return null;
    }


    public boolean swap_to_slot(EntityDecorator dec, int slot){
        if(slot_unlocked[slot]) {
            drop_from_slot(slot);
            slots[slot] = dec;
            return true;
        }
        return false;
    }

    public void drop_from_slot(int i){
        if(slot_unlocked[i]){
            if(slots[i]!=null){
                //TODO drop the item to the map
                slots[i] = null;
            }
        }
    }

    public EntityDecorator get_current_Dec(){
        return slots[currentslot];
    }

    public int get_current_index(){
        return currentslot;
    }

    public boolean set_current_index(int i){
        if(0<=i && i<slots.length){
            currentslot = i;
            return true;
        }
        return false;
    }

    private void strip_unstrip(){
        if(slots[currentslot]!=null) {
            World w = Engine.getInstance().getWorld();
            EntityDecorator e = (EntityDecorator) w.getPlayer();
            Player pc = (Player) e.getEntity();
            w.getEntities().remove(e);
            e.setEntityR(null);
            EntityDecorator e2 = slots[currentslot];
            e2.setEntityR(pc);
            w.getEntities().add(e2);
        }
    }

    public void index_inc(){
        currentslot = (currentslot+1)%3;
        while(!slot_unlocked[currentslot]){
            currentslot = (currentslot+1)%3;
        }
        set_current_index(currentslot);
    }
    public void index_dec(){
        currentslot = (currentslot+2)%3;
        while(!slot_unlocked[currentslot]){
            currentslot = (currentslot+2)%3;
        }
        set_current_index(currentslot);
    }








}
