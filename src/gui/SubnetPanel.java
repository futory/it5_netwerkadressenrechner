package gui;

import org.mnm.ipv4.subnet.ipv4SubnetUtils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Predicate;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class SubnetPanel extends JPanel {

    private JTextField txtNetworkID;
    private JTextField txtSubnetMask;
    private JTextField txtSubnetName;

    private JPanel panel_3;
    private JPanel panel_4;
    private JPanel panel_6;
    private JPanel panel_2;
    private JPanel panel_1;
    private JPanel panel;
    private JPanel panel_5;

    private JPanel scrollPaneViewPortPane;
    private JPanel hostPanelScrollPaneAncor;

    private MainFrame mainFrame;
    private SubnetFrame subnetFrame;

    private JButton btnNext;

    private String name = "", subnetMask = "", netID = "";

    public SubnetPanel(MainFrame mainFrame, SubnetFrame subnetFrame) {
        setBorder(new LineBorder(new Color(0, 0, 0)));
        this.mainFrame = mainFrame;
        this.subnetFrame = subnetFrame;
        Color textColor = new Color(51,153,255);
        setSize(new Dimension(300, 400));
        setBackground(Color.WHITE);
        setLayout(null);

        panel_4 = new JPanel();
        panel_4.setBackground(Color.WHITE);
        panel_4.setBounds(5, 5, 285, 329);
        add(panel_4);
        panel_4.setLayout(null);

        panel_3 = new JPanel();
        panel_3.setBounds(0, 0, 285, 41);
        panel_4.add(panel_3);
        panel_3.setLayout(new BorderLayout(0, 0));

        panel_1 = new JPanel();
        panel_3.add(panel_1, BorderLayout.CENTER);
        panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Subnet Name", TitledBorder.LEADING, TitledBorder.TOP, null, textColor));
        panel_1.setBackground(Color.WHITE);
        panel_1.setLayout(new BorderLayout(0, 0));

        txtSubnetName = new JTextField();
        txtSubnetName.setHorizontalAlignment(SwingConstants.LEFT);
        panel_1.add(txtSubnetName, BorderLayout.CENTER);
        txtSubnetName.setToolTipText("The name of the subnet");
        txtSubnetName.setColumns(10);

        panel_2 = new JPanel();
        panel_2.setBounds(0, 52, 285, 41);
        panel_4.add(panel_2);
        panel_2.setBackground(Color.WHITE);
        panel_2.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Network ID", TitledBorder.LEADING, TitledBorder.TOP, null, textColor));
        panel_2.setLayout(new BorderLayout(0, 0));

        txtNetworkID = new JTextField();
        panel_2.add(txtNetworkID);
        txtNetworkID.setColumns(10);

        panel = new JPanel();
        panel.setBounds(0, 104, 285, 41);
        panel_4.add(panel);
        panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Subnet Mask", TitledBorder.LEADING, TitledBorder.TOP, null, textColor));
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout(0, 0));

        txtSubnetMask = new JTextField();
        panel.add(txtSubnetMask);
        txtSubnetMask.setColumns(10);

        panel_5 = new JPanel();
        panel_5.setBounds(5, 345, 285, 33);
        add(panel_5);
        panel_5.setBackground(Color.WHITE);

        JButton btnCreate = new JButton("Create");
        btnCreate.setToolTipText("Create the Subnet");
        btnCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                sendSubnet();
                subnetFrame.closeFrame();
            }
        });
        panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel_5.add(btnCreate);

        btnNext = new JButton("Next");
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(sendSubnet())
                    clearFields();
            }
        });
        panel_5.add(btnNext);

        JPanel hostPanel = new JPanel();
        hostPanel.setBounds(0, 156, 285, 174);
        hostPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Host Addresses", TitledBorder.LEADING, TitledBorder.TOP, null, textColor));
        hostPanel.setBackground(Color.WHITE);
        hostPanel.setLayout(new BorderLayout(0, 0));

        JPanel hostPanelRootPane = new JPanel(new BorderLayout());
        hostPanel.add(hostPanelRootPane);

        JPanel hostPanelButtonPane = new JPanel();
        hostPanelButtonPane.setBackground(Color.WHITE);
        hostPanelRootPane.add(hostPanelButtonPane, BorderLayout.NORTH);
        hostPanelButtonPane.setLayout(new BorderLayout(0, 0));

        JButton btnAddHost = new JButton("");
        btnAddHost.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                scrollPaneViewPortPane.add(new HostLabel("192.168.0.0"));
                repaintScrollPaneViewPortPane();
            }
        });
        hostPanelButtonPane.add(btnAddHost, BorderLayout.EAST);
        btnAddHost.setToolTipText("Add a Host Address");
        btnAddHost.setIcon(new ImageIcon("resources/add.png"));
        btnAddHost.setBorderPainted(false);
        btnAddHost.setMargin(new Insets(0, 0, 0, 0));
        btnAddHost.setContentAreaFilled(false);

        hostPanelScrollPaneAncor = new JPanel();
        hostPanelScrollPaneAncor.setBackground(Color.WHITE);
        hostPanelRootPane.add(hostPanelScrollPaneAncor, BorderLayout.CENTER);
        hostPanelScrollPaneAncor.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        hostPanelScrollPaneAncor.add(scrollPane,BorderLayout.CENTER);

        scrollPaneViewPortPane = new JPanel();
        scrollPaneViewPortPane.setBackground(Color.WHITE);
        scrollPaneViewPortPane.setLayout(new BoxLayout(scrollPaneViewPortPane, BoxLayout.Y_AXIS));
        scrollPaneViewPortPane.setAlignmentX(LEFT_ALIGNMENT);
        scrollPane.setViewportView(scrollPaneViewPortPane);

        panel_4.add(hostPanel);
        setBackground(Color.WHITE);
    }

    private boolean sendSubnet() {
        transferFields();
        boolean testPassed = true;
        if(this.name.isEmpty()) {
            updateTextArea(this.txtSubnetName);
            testPassed = false;
        }

        if(!ipv4SubnetUtils.isValidIP(this.netID)){
            updateTextArea(this.txtNetworkID);
            testPassed = false;
        }

        if(!ipv4SubnetUtils.isValidSubnetMask(this.subnetMask)){
            updateTextArea(this.txtSubnetMask);
            testPassed = false;
        }

        if(testPassed)
            mainFrame.addSubnet(this);
        return testPassed;
    }

    private void updateTextArea(JTextField jTextField) {
        jTextField.setForeground(Color.RED);
        jTextField.setBorder(new LineBorder(Color.RED));
        jTextField.revalidate();
        jTextField.repaint();
    }

    private void transferFields() {
        this.name = this.txtSubnetName.getText();
        this.netID = this.txtNetworkID.getText();
        this.subnetMask = this.txtSubnetMask.getText();
    }

    private void clearFields() {
        this.txtSubnetName.setText("");
        this.txtSubnetMask.setText("");
        this.txtNetworkID.setText("");
        this.scrollPaneViewPortPane.removeAll();
        repaintScrollPaneViewPortPane();
    }

    private void repaintScrollPaneViewPortPane() {
        this.scrollPaneViewPortPane.revalidate();
        this.scrollPaneViewPortPane.repaint();
    }

    public String getName() {
        return this.name;
    }

    public String getSubnetMask() {
        return this.subnetMask;
    }

    public String getNetID() {
        return this.netID;
    }

    private class HostLabel extends JPanel{
        private String name;
        private JLabel nameLabel;
        private JButton btnEdit;
        private JButton btnDelete;

        public HostLabel(String name){
            this.name = name;
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.setBackground(Color.WHITE);

            btnDelete = new JButton("");
            btnDelete.setToolTipText("delete this Host Address");
            btnDelete.setIcon(new ImageIcon("resources/delete.png"));
            btnDelete.setBorderPainted(false);
            btnDelete.setMargin(new Insets(0, 0, 0, 0));
            btnDelete.setContentAreaFilled(false);

            btnEdit = new JButton("");
            btnEdit.setToolTipText("edit this Host Address");
            btnEdit.setIcon(new ImageIcon("resources/pencil2.png"));
            btnEdit.setBorderPainted(false);
            btnEdit.setMargin(new Insets(0, 0, 0, 0));
            btnEdit.setContentAreaFilled(false);

            nameLabel = new JLabel(name);
            nameLabel.setBackground(Color.WHITE);
            nameLabel.setOpaque(true);
            this.add(nameLabel);
            this.add(Box.createRigidArea(new Dimension(100,0)));
            this.add(btnEdit);
            this.add(Box.createRigidArea(new Dimension(25,0)));
            this.add(btnDelete);
        }

        public String getName(){ return this.name; }
    }
}
