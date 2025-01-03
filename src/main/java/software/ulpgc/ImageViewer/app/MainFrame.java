package software.ulpgc.ImageViewer.app;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import software.ulpgc.ImageViewer.mvc.control.Command;

public class MainFrame extends JFrame {
    private final Map<String, Command> commands = new HashMap<>();
    private final SwingImageDisplay display;

    public MainFrame() {
        setLayout(new BorderLayout());
        setTitle("ImageViewer");
        setSize(900, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(BorderLayout.CENTER, this.display = new SwingImageDisplay(new SwingImageDeserializer()));
        add(BorderLayout.SOUTH, this.buttons());
    }

    private Component buttons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(button("previous"));
        panel.add(button("next"));
        return panel;
    }

    private Component button(final String commandName) {
        JButton button = new JButton(commandName);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (commands.containsKey(commandName)) {
                    (commands.get(commandName)).execute();
                }

            }
        });
        return button;
    }

    public SwingImageDisplay getDisplay() {
        return this.display;
    }

    public MainFrame put(String key, Command value) {
        this.commands.put(key, value);
        return this;
    }
}