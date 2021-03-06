package de.himbiss.ld35.engine;

import de.himbiss.ld35.editor.Editor;
import de.himbiss.ld35.world.generator.Tile_SpawnButton;
import de.himbiss.ld35.world.entity.Entity;
import de.himbiss.ld35.world.fightsystem.HasHealth;
import de.himbiss.ld35.world.generator.*;
import de.himbiss.ld35.world.World;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Vincent on 16.04.2016.
 */
public class Engine {

    private static Engine instance;

    private World world;
    private DisplayMode displayMode;
    private ExecutorService scriptThreadPool;
    private Map<HasScript, Future<?>> scriptThreadMap;

    private float offsetX = 0f;
    private float offsetY = 0f;

    private long lastFrame;
    private int fps;
    private long lastFPS;
    private int realFPS;
    private TrueTypeFont debugFont;
    private boolean debugMode;
    private boolean minimap;
    private float gravity = 1f;
    private ScriptEngine scriptEngine;
    private String infoMessage;
    private long infoDelay;
    private CollisionDetector collisionDetector;

    private Engine() {
        this.displayMode = new DisplayMode(800, 600);
        this.scriptThreadPool = Executors.newCachedThreadPool();
        this.scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");
        this.scriptThreadMap = new HashMap<>();
    }

    public static Engine getInstance() {
        if (instance == null) {
            instance = new Engine();
        }
        return instance;
    }

    public ExecutorService getScriptThreadPool() {
        return scriptThreadPool;
    }

    public void invokeScript(HasScript hasScript) {
        stopScript(hasScript);
        scriptEngine.put("engine", this);
        scriptEngine.put("world", world);
        scriptEngine.put("player", world.getPlayer());
        scriptEngine.put("me", hasScript);
        Future<?> future = scriptThreadPool.submit(() -> {
            try {
                System.out.println("Script starting.");
                scriptEngine.eval(hasScript.getScript());
                System.out.println("Script finished.");
            }
            catch (ScriptException e) {
                e.printStackTrace();
                System.out.println("Script stopped.");
            }
        });
        scriptThreadMap.put(hasScript, future);
    }

    public void stopScript(HasScript script) {
        Future future = scriptThreadMap.get(script);
        if (future != null) {
            future.cancel(true);
        }
    }

    public void setWorld(World world) {
        this.world = world;
        this.collisionDetector = new CollisionDetector(world);
    }

    public void start() {
        if (world == null) {
            throw new IllegalStateException("a world has to be set first!");
        }

        initGL();
        world.populate();
        getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer
        while (!Display.isCloseRequested()) {

            int delta = getDelta();
            // Clear the screen and depth buffer
            //GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            world.update(delta);
            calculatePhysics();

            scrollWorld();
            renderWorld();

            handleKeyEvents();

            if (debugMode) {
                renderDebug();
            }

            renderUI();
            if(minimap){
                renderMinimap();
            }

            Display.sync(60);
            Display.update();

            if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
                int cx = (int)(world.getPlayer().getCoordX()-Engine.getInstance().getOffsetX()+25)/50;
                int cy = (int)(world.getPlayer().getCoordY()-Engine.getInstance().getOffsetY()+25)/50;
                RoomStrukt r = world.getRoom(cx,cy);
                if(r!=null)r.openDoors();
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
                int cx = (int)(world.getPlayer().getCoordX()-Engine.getInstance().getOffsetX()+25)/50;
                int cy = (int)(world.getPlayer().getCoordY()-Engine.getInstance().getOffsetY()+25)/50;
                RoomStrukt r = world.getRoom(cx,cy);
                if(r!=null)r.closeDoors();
            }

            if (Display.isCloseRequested()) {
                exit();
            }
            updateFPS();
        }

        Display.destroy();
    }

    private void handleKeyEvents() {
        while(Keyboard.next()) {
            if(Keyboard.getEventKey() == Keyboard.KEY_F2 && Keyboard.getEventKeyState()) {
                Editor editor = Editor.getInstance();
                editor.setVisible(! editor.isVisible());
            }
            else if (Keyboard.getEventKey() == Keyboard.KEY_F1 && Keyboard.getEventKeyState()) {
                debugMode = ! debugMode;
            }
            else if (Keyboard.getEventKey() == Keyboard.KEY_F3 && Keyboard.getEventKeyState()) {
                minimap = ! minimap;
            }
        }
    }

    private void renderDebug() {
        GL11.glColor3f(1f, 0f, 0f);
        debugFont.drawString(10f, 10f, "fps: " + realFPS, Color.yellow);
        debugFont.drawString(10f, 30f, "dX,dY: " + ((Movement) world.getPlayer()).getDeltaX() + "," + ((Movement) world.getPlayer()).getDeltaY(), Color.yellow);
        debugFont.drawString(10f, 50f, "posX,posY: " + world.getPlayer().getCoordX() + "," + world.getPlayer().getCoordY(), Color.yellow);

        for (Entity entity : world.getEntities()) {
            Renderable hitbox = new Renderable() {
                @Override
                public int getWidth() {
                    return (int) entity.getHitboxWidth();
                }

                @Override
                public int getHeight() {
                    return (int) entity.getHitboxHeight();
                }

                @Override
                public Texture getTexture() {
                    return ResourceManager.getInstance().getTexture("hitbox");
                }
            };

            renderObject(hitbox, entity.getHitBoxCoordX(), entity.getHitBoxCoordY());
            if (entity instanceof HasHealth) {
                debugFont.drawString(entity.getCoordX(), entity.getCoordY() - 10, "HP:" + ((HasHealth) entity).getHealth(), Color.red);
            }
        }

        for (Tile[] tiles : world.getWorldArray()) {
            for (Tile tile : tiles) {
                if (tile instanceof HasHitbox) {
                    if (!(tile instanceof Tile_Door) || (!((Tile_Door) tile).isOpen())) {
                        HasHitbox hasHitbox = ((HasHitbox) tile);
                        Renderable hitbox = new Renderable() {
                            @Override
                            public int getWidth() {
                                return (int) hasHitbox.getHitboxWidth();
                            }

                            @Override
                            public int getHeight() {
                                return (int) hasHitbox.getHitboxHeight();
                            }

                            @Override
                            public Texture getTexture() {
                                return ResourceManager.getInstance().getTexture("hitbox");
                            }
                        };

                        renderObject(hitbox, hasHitbox.getHitBoxCoordX(), hasHitbox.getHitBoxCoordY());
                    }
                }
            }
        }
    }

    private void renderUI(){

        GL11.glColor3f(1f, 1f, 1f);
        if (infoDelay > 0) {
            debugFont.drawString(20f, 20f, infoMessage, Color.gray);
            infoDelay--;
        }

        Texture texture = ResourceManager.getInstance().getTexture("dummy");
        texture.bind();
        // draw quad
        GL11.glBegin(GL11.GL_QUADS);
        // upper left
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(displayMode.getWidth()/2-205, displayMode.getHeight()-55);
        // upper right
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(displayMode.getWidth()/2+205,displayMode.getHeight()-55);
        // lower right
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(displayMode.getWidth()/2+205, displayMode.getHeight()-5);
        // lower left
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(displayMode.getWidth()/2-205,displayMode.getHeight()-5);
        GL11.glEnd();

        texture = ResourceManager.getInstance().getTexture("floor");
        texture.bind();
        int ci = 8; //TODO get current slot index
        // draw quad
        GL11.glBegin(GL11.GL_QUADS);
        // upper left
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(displayMode.getWidth()/2-200+ci*40+ci*5-2, displayMode.getHeight()-52);
        // upper right
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(displayMode.getWidth()/2-160+ci*40+ci*5+1,displayMode.getHeight()-52);
        // lower right
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(displayMode.getWidth()/2-160+ci*40+ci*5+1, displayMode.getHeight()-9);
        // lower left
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(displayMode.getWidth()/2-200+ci*40+ci*5-2,displayMode.getHeight()-9);
        GL11.glEnd();

        for(int i = 0; i < 9; i++){
            texture = ResourceManager.getInstance().getTexture("crate");
            texture.bind();
            // draw quad
            GL11.glBegin(GL11.GL_QUADS);
            // upper left
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2f(displayMode.getWidth()/2-200+i*40+i*5, displayMode.getHeight()-50);
            // upper right
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex2f(displayMode.getWidth()/2-160+i*40+i*5,displayMode.getHeight()-50);
            // lower right
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex2f(displayMode.getWidth()/2-160+i*40+i*5, displayMode.getHeight()-10);
            // lower left
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex2f(displayMode.getWidth()/2-200+i*40+i*5,displayMode.getHeight()-10);
            GL11.glEnd();
        }
    }

    private void renderMinimap(){
        int cx = (int)(world.getPlayer().getCoordX()-Engine.getInstance().getOffsetX()+25)/50;
        int cy = (int)(world.getPlayer().getCoordY()-Engine.getInstance().getOffsetY()+25)/50;
        for(int i = 0; i<world.getSizeX();i++){
            for(int j = 0; j<world.getSizeY();j++){
                Tile t = world.getWorldArray()[i][j];
                Texture texture = null;

                texture = ResourceManager.getInstance().getTexture("alpha");
                if(t.isRendered()) {
                    if ((i == cx && j == cy)) {
                        texture = ResourceManager.getInstance().getTexture("red");
                    } else if (t instanceof Tile_Floor || t instanceof Tile_SpawnButton) {
                        texture = ResourceManager.getInstance().getTexture("gray");
                    } else if (t instanceof Tile_Corridor || t instanceof Tile_Button || (t instanceof Tile_Door && ((Tile_Door) t).isOpen())) {
                        texture = ResourceManager.getInstance().getTexture("gray2");
                    } else if (t instanceof Tile_Wall || (t instanceof Tile_Door && !((Tile_Door) t).isOpen())) {
                        texture = ResourceManager.getInstance().getTexture("blue");
                    }
                }
                if(texture!=null) texture.bind();

                GL11.glBegin(GL11.GL_QUADS);

                // upper left
                GL11.glVertex2f(displayMode.getWidth()-world.getSizeX()*2-2+i*2, j*2);
                // upper right
                GL11.glVertex2f(displayMode.getWidth()-world.getSizeX()*2+i*2,j*2);
                // lower right
                GL11.glVertex2f(displayMode.getWidth()-world.getSizeX()*2+i*2, (j*2)+2);
                // lower left
                GL11.glVertex2f(displayMode.getWidth()-world.getSizeX()*2-2+i*2,(j*2)+2);
                GL11.glEnd();
            }
        }
    }

    private void calculatePhysics() {
        List<Entity> entities = world.getEntities();
        Set<HasHitbox> tileHitboxes = new HashSet<>();
        for (Tile[] tiles : world.getWorldArray()) {
            for (Tile tile : tiles) {
                if (tile instanceof HasHitbox) {
                    if (!(tile instanceof Tile_Door) || (tile instanceof Tile_Door && !((Tile_Door) tile).isOpen())){
                        tileHitboxes.add(((HasHitbox) tile));
                    }
                }
            }
        }

        //Move entity according to deltaX / deltaY
        for (Entity entity : entities) {
            if (entity instanceof Movement) {
                entity.setCoordX((entity.getCoordX() - offsetX) + ((Movement) entity).getDeltaX());
                entity.setCoordY((entity.getCoordY() - offsetY) + ((Movement) entity).getDeltaY());
            }
        }


        collisionDetector.doCollision();

        //Apply Gravity only after Collision Detection!!!
        //this way collisionDetection can use the deltas to restore the position before moving
        entities.stream().filter(e -> e instanceof Movement).forEach(e -> ((Movement) e).applyGravity(gravity));
    }

    private void initGL() {
        try {
            Display.setDisplayMode(displayMode);
            Display.create();
            Display.setTitle("Ludum Dare 35");
            Display.setVSyncEnabled(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
            Engine.exit();
        }

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);

        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        //Setup filtering, i.e. how OpenGL will interpolate the pixels when scaling up or down
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        //Setup wrap mode, i.e. how OpenGL will handle pixels outside of the expected range
        //Note: GL_CLAMP_TO_EDGE is part of GL12
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        GL11.glViewport(0,0,displayMode.getWidth(),displayMode.getHeight());
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, displayMode.getWidth(), displayMode.getHeight(), 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        this.debugFont = ResourceManager.getInstance().getFont("plasmati");
    }

    public static void exit() {
        for (HasScript hasScript : getInstance().scriptThreadMap.keySet()) {
            getInstance().stopScript(hasScript);
        }
        System.exit(0);
    }

    private void scrollWorld() {
        Entity player = world.getPlayer();

        float scrollingDistVertical = displayMode.getWidth() / 4;
        float scrollingDistHorizontal = displayMode.getHeight() / 4;

        float distanceLeft = scrollingDistHorizontal;
        float distanceRight = (displayMode.getWidth() - scrollingDistHorizontal) - player.getWidth();
        float distanceUp = scrollingDistVertical;
        float distanceDown = (displayMode.getHeight() - scrollingDistVertical) - player.getHeight();

        float coordX = player.getCoordX();
        float coordY = player.getCoordY();

        if (coordX < distanceLeft) {
            float dX = distanceLeft - coordX;
            offsetX += dX;
            player.setCoordX((distanceLeft - offsetX) + 1);
        }
        if (coordX > distanceRight) {
            float dX = coordX - distanceRight;
            offsetX -= dX;
            player.setCoordX((distanceRight - offsetX) - 1);
        }
        if (coordY < distanceUp) {
            float dY = distanceUp - coordY;
            offsetY += dY;
            player.setCoordY((distanceUp - offsetY) + 1);
        }
        if (coordY > distanceDown) {
            float dY = coordY - distanceDown;
            offsetY -= dY;
            player.setCoordY((distanceDown - offsetY) - 1);
        }
    }

    public DisplayMode getDisplayMode() {
        return displayMode;
    }

    private void renderWorld() {
        int iStart = (int) Math.abs(offsetX / Tile.TILE_SIZE);
        int jStart = (int) Math.abs(offsetY / Tile.TILE_SIZE);
        int tilesHorizontal = displayMode.getWidth() / Tile.TILE_SIZE;
        int tilesVertical = displayMode.getHeight() / Tile.TILE_SIZE;
        int iMax = iStart + tilesHorizontal + 1;
        int jMax = jStart + tilesVertical + 1;

        iMax = iMax > world.getSizeX() ? world.getSizeX() : iMax;
        jMax = jMax > world.getSizeY() ? world.getSizeY() : jMax;

        for (int i = iStart ; i < iMax; i++) {
            for (int j = jStart; j < jMax; j++) {
                Tile tile = world.getWorldArray()[i][j];
                float posX = offsetX + (i * tile.getWidth());
                float posY = offsetY + (j * tile.getHeight());
                renderObject(tile, posX, posY);
                tile.setRendered();
            }
        }
        for (Entity entity : world.getEntities()) {
            renderObject(entity, entity.getCoordX(), entity.getCoordY());
        }
    }

    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            realFPS = fps;
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    private void renderObject(Renderable object, float posX, float posY) {

        if (object.renderMyself()) {
            object.render(this);
        }
        else {
            GL11.glColor3f(1f, 1f, 1f);
            Texture texture = object.getTexture();
            
            texture.bind();

            // draw quad
            GL11.glBegin(GL11.GL_QUADS);

            // upper left
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2f(posX-1, posY-1);
            // upper right
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex2f(posX + object.getWidth()+1, posY-1);
            // lower right
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex2f(posX + object.getWidth()+1, posY + object.getHeight()+1);
            // lower left
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex2f(posX-1, posY + object.getHeight()+1);


            GL11.glEnd();
        }
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public float getGravity() {
        return gravity;
    }

    public World getWorld() {
        return world;
    }

    public void showMessage(String s) {
        this.infoMessage = s;
        this.infoDelay = 30;
    }
}
