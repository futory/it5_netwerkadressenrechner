package GUI;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Jan-Philipp on 15/05/2017.
 */
public class GUI {

    JFrame frame = new JFrame("FrameDemo");
    GridLayout gridMain = new GridLayout(2,2);
    GridLayout gridNetworks = new GridLayout(3,1);
    JButton plus = new JButton("+");
    JButton minus = new JButton("-");
    JLabel networks = new JLabel("Networks");
    JTextField networksField = new JTextField();
    EmptyBorder border1 = new EmptyBorder(20, 20, 20,20 );
    private JPanel panel1;
    private JList list1;
    private JButton button1;
    private JButton button2;

    public static void main(String[] args) {
        GUI g = new GUI();
        g.run();
    }

    private void run() {
        frame.setLayout(gridMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




        frame.setVisible(true);
        frame.setSize(400,400);
        frame.add(networks);
        frame.add(networksField);
        frame.add(plus);
        frame.add(minus);
        minus.setSize(2,2);

    }

}
