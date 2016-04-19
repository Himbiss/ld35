package de.himbiss.ld35.world.entity;

import de.himbiss.ld35.engine.*;
import de.himbiss.ld35.world.fightsystem.*;
import de.himbiss.ld35.world.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vincent on 16.04.2016.
 */
public class Player extends Entity implements HasHealth, HasScript, MovingStrategy, ShootingStrategy, IsAnimated {

    private int health = 10;
    private String script;
    protected boolean[] slot_unlocked;
    protected EntityDecorator[] slots;
    public int currentslot;
    private long lastKey;
    private float acceleration = 1f;

    public Player() {
        width = 50;
        height = 50;
        coordX =  (Engine.getInstance().getDisplayMode().getWidth() / 2) - (width / 2);
        coordY =  (Engine.getInstance().getDisplayMode().getHeight() / 2) - (height / 2);
        script = ResourceManager.getInstance().getScript("player");

        currentslot = 0;
        slot_unlocked = new boolean[3];
        slots = new EntityDecorator[3];
        slot_unlocked[0] = true;
        slot_unlocked[1] = false;
        slot_unlocked[2] = false;

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
        if (Keyboard.isKeyDown(Keyboard.KEY_ADD) && (System.currentTimeMillis() - lastKey) > 100) {
            index_inc();
            lastKey = System.currentTimeMillis();
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT) && (System.currentTimeMillis() - lastKey) > 100) {
            index_dec();
            lastKey = System.currentTimeMillis();
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
        this.health -= damageObject.getBaseDamage();
        if (this.health < 0) {
            System.out.println("Game Over");
            System.exit(0);
        }
    }

    @Override
    public String toString() {
        return "Player";
    }

    @Override
    public String getScript() {
        return script;
    }

    @Override
    public void setScript(String script) {
        this.script = script;
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
            System.out.println("Current Slot: " + i);
            strip_unstrip();
            return true;
        }
        return false;
    }

    private void strip_unstrip(){
        if(slots[currentslot]!=null) {
            World w = Engine.getInstance().getWorld();
            EntityDecorator e = (EntityDecorator) w.getPlayer();
            //Player pc = (Player) e.getEntity();
            w.getEntities().remove(e);
            //e.setEntityR(null);
            EntityDecorator e2 = slots[currentslot];
            e2.setEntityR(this);
            e2.setCoordX(e.getCoordX()-Engine.getInstance().getOffsetX());
            e2.setCoordY(e.getCoordY()-Engine.getInstance().getOffsetY());
            e2.setDeltas(e.getDeltaX(),e.getDeltaY());
            w.setPlayer(e2);
            w.getEntities().add(e2);
        }
    }

    public void index_inc(){
        System.out.println("inc");
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


    @Override
    public String getAnimation() {
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            return  "walk_left";
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            return "walk_right";
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            return "walk_down";
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            return "walk_up";
        }
        else {
            return "freeze";
        }
    }

    @Override
    public Vector2D calcDirection(float deltaX, float deltaY, int deltaT) {
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            deltaX -= acceleration * deltaT;
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            deltaX += acceleration * deltaT;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            deltaY += acceleration * deltaT;
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            deltaY -= acceleration * deltaT;
        }

        return new Vector2D(deltaX, deltaY);
    }

    @Override
    public float getDeltaMax() {
        return 5f;
    }

    @Override
    public int getShotDelayInMillis() {
        return 1000;
    }

    @Override
    public float getMaxBulletSpeed() {
        return 4f;
    }

    @Override
    public Vector2D calcDirectionOfShot() {
        if (Mouse.isButtonDown(0)) {
            int x = Mouse.getX();
            int y = Engine.getInstance().getDisplayMode().getHeight() - Mouse.getY();
            float pX = x - getCoordX();
            float pY = y - getCoordY();
            return new Vector2D(pX, pY);
        }
        else {
            throw new IllegalStateException("not shooting!");
        }
    }

    @Override
    public boolean isShooting() {
        return Mouse.isButtonDown(0);
    }

    @Override
    public Map<String, Animation> getAnimationMap() {
        Map<String, Animation> animationMap = new HashMap<>();
        SpriteSheet spriteSheet = ResourceManager.getInstance().getSpriteSheet("Albert", 16, 16);
        animationMap.put("walk_up", new Animation(spriteSheet, new int[] {0,0, 0,1, 1,1}, new int[] {100, 100, 100}));
        animationMap.put("walk_down", new Animation(spriteSheet, new int[] {2,0, 0,3, 1,3}, new int[] {100, 100, 100}));
        animationMap.put("walk_right", new Animation(spriteSheet, new int[] {1,0, 0,2, 1,2}, new int[] {100, 100, 100}));
        animationMap.put("walk_left", new Animation(spriteSheet, new int[] {3,0, 0,4, 1,4}, new int[] {100, 100, 100}));
        return animationMap;
    }
}
