package de.himbiss.ld35;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 * Created by Vincent on 15.04.2016.
 */
public class Main {

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
            int i= 1;
            int j = 1;
            while (!Display.isCloseRequested()) {
                // Clear the screen and depth buffer
                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

                // set the color of the quad (R,G,B,A)
                GL11.glColor3f(0.5f,0.5f,1.0f);

                // draw quad
                GL11.glBegin(GL11.GL_QUADS);
                GL11.glVertex2f(100,100);
                GL11.glVertex2f(100+i,100);
                GL11.glVertex2f(100+i,100+i);
                GL11.glVertex2f(100,100+i);
                GL11.glEnd();

                i+=j;
                if(i==500 || i == 1) j

                Display.update();
            }

            Display.destroy();
        }

        public static void main(String[] argv) {
            Main quadExample = new Main();
            quadExample.start();
        }
}
