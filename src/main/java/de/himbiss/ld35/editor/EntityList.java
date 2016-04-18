package de.himbiss.ld35.editor;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.world.Entity;
import de.himbiss.ld35.world.World;
import javafx.collections.ListChangeListener;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Vincent on 18.04.2016.
 */
public class EntityList extends JList<Entity> {

    public EntityList(World world) {
        super(new EntityListModel());

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(50, 200);
    }

    static class EntityListModel extends DefaultListModel<Entity> implements ListChangeListener<Entity> {
        public EntityListModel() {
            Engine.getInstance().getWorld().getEntities().addListener(this);
        }

        @Override
        public void onChanged(Change<? extends Entity> c) {
            clear();
            new LinkedList<>(c.getList()).forEach(this::addElement);
        }
    }
}
