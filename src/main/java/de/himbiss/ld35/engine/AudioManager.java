package de.himbiss.ld35.engine;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oneidavar on 17/04/2016.
 */
public class AudioManager {

    private static AudioManager instance;
    private final Map<String, Audio> audioCache;
    private final Map<String, Audio> effectCache;

    private AudioManager(){
        //Constructor
        this.audioCache = new HashMap<>();
        this.effectCache = new HashMap<>();
    }


    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }


    public Audio getEffect(String effectKey){
        if(effectCache.containsKey(effectKey)){
            return effectCache.get(effectKey);
        } else {
            try(InputStream stream = getClass().getClassLoader().getResourceAsStream("effect/"+effectKey+".ogg")){
                Audio audio = AudioLoader.getAudio("OGG",stream);
                effectCache.put(effectKey, audio);
                return audio;
            } catch(IOException e){
                e.printStackTrace();
                return getEffect("dummy");
            }
        }
    }
    public Audio getAudio(String audioKey){
        if(audioCache.containsKey(audioKey)){
            return effectCache.get(audioKey);
        } else {

            try(InputStream stream = getClass().getClassLoader().getResourceAsStream("audio/"+audioKey+".ogg")){
                Audio audio = AudioLoader.getAudio("OGG", stream);
                effectCache.put(audioKey, audio);
                return audio;
            } catch(IOException e){
                e.printStackTrace();
                return getAudio("dummy");
            }
        }
    }

}
