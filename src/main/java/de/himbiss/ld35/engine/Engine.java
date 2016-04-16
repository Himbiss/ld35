package de.himbiss.ld35.engine;

import de.himbiss.ld35.world.Entity;
import de.himbiss.ld35.world.Tile;
import de.himbiss.ld35.world.World;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 * Created by Vincent on 16.04.2016.
 */
public class Engine {
    private final World world;

    public Engine(World world) {
        this.world = world;
    }

    public void start() {
        try {
            Display.setDisplayMode(new DisplayMode(800,600));
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
        long start;
        long elapsed = 1;
        while (!Display.isCloseRequested()) {
            start = System.nanoTime();
            // Clear the screen and depth buffer
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            // set the color of the quad (R,G,B,A)
            GL11.glColor3f(0.5f,0.5f,1.0f);

            //TODO Implement FPS Counter
            //System.out.println(1000000/elapsed);

            // draw quad
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f(100,100);
            GL11.glVertex2f(200,100);
            GL11.glVertex2f(200,200);
            GL11.glVertex2f(100,200);
            GL11.glEnd();

            renderWorld();

            Display.update();

            world.updateWorld();
            elapsed = System.nanoTime() - start;
        }

        Display.destroy();
    }

    private void renderWorld() {
        for (int i = 0; i < world.getSizeX(); i++) {
            for (int j = 0; j < world.getSizeY(); j++) {
                Tile tile = world.getWorldArray()[i][j];
                renderTile(tile, i, j);
            }
        }
        for (Entity entity : world.getEntities()) {
            renderEntity(entity);
        }
    }

    private void renderEntity(Entity entity) {
        // TODO: 16.04.2016 implement!
    }

    private void renderTile(Tile tile, int i, int j) {
        // TODO: 16.04.2016 implement!
    }
}
