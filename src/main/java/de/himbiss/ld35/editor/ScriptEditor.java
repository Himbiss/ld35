package de.himbiss.ld35.editor;

import de.himbiss.ld35.engine.Engine;
import de.himbiss.ld35.engine.HasScript;
import de.himbiss.ld35.world.Entity;
import de.himbiss.ld35.world.fightsystem.EntityDecorator;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.script.ScriptException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;

/**
 * Created by Vincent on 18.04.2016.
 */
public class ScriptEditor extends JPanel implements ListSelectionListener {

    private HasScript hasScript;
    private final RSyntaxTextArea textArea;

    public ScriptEditor() {
        textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        RTextScrollPane sp = new RTextScrollPane(textArea);
        add(sp);
        JButton runScriptBtn = new JButton("Run Script");
        runScriptBtn.addActionListener(e -> {
            if (hasScript != null) {
                try {
                    saveScript();
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
                Engine.getInstance().invokeScript(hasScript);
            }
        });
        add(runScriptBtn);
    }

    public static void main(String[] args) {
        // Start all Swing applications on the EDT.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ScriptEditor().setVisible(true);
            }
        });
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        EntityList source = (EntityList) e.getSource();
        Entity selected = source.getSelectedValue();
        if (selected != null) {
            try {
                saveScript();
                if (selected instanceof EntityDecorator) {
                    selected = ((EntityDecorator) selected).getEntity();
                }
                if (selected instanceof HasScript) {
                    setEnabled(true);
                    textArea.setEnabled(true);
                    String script = ((HasScript) selected).getScript();

                    textArea.getDocument().remove(0, textArea.getDocument().getLength());
                    this.hasScript = (HasScript) selected;
                    textArea.getDocument().insertString(0, script, null);
                } else {
                    textArea.getDocument().remove(0, textArea.getDocument().getLength());
                    setEnabled(false);
                    textArea.setEnabled(false);
                    this.hasScript = null;
                }
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void saveScript() throws BadLocationException {
        if (this.hasScript != null) {
            this.hasScript.setScript(textArea.getDocument().getText(0, textArea.getDocument().getLength()));
        }
    }
}
