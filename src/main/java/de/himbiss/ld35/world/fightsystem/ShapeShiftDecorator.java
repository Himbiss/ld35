package de.himbiss.ld35.world.fightsystem;

import de.himbiss.ld35.world.Entity;
import org.lwjgl.input.Keyboard;

/**
 * Created by Oneidavar on 18/04/2016.
 */
public class ShapeShiftDecorator extends EntityDecorator {

    protected boolean[] slot_unlocked;
    protected Object[] slots;
    public int currentslot;

    public ShapeShiftDecorator(Entity entity, Object slot0, Object slot1){
        super(entity);
        int numberSlots = 9; //TODO how many slots do we need?
       currentslot = 0;

        slot_unlocked = new boolean[numberSlots];
        slots = new Object[numberSlots];
        slots[0] = slot0;
        slots[1] = slot1;

        for(int i = 0; i<numberSlots; i++){
            slot_unlocked[i] = i < 2;
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

    public Object getSlot(int i){
        if(slot_unlocked[i]) {
            if (slots[i] != null) {
                return slots[i];
            }
        }
            return null;
    }

    public boolean swap_to_slot(Object obj, int slot){
        if(slot_unlocked[slot]) {
            drop_from_slot(slot);
            slots[slot] = obj;
            return true;
        }
        return false;
    }

    public void drop_from_slot(int i){
        if(slot_unlocked[i]){
            if(slots[i]!=null){
                //TODO drop the item
                slots[i] = null;
            }
        }
    }

    public Object get_current_Obj(){
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

    public void index_inc(){
        currentslot = (currentslot+1)%9;
    }
    public void index_dec(){
        currentslot = (currentslot+8)%9;
    }

    public void update(int delta) {
        super.update(delta);
        if (Keyboard.isKeyDown(Keyboard.KEY_ADD)) {
            index_inc();
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)) {
            index_dec();
        }
    }
}
