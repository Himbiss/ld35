package de.himbiss.ld35.editor;

import de.himbiss.ld35.engine.Engine;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Vincent on 18.04.2016.
 */
public class Editor extends JFrame {

    private static Editor instance;

    public static Editor getInstance() {
        if (instance == null) {
            instance = new Editor();
        }
        return instance;
    }

    private Editor() {
        ScriptEditor scriptEditor = new ScriptEditor();
        EntityList entityList = new EntityList(Engine.getInstance().getWorld());
        JScrollPane scrollPane = new JScrollPane(entityList);

        entityList.addListSelectionListener(scriptEditor);

        setLayout(new BorderLayout());
        add(scriptEditor, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.WEST);
        setTitle("Editor");
        pack();
        setLocationRelativeTo(null);

    }

}
