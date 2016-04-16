package de.himbiss.ld35.engine;

import de.himbiss.ld35.world.Entity;
import de.himbiss.ld35.world.Tile;
import de.himbiss.ld35.world.World;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Vincent on 16.04.2016.
 */
public class Engine {

    private final int SCROLLING_DISTANCE = 150;
    private static Engine instance;

    private World world;
    private DisplayMode displayMode;

    private float offsetX = 0f;
    private float offsetY = 0f;

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

        try {
            Display.setDisplayMode(displayMode);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        // init OpenGL
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 800, 0, 600, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        long start;
        long elapsed = 1;

        Texture texture = ResourceManager.getInstance().getTexture("crate");

        while (!Display.isCloseRequested()) {
            start = System.nanoTime();
            // Clear the screen and depth buffer
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            texture.bind();
            // set the color of the quad (R,G,B,A)
            //GL11.glColor3f(0.5f,0.5f,1.0f);

            //TODO Implement FPS Counter
            //System.out.println(1000000/elapsed);

            renderWorld();
            scrollWorld();

            Display.update();

            world.updateWorld();
            elapsed = System.nanoTime() - start;
        }

        Display.destroy();
    }

    private void scrollWorld() {
        Entity player = world.getPlayer();

    }

    public DisplayMode getDisplayMode() {
        return displayMode;
    }

    private void renderWorld() {
        for (int i = 0; i < world.getSizeX(); i++) {
            for (int j = 0; j < world.getSizeY(); j++) {
                Tile tile = world.getWorldArray()[i][j];
                renderObject(tile, i * tile.getWidth(), j * tile.getHeight());
            }
        }
        for (Entity entity : world.getEntities()) {
            renderObject(entity, entity.getCoordX(), entity.getCoordY());
        }
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
