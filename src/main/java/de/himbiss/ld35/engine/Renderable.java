package de.himbiss.ld35.engine;

import org.newdawn.slick.opengl.Texture;

/**
 * Created by Vincent on 16.04.2016.
 */
public interface Renderable {

    int getWidth();

    int getHeight();

    Texture getTexture();

    default boolean renderMyself() {
        return false;
    }

    default void render(Engine engine) {
        // callers choice
    }
}
