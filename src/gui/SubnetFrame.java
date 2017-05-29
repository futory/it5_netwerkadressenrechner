package gui;

import javax.swing.*;
import java.awt.*;

/**
 * SubnetFrame is a JFrame, that is used to create a new Subnet from TextFields, etc.
 * Dispose on close action
 */
@SuppressWarnings("serial")
public class SubnetFrame extends JFrame {

    private MainFrame mainFrame;

    /**
     * constructor creating the frame
     * @param mainFrame
     */
    public SubnetFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setTitle("Create Subnet");
        setResizable(false);
        setSize(new Dimension(300, 450));
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        getContentPane().setLayout(new BorderLayout(0, 0));

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(tabbedPane);

        JPanel panel_7 = new JPanel();
        panel_7.setBackground(Color.WHITE);
        tabbedPane.addTab("Subnet 1", null, panel_7, null);
        panel_7.setLayout(null);
        panel_7.add(new SubnetPanel(mainFrame, this));
        setVisible(true);
    }

    /**
     * method used to dispose the frame on button presses
     */
    public void closeFrame() {
        this.dispose();
    }
}
