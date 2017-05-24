package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class SubnetFrame extends JFrame {

    private MainFrame mainFrame;

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

    public void closeFrame() {
        this.dispose();
    }
}
