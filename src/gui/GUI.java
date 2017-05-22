package gui;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jan-Philipp on 15/05/2017.
 */
public class GUI extends JFrame implements ActionListener{

    private JPanel buttonPanel = new JPanel();
    private JPanel displayPanel = new JPanel();
    private JPanel westPanel = new JPanel();
    private JPanel eastPanel = new JPanel();

    private final Color lightBlue = new Color(176,224,230);

    private JButton addSubnet;

    public GUI(){
        this.setTitle("Network Calculator");
        this.setSize(440, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());

        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(displayPanel, BorderLayout.CENTER);
        this.add(westPanel, BorderLayout.WEST);
        this.add(eastPanel, BorderLayout.EAST);
    }

    public static void main(String[] args) {
        GUI g = new GUI();
        g.run();
    }

    private void run() {
        setPanels();

        addSubnet = createSimpleButton("Add Subnet");

        buttonPanel.add(addSubnet);
    }

    private void setPanels() {
        displayPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        buttonPanel.setBackground(Color.WHITE);
        westPanel.setBackground(Color.WHITE);
        displayPanel.setBackground(Color.WHITE);
        eastPanel.setBackground(Color.WHITE);
    }

    private JButton createSimpleButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.BLACK);
        button.setBackground(lightBlue);
        Border line = new LineBorder(lightBlue);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        button.setBorder(compound);
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == addSubnet) {
            SubnetFrame subnetFrame = SubnetFrame.getInstance();
        }
    }
}
