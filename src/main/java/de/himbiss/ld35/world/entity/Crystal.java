package de.himbiss.ld35.world.entity;

import de.himbiss.ld35.engine.*;
import de.himbiss.ld35.world.World;
import de.himbiss.ld35.world.fightsystem.EntityDecorator;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by Vincent on 19.04.2016.
 */
public class Crystal extends Entity implements HasHitbox {

    private EntityDecorator emptyDecorator;

    public Crystal(EntityDecorator decorator, float posX, float posY) {
        emptyDecorator = decorator;
        coordX = posX;
        coordY = posY;
        width = 16;
        height = 32;
    }

    @Override
    public void collideWith(HasHitbox object, float deltaX, float deltaY) {
        if (object instanceof EntityDecorator) {
            Entity entity = ((EntityDecorator) object).getEntity();
            if (entity instanceof Player) {
                Player player = ((Player) entity);
                Engine.getInstance().showMessage("Press SPACE for Ability");
            }
        }
    }

    @Override
    public void update(int delta) {
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            World world = Engine.getInstance().getWorld();
            world.getEntities().remove(this);
            Player player = (Player) ((EntityDecorator) world.getPlayer()).getEntity();
            player.swap_to_slot(emptyDecorator, player.get_current_index() + 1);
        }
    }

    @Override
    public Texture getTexture() {
        return ResourceManager.getInstance().getTexture("crystal");
    }

    @Override
    public String toString() {
        return "Crystal";
    }
}
