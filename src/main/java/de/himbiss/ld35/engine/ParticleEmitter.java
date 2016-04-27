package de.himbiss.ld35.engine;

import de.himbiss.ld35.world.entity.Entity;
import de.himbiss.ld35.world.fightsystem.Bullet;
import de.himbiss.ld35.world.fightsystem.EntityDecorator;
import de.himbiss.ld35.world.fightsystem.MovingDecorator;
import de.himbiss.ld35.world.fightsystem.MovingStrategy;
import org.lwjgl.opengl.GL11;

/**
 * Created by Vincent on 27.04.2016.
 */
public class ParticleEmitter extends Entity {

    private final int numParticles;
    private final int intervalSpeed;
    private long lastEmission;

    public ParticleEmitter(float coordX, float coordY, int numParticles, int intervalSpeed) {
        super(coordX, coordY, 0, 0);
        this.numParticles = numParticles;
        this.intervalSpeed = intervalSpeed;
        this.lastEmission = intervalSpeed;
    }

    @Override
    public void collideWith(HasHitbox object, float deltaX, float deltaY) {
        // do nothing
    }

    @Override
    public void update(int delta) {
        lastEmission -= delta;
        if (lastEmission <= 0) {
            lastEmission = intervalSpeed;
            for (int i = 0; i < numParticles; i++) {
                Vector2D direction = Vector2D.randomVector();
                float multiplicator = 2f / direction.abs().max();
                direction = direction.mult(multiplicator);

                Engine.getInstance().getWorld().getEntities().add(new MovingDecorator(new Particle(this, 1000, 1f, direction, coordX, coordY, 1f, 0f, 0f)));
            }
        }
    }

    public class Particle extends Entity implements MovingStrategy {

        private final float speed;
        private Vector2D direction;
        private final long timeToLive;
        private long ttl;
        private final float colR;
        private final float colG;
        private final float colB;

        Particle(ParticleEmitter parent, long timeToLive, float speed, Vector2D direction, float coordX, float coordY, float colR, float colG, float colB) {
            super(coordX, coordY, 1, 1);
            this.timeToLive = timeToLive;
            this.ttl = timeToLive;
            this.speed = speed;
            this.direction = direction;
            this.coordX = coordX;
            this.coordY = coordY;
            this.colR = colR;
            this.colG = colG;
            this.colB = colB;
        }

        @Override
        public void update(int delta) {
            ttl -= delta * speed;
            if (ttl <= 0) {
                Engine.getInstance().getWorld().getEntities().remove(this);
            }
        }

        @Override
        public void collideWith(HasHitbox object, float deltaX, float deltaY) {
            if (object instanceof EntityDecorator) {
                if (((EntityDecorator) object).getEntity() instanceof Particle) {
                    return;
                }
            }
            this.direction = new Vector2D(deltaX, deltaY);
        }

        @Override
        public boolean renderMyself() {
            return true;
        }

        @Override
        public void render(Engine engine) {
            float posX = getCoordX();
            float posY = getCoordY();
            float alpha = 1f;

            // Clear the screen and depth buffer
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            // set the color of the quad (R,G,B,A)
            GL11.glClearDepth(1);
            GL11.glColor4f(0.0f,1.0f,0.0f, 1.0f);

            // draw quad
            GL11.glBegin(GL11.GL_QUADS);

            // upper left
            GL11.glVertex2f(posX-1, posY-1);
            // upper right
            GL11.glVertex2f(posX + getWidth()+1, posY-1);
            // lower right
            GL11.glVertex2f(posX + getWidth()+1, posY + getHeight()+1);
            // lower left
            GL11.glVertex2f(posX-1, posY + getHeight()+1);

            GL11.glEnd();
        }

        @Override
        public Vector2D calcDirection(float deltaX, float deltaY, int deltaT) {
            return direction;
        }

        @Override
        public float getDeltaMax() {
            return Float.MAX_VALUE;
        }

        @Override
        public String toString() {
            return "Particle";
        }
    }

    class ParticleWaypoint {

    }
}
