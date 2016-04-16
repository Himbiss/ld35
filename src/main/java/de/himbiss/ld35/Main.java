package de.himbiss.ld35;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.world.World;
import de.himbiss.ld35.world.WorldGenerator;

/**
 * Created by Vincent on 15.04.2016.
 */
public class Main {

        public static void main(String[] argv) {
            World world = WorldGenerator.generate(30,3049);
            Engine engine = new Engine(world);

            engine.start();
        }
}
