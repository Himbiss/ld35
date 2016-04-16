package de.himbiss.ld35.engine;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vincent on 16.04.2016.
 */
public class ResourceManager {

    private static ResourceManager instance;
    private static float FONT_SIZE = 20f;

    private final Map<String, Texture> textureCache;
    private final Map<String, TrueTypeFont> fontCache;

    private ResourceManager() {
        this.textureCache = new HashMap<>();
        this.fontCache = new HashMap<>();
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
            try (InputStream stream = getClass().getClassLoader().getResourceAsStream(textureKey + ".png")){
                Texture texture = TextureLoader.getTexture("png", stream);
                textureCache.put(textureKey, texture);
                return texture;
            } catch (IOException e) {
                e.printStackTrace();
                return getTexture("dummy");
            }
        }
    }

    public TrueTypeFont getFont(String fontKey) {
        if (fontCache.containsKey(fontKey)) {
            return fontCache.get(fontKey);
        }
        else {
            try (InputStream stream = getClass().getClassLoader().getResourceAsStream(fontKey + ".ttf")){
                Font awtFont = Font.createFont(Font.TRUETYPE_FONT, stream);
                awtFont = awtFont.deriveFont(FONT_SIZE);
                TrueTypeFont font = new TrueTypeFont(awtFont, false);
                fontCache.put(fontKey, font);
                return font;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FontFormatException e) {
                e.printStackTrace();
            }
            Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
            return new TrueTypeFont(awtFont, false);
        }
    }

}