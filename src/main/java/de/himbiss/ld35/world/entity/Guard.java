package de.himbiss.ld35.world.entity;

import de.himbiss.ld35.engine.*;
import de.himbiss.ld35.world.World;
import de.himbiss.ld35.world.fightsystem.MovingStrategy;
import de.himbiss.ld35.world.fightsystem.ShootingStrategy;
import de.himbiss.ld35.world.generator.Tile;
import de.himbiss.ld35.world.generator.Tile_Door;
import de.himbiss.ld35.world.generator.Tile_Floor;
import de.himbiss.ld35.world.generator.Tile_Wall;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.Texture;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oneidavar on 17/04/2016.
 */
public class Guard extends Enemy implements HasScript, IsAnimated, MovingStrategy, ShootingStrategy {
    public float speed = .1f;
    public static final float DELTA_MAX = 3f;
    public long lastShot;
    public int attackSpeed;
    public int dist_move;
    public int dist_attack;
    public int dist_ignore;
    public String script;
    private String animationKey = "freeze";

    public Guard(float posX, float posY) {
        super(posX, posY);
        textureKey="crate";
        width = 50;
        height = 50;
        attackSpeed = 2000;
        dist_move = 5;
        dist_attack = 7;
        dist_ignore = 10;
        script = ResourceManager.getInstance().getScript("spider");
        Engine.getInstance().invokeScript(this);
    }

    public void update(int delta) {
    //TODO Pathing towards player
        /*
        World w = Engine.getInstance().getWorld();

        float tX = getCoordX()-Engine.getInstance().getOffsetX();
        float tY = getCoordY()-Engine.getInstance().getOffsetY();
        float pX = w.getPlayer().getCoordX()-Engine.getInstance().getOffsetX();
        float pY = w.getPlayer().getCoordY()-Engine.getInstance().getOffsetY();

        float dx = Math.abs(tX-pX);
        float dy = Math.abs(tY-pY);


        if(Math.sqrt(dx*dx+dy*dy)>dist_move*50 && Math.sqrt(dx*dx+dy*dy)<dist_ignore*50 ) {
            float grade = (dx / (dx + dy));

            if (tX < pX ) {
                //TODO set animation
                deltaX += speed * delta * grade;
            } else if (tX > pX ) {
                //TODO set animation
                deltaX -= speed * delta * grade;
            }
            if (tY < pY) {
                //TODO set animation
                deltaY += speed * delta * (1-grade);
            } else if (tY > pY) {
                //TODO set animation
                deltaY -= speed * delta * (1-grade);
            }

            if (Math.abs(deltaX) > DELTA_MAX) {
                deltaX = deltaX < 0 ? -DELTA_MAX : DELTA_MAX;
            }
            if (Math.abs(deltaY) > DELTA_MAX) {
                deltaY = deltaY < 0 ? -DELTA_MAX : DELTA_MAX;
            }

        }
        if(Math.sqrt(dx*dx+dy*dy)<dist_attack*50){

            if ((System.currentTimeMillis() - lastShot) > attackSpeed) {
                lastShot = System.currentTimeMillis();
                dx *= -0.1f;
                dy *= -0.1f;
                //System.out.println("spider shooting tear: " + dx + "," + dy);
                //Engine.getInstance().getWorld().getEntities().add(new Tear(this, coordX + (getWidth() / 2), coordY + (getHeight() / 2), dx, dy));
            }
        }
    */

    }

    public Texture getTexture() {
        return ResourceManager.getInstance().getTexture(textureKey);
    }

    @Override
    public String toString() {
        return "Guard";
    }

    @Override
    public String getScript() {
        return script;
    }

    @Override
    public void setScript(String script) {
        this.script = script;
    }


    @Override
    public Map<String, Animation> getAnimationMap() {
        Map<String, Animation> animationMap = new HashMap<>();
        SpriteSheet spriteSheet = ResourceManager.getInstance().getSpriteSheet("Guard", 16, 16);
        animationMap.put("walk_up", new Animation(spriteSheet, new int[] {0,0, 0,1, 1,1}, new int[] {100, 100, 100}));
        animationMap.put("walk_down", new Animation(spriteSheet, new int[] {2,0, 0,3, 1,3}, new int[] {100, 100, 100}));
        animationMap.put("walk_right", new Animation(spriteSheet, new int[] {1,0, 0,2, 1,2}, new int[] {100, 100, 100}));
        animationMap.put("walk_left", new Animation(spriteSheet, new int[] {3,0, 0,4, 1,4}, new int[] {100, 100, 100}));
        return animationMap;
    }

    @Override
    public Vector2D calcDirection(float deltaX, float deltaY, int deltaT) {
        World w = Engine.getInstance().getWorld();

        float tX = getCoordX()-Engine.getInstance().getOffsetX();
        float tY = getCoordY()-Engine.getInstance().getOffsetY();
        float pX = w.getPlayer().getCoordX()-Engine.getInstance().getOffsetX();
        float pY = w.getPlayer().getCoordY()-Engine.getInstance().getOffsetY();

        float dx = Math.abs(tX-pX);
        float dy = Math.abs(tY-pY);

        int aX = (int)((pX-tX)/dx);
        int aY = (int)((pY-tY)/dy);

        if(Math.sqrt(dx*dx+dy*dy)>dist_move*50 && Math.sqrt(dx*dx+dy*dy)<dist_ignore*50 ) {
            Tile ta = w.getWorldArray()[(int)(tX+25)/50+aX][(int)(tY+25)/50+aY];
            if(ta instanceof Tile_Wall || (ta instanceof Tile_Door && !((Tile_Door) ta).isOpen())) {

                //TODO smart pathfinding
                int sx = w.getSizeX();
                int sy = w.getSizeY();
                boolean[][] visited =  new boolean[sx][sy];
                int[][] direction = new int[sx][sy];
                int[][] value = new int[sx][sy];
                int TX = (int)(tX+25)/50;
                int TY = (int)(tY+25)/50;
                int PX = (int)(pX+25)/50;
                int PY = (int)(pY+25)/50;
                int cX = TX;
                int cY = TY;
                for(int i = 0; i< sx;i++){
                    for(int j = 0; j<sy;j++){
                        value[i][j] = 9999999;
                    }
                }
                value[cX][cY] = 0;
                direction[cX][cY] = 0;
                //Directions
                //      1
                //      |
                //   4 -0- 2
                //      |
                //      3

                while(cX != PX && cY != PY){
                    visited[cX][cY] = true;
                    System.out.println(cX + " | " + cY);
                    if(cX >0){
                        Tile t = w.getWorldArray()[cX-1][cY];
                        if(t instanceof Tile_Floor || (t instanceof Tile_Door && ((Tile_Door) t).isOpen())){
                            int v = value[cX][cY] + 10 + Math.abs(cX-1-PX) + Math.abs(cY-PY);
                            if(v< value[cX-1][cY]){
                                value[cX-1][cY] = v;
                                direction[cX-1][cY] = 2;
                            }
                        }
                    }
                    if(cY >0){
                        Tile t = w.getWorldArray()[cX][cY-1];
                        if(t instanceof Tile_Floor || (t instanceof Tile_Door && ((Tile_Door) t).isOpen())){
                            int v = value[cX][cY] + 10 + Math.abs(cX-PX) + Math.abs(cY-1-PY);
                            if(v< value[cX][cY-1]){
                                value[cX][cY-1] = v;
                                direction[cX][cY-1] = 3;
                            }
                        }
                    }
                    if(cX <sx-1){
                        Tile t = w.getWorldArray()[cX+1][cY];
                        if(t instanceof Tile_Floor || (t instanceof Tile_Door && ((Tile_Door) t).isOpen())){
                            int v = value[cX][cY] + 10 + Math.abs(cX+1-PX) + Math.abs(cY-PY);
                            if(v< value[cX+1][cY]){
                                value[cX+1][cY] = v;
                                direction[cX+1][cY] = 4;
                            }
                        }
                    }
                    if(cY < sy-1){
                        Tile t = w.getWorldArray()[cX][cY+1];
                        if(t instanceof Tile_Floor || (t instanceof Tile_Door && ((Tile_Door) t).isOpen())){
                            int v = value[cX][cY] + 10 + Math.abs(cX-PX) + Math.abs(cY+1-PY);
                            if(v< value[cX][cY+1]){
                                value[cX][cY+1] = v;
                                direction[cX][cY+1] = 1;
                            }
                        }
                    }


                    int nv = 9999999;
                    int nx = -1;
                    int ny = -1;
                    for(int i = 0; i< sx;i++){
                        for(int j = 0; j<sy;j++){
                            if(!visited[i][j]){
                                if(value[i][j] < nv){
                                    nx = i;
                                    ny = j;
                                }
                            }
                        }
                    }
                    if(nx != -1 && ny != -1){
                        cX = nx;
                        cY = ny;
                    } else {
                        System.out.println("no better");
                        break;
                    }

                }

                int lastdirection = 0;
                //cX = PX;
                //cY = PY;
                while(cX != TX && cY != TY) {
                    System.out.println(cX + " x " + cY);
                    int cd = direction[cX][cY];
                    if(cd == 1){
                        cX --;
                    }
                    if(cd == 3){
                        cX ++;
                    }
                    if(cd == 2){
                        cY ++;
                    }
                    if(cd == 4){
                        cY --;
                    }
                    lastdirection = cd;
                    if(lastdirection == 0){
                        System.out.println("Direction Zero");
                        break;
                    }
                }

                switch(lastdirection){
                    case 1:
                        animationKey = "walk_up";
                        deltaY -= speed * deltaT;
                        break;
                    case 2:
                        animationKey = "walk_right";
                        deltaX += speed * deltaT;
                        break;
                    case 3:
                        animationKey = "walk_down";
                        deltaY += speed * deltaT;
                        break;
                    case 4:
                        animationKey = "walk_left";
                        deltaX -= speed * deltaT;
                        break;
                    default:
                        animationKey = "freeze";
                        break;
                }
                System.out.println(lastdirection);
                return new Vector2D(deltaX, deltaY);

            } else {
                float grade = (dx / (dx + dy));

                if (tX < pX) {
                    animationKey = "walk_left";
                    deltaX += speed * deltaT * grade;
                } else if (tX > pX) {
                    animationKey = "walk_right";
                    deltaX -= speed * deltaT * grade;
                }

                if (tY < pY) {
                    animationKey = "walk_down";
                    deltaY += speed * deltaT * (1 - grade);
                } else if (tY > pY) {
                    animationKey = "walk_up";
                    deltaY -= speed * deltaT * (1 - grade);
                }
            }
        }
        else {
            animationKey = "freeze";
        }
        return new Vector2D(deltaX, deltaY);
    }

    @Override
    public String getAnimation() {
        return animationKey;
    }

    @Override
    public float getDeltaMax() {
        return 3f;
    }

    @Override
    public int getShotDelayInMillis() {
        return 1500;
    }

    @Override
    public float getMaxBulletSpeed() {
        return 4f;
    }

    @Override
    public Vector2D calcDirectionOfShot() {
        World w = Engine.getInstance().getWorld();
        float x = w.getPlayer().getCoordX()-Engine.getInstance().getOffsetX();
        float y = w.getPlayer().getCoordY()-Engine.getInstance().getOffsetY();
        float pX = x - coordX;
        float pY = y - coordY;
        return new Vector2D(pX, pY);
    }

    @Override
    public boolean isShooting() {
        World w = Engine.getInstance().getWorld();

        float tX = getCoordX()-Engine.getInstance().getOffsetX();
        float tY = getCoordY()-Engine.getInstance().getOffsetY();
        float pX = w.getPlayer().getCoordX()-Engine.getInstance().getOffsetX();
        float pY = w.getPlayer().getCoordY()-Engine.getInstance().getOffsetY();

        float dx = Math.abs(tX-pX);
        float dy = Math.abs(tY-pY);

        return Math.sqrt(dx*dx+dy*dy)<dist_attack*50;
    }
}
