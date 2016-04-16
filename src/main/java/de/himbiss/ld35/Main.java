package de.himbiss.ld35;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.world.World;

/**
 * Created by Vincent on 15.04.2016.
 */
public class Main {

        public static void main(String[] argv) {
            World world = new World(100, 100);
            Engine engine = new Engine(world);
            engine.start();
        }
}
