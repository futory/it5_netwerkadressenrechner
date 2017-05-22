package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by martin on 22/05/17.
 */
public class SubnetFrame extends JFrame implements ActionListener{

    private SubnetFrame(){
        this.setTitle("Add Subnet");
        this.setSize(400, 380);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
    }

    public static SubnetFrame getInstance(){
        return new SubnetFrame();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
