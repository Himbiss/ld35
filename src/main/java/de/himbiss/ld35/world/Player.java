package de.himbiss.ld35.world;

import de.himbiss.ld35.engine.HasHitbox;
import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.ResourceManager;
import de.himbiss.ld35.world.animation.Animator;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Vincent on 16.04.2016.
 */
public class Player extends Entity {

    private static final float DELTA_MAX = 4f;
    private float speed = .1f;
    private Animator animator;
    private SpriteSheet spriteSheet;
    private Animation walkAnimation;

    public Player() {
        width = 50;
        height = 50;
        coordX =  (Engine.getInstance().getDisplayMode().getWidth() / 2) - (width / 2);
        coordY =  (Engine.getInstance().getDisplayMode().getHeight() / 2) - (height / 2);
        spriteSheet = ResourceManager.getInstance().getSpriteSheet("Albert", 16, 16);
        walkAnimation = new Animation(spriteSheet, new int[] {0,3,5}, new int[] {50, 50, 50});
        walkAnimation.setAutoUpdate(true);
    }

    public void update(int delta) {
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            deltaX -= speed * delta;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            deltaX += speed * delta;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            deltaY += speed * delta;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            deltaY -= speed * delta;
        }

        if (Math.abs(deltaX) > DELTA_MAX) {
            deltaX = deltaX < 0 ? -DELTA_MAX : DELTA_MAX;
        }
        if (Math.abs(deltaY) > DELTA_MAX) {
            deltaY = deltaY < 0 ? -DELTA_MAX : DELTA_MAX;
        }

        //if (Keyboard.next()) {
        //    animator.step(delta);
        //}
    }

    @Override
    public boolean renderMyself() {
        return true;
    }

    @Override
    public void render(Engine engine) {
        walkAnimation.draw(getCoordX(), getCoordY(), width, height);
    }

    @Override
    public void collideWith(HasHitbox object, float deltaX, float deltaY) {
    }
}
