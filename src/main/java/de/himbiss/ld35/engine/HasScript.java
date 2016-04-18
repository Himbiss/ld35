package de.himbiss.ld35.engine;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.world.Entity;
import de.himbiss.ld35.world.World;

import javax.script.*;

/**
 * Created by Vincent on 18.04.2016.
 */
public interface HasScript {
    String getScript();

    void setScript(String script);
}
