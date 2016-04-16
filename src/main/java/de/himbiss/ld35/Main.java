package de.himbiss.ld35;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.world.World;
import de.himbiss.ld35.world.WorldGenerator;

/**
 * Created by Vincent on 15.04.2016.
 */
public class Main {

        public static void main(String[] argv) {
            World generate = WorldGenerator.generate(60,20,(int) System.currentTimeMillis());//553616882);//
            Engine.getInstance().setWorld(generate);
            Engine.getInstance().start();
        }
}
