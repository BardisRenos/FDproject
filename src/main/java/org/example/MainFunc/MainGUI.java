package org.example.MainFunc;

import org.example.CompDBVOL.GUICompareDBVOL;
import org.example.DBVOL.DBVOL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    JButton CompareFile;
    JButton Prod;
    JButton BackUp;
    JButton DiskCompare;
    JButton Exit;
    JButton DBVOL;
    JButton CompareDBVOL;
    JLabel Copyright;

    public static void main(String[] args) {
        (new MainGUI()).setVisible(true);
    }

    public MainGUI() {
        super("Main");
        this.getContentPane().setBackground(Color.yellow);
        this.setSize(300, 300);
        this.setLayout((LayoutManager)null);
        this.Copyright = new JLabel("Created by Renos Bardis");
        this.Copyright.setBounds(5, 250, 270, 20);
        this.CompareFile = new JButton("Compare Files");
        this.CompareFile.setBounds(75, 50, 150, 20);
        this.DBVOL = new JButton("DBVOL");
        this.DBVOL.setBounds(75, 75, 150, 20);
        this.DiskCompare = new JButton("Disk's Report");
        this.DiskCompare.setBounds(75, 100, 150, 20);
        this.CompareDBVOL = new JButton("Comp DBVOL");
        this.CompareDBVOL.setBounds(75, 125, 150, 20);
        this.Exit = new JButton("Exit");
        this.Exit.setBounds(10, 195, 70, 30);
        this.add(this.CompareFile);
        this.add(this.DBVOL);
        this.add(this.CompareDBVOL);
        this.add(this.DiskCompare);
        this.add(this.Copyright);
        this.add(this.Exit);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.setVisible(true);

        this.CompareFile.addActionListener(arg0 -> {
            new FormGUI();
            MainGUI.this.dispose();
        });
        this.DiskCompare.addActionListener(arg0 -> {
            new AnaktisiArxeion();
            MainGUI.this.dispose();
        });

        this.DBVOL.addActionListener(e -> {
            new DBVOL();
            MainGUI.this.dispose();
        });

        this.CompareDBVOL.addActionListener(e -> {
            new GUICompareDBVOL();
            MainGUI.this.dispose();
        });

        this.Exit.addActionListener(arg0 -> System.exit(0));
    }
}
