package de.himbiss.ld35.engine;

import de.himbiss.ld35.world.Entity;
import de.himbiss.ld35.world.Tile;
import de.himbiss.ld35.world.World;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Vincent on 16.04.2016.
 */
public class Engine {

    private static Engine instance;

    private World world;
    private DisplayMode displayMode;

    private float offsetX = 0f;
    private float offsetY = 0f;

    private long lastFrame;
    private int fps;
    private long lastFPS;
    private int realFPS;
    private TrueTypeFont debugFont;

    private Engine() {
        this.displayMode = new DisplayMode(800, 600);
    }

    public static Engine getInstance() {
        if (instance == null) {
            instance = new Engine();
        }
        return instance;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void start() {
        if (world == null) {
            throw new IllegalStateException("a world has to be set first!");
        }

        initGL();
        getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer

        while (!Display.isCloseRequested()) {
            int delta = getDelta();
            // Clear the screen and depth buffer
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            renderWorld();
            scrollWorld();

            debugFont.drawString(10f, 10f, "fps: " + realFPS, Color.white);
            Display.sync(60);
            Display.update();

            world.update(delta);
            updateFPS();
        }

        Display.destroy();
    }

    private void initGL() {
        try {
            Display.setDisplayMode(displayMode);
            Display.create();
            Display.setTitle("Ludum Dare 35");
            Display.setVSyncEnabled(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);

        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glViewport(0,0,displayMode.getWidth(),displayMode.getHeight());
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, displayMode.getWidth(), displayMode.getHeight(), 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        this.debugFont = ResourceManager.getInstance().getFont("plasmati");
    }

    private void scrollWorld() {
        Entity player = world.getPlayer();

        float scrollingDistVertical = 100f;
        float scrollingDistHorizontal = 150f;

        float distanceLeft = scrollingDistHorizontal;
        float distanceRight = (displayMode.getWidth() - scrollingDistHorizontal) - player.getWidth();
        float distanceUp = scrollingDistVertical;
        float distanceDown = (displayMode.getHeight() - scrollingDistVertical) - player.getHeight();

        float coordX = player.getCoordX();
        float coordY = player.getCoordY();

        if (coordX < distanceLeft) {
            float dX = distanceLeft - coordX;
            offsetX += dX;
            player.setCoordX(distanceLeft);
        }
        if (coordX > distanceRight) {
            float dX = coordX - distanceRight;
            offsetX -= dX;
            player.setCoordX(distanceRight);
        }
        if (coordY < distanceUp) {
            float dY = distanceUp - coordY;
            offsetY += dY;
            player.setCoordY(distanceUp);
        }
        if (coordY > distanceDown) {
            float dY = coordY - distanceDown;
            offsetY -= dY;
            player.setCoordY(distanceDown);
        }
    }

    public DisplayMode getDisplayMode() {
        return displayMode;
    }

    private void renderWorld() {
        for (int i = 0; i < world.getSizeX(); i++) {
            for (int j = 0; j < world.getSizeY(); j++) {
                Tile tile = world.getWorldArray()[i][j];
                float posX = offsetX + (i * tile.getWidth());
                float posY = offsetY + (j * tile.getHeight());
                renderObject(tile, posX, posY);
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

    private void renderObject(Renderable object,float posX, float posY) {

        Texture texture = ResourceManager.getInstance().getTexture(object.getTextureKey());
        texture.bind();

        // draw quad
        GL11.glBegin(GL11.GL_QUADS);

        // upper left
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(posX, posY);
        // upper right
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(posX + object.getWidth(), posY);
        // lower right
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(posX + object.getWidth(), posY + object.getHeight());
        // lower left
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(posX, posY + object.getHeight());

        GL11.glEnd();
    }
}
