package de.himbiss.ld35.engine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.PNGDecoder;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vincent on 16.04.2016.
 */
public class ResourceManager {

    private static ResourceManager instance;

    private final Map<String, Texture> textureCache;

    private ResourceManager() {
        this.textureCache = new HashMap<String, Texture>();
    }

    public static ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    public Texture getTexture(String textureKey) {
        if (textureCache.containsKey(textureKey)) {
            return textureCache.get(textureKey);
        }
        else {
            try {
                Texture texture = TextureLoader.getTexture("png", getClass().getClassLoader().getResourceAsStream(textureKey + ".png"));
                textureCache.put(textureKey, texture);
                return texture;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}