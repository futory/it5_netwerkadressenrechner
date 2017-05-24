package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{

    private JPanel button_panel;
    private JPanel content_panel;
    private JPanel panel_2;
    private JPanel panel_3;
    private JPanel panel_4;

    private JButton btnAddSubnet;

    private Color textColor = new Color(51,153,255);


    public MainFrame() {
        setSize(new Dimension(400,450));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        getContentPane().setLayout(new BorderLayout(5, 5));

        button_panel = new JPanel();
        button_panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Buttons", TitledBorder.LEADING, TitledBorder.TOP, null, textColor));
        button_panel.setBackground(Color.WHITE);
        getContentPane().add(button_panel, BorderLayout.SOUTH);

        btnAddSubnet = new JButton("Create Subnet");
        btnAddSubnet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                initSubnetFrame();
            }
        });
        btnAddSubnet.setToolTipText("opens a new window to define a new subnet");
        btnAddSubnet.setForeground(Color.BLACK);
        btnAddSubnet.setFont(new Font("Tahoma", Font.PLAIN, 13));
        button_panel.add(btnAddSubnet);

        content_panel = new JPanel();
        content_panel.setBackground(Color.WHITE);
        content_panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Subnets", TitledBorder.LEADING, TitledBorder.TOP, null, textColor));
        content_panel.setLayout(new BoxLayout(content_panel, BoxLayout.Y_AXIS));
        getContentPane().add(content_panel, BorderLayout.CENTER);

        panel_2 = new JPanel();
        panel_2.setBackground(Color.WHITE);
        getContentPane().add(panel_2, BorderLayout.WEST);
        panel_3 = new JPanel();
        panel_3.setBackground(Color.WHITE);
        getContentPane().add(panel_3, BorderLayout.EAST);

        panel_4 = new JPanel();
        panel_4.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Tabs", TitledBorder.LEADING, TitledBorder.TOP, null, textColor));
        panel_4.setBackground(Color.WHITE);
        getContentPane().add(panel_4, BorderLayout.NORTH);
        setForeground(Color.WHITE);
        setBackground(Color.WHITE);
        setVisible(true);
    }

    private void initSubnetFrame() {
        SubnetFrame subnetFrame = new SubnetFrame(this);
    }

    public static void main(String[] args) {
        MainFrame m = new MainFrame();
    }

    public void addSubnet(SubnetPanel sPanel) {
        content_panel.add(new SubnetLabel(sPanel));
        refreshContentPanel();
    }

    private void refreshContentPanel() {
        content_panel.revalidate();
        content_panel.repaint();
    }

    private void destroyChild(JPanel component, SubnetLabel subnetLabel) {
        component.remove(subnetLabel);
        refreshContentPanel();
    }

    private class SubnetLabel extends JPanel{
        private String name, subnetMask, netID;
        private JLabel nameLabel;
        private JButton btnEdit;
        private JButton btnDelete;

        public SubnetLabel(SubnetPanel sPanel){
            this.name = sPanel.getName();
            this.subnetMask = sPanel.getSubnetMask();
            this.netID = sPanel.getNetID();
            this.setLayout(new FlowLayout(FlowLayout.RIGHT));
            this.setBackground(Color.WHITE);
            this.setMaximumSize(new Dimension(400, 40));

            btnDelete = new JButton("");
            btnDelete.setToolTipText("delete this Host Address");
            btnDelete.setIcon(new ImageIcon("resources/delete.png"));
            btnDelete.setBorderPainted(false);
            btnDelete.setMargin(new Insets(0, 0, 0, 0));
            btnDelete.setContentAreaFilled(false);
            btnDelete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    destroy();
                }
            });

            btnEdit = new JButton("");
            btnEdit.setToolTipText("edit this Host Address");
            btnEdit.setIcon(new ImageIcon("resources/pencil2.png"));
            btnEdit.setBorderPainted(false);
            btnEdit.setMargin(new Insets(0, 0, 0, 0));
            btnEdit.setContentAreaFilled(false);

            nameLabel = buildLabel();
            nameLabel.setBackground(Color.WHITE);
            nameLabel.setOpaque(true);
            this.add(nameLabel);
            this.add(Box.createRigidArea(new Dimension(10,0)));
            this.add(btnEdit);
            this.add(Box.createRigidArea(new Dimension(10,0)));
            this.add(btnDelete);
        }

        private JLabel buildLabel() {
            String label = "";
            if(!this.name.isEmpty())
                label += this.name + " | ";
            if(!this.netID.isEmpty())
                label += this.netID + " | ";
            if(!this.subnetMask.isEmpty())
                label += this.subnetMask;
            return new JLabel(label);
        }

        public String getName(){ return this.name; }

        private void destroy() {
            destroyChild(content_panel,this);
        }
    }
}
