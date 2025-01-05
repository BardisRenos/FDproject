package org.example.MainFunc;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    JTextField OnomaPRD_DR;
    JLabel ArxeioPRD_DR;
    JLabel ArxeioDR;
    JLabel ArxeioPRD;
    JLabel title;
    JLabel OnomaNeouArxeiou;
    JLabel Copyright;
    JLabel image;
    JTextField OnomasiaArxeiou;
    JTextField OnomaDr;
    JTextField OnomaPRD;
    JTextField OnomaEksagogis;
    JButton Apotelesma;
    JButton DRbutton;
    JButton PRDbutton;
    JButton exit;
    JButton Destination;
    JButton back;
    JTextField OnomaArxieou;
    JCheckBox PRD_DR_Checkbox;

    public GUI() {
        super("File Compare");
        this.setSize(520, 700);  // Adjusted the size to accommodate additional components
        this.getContentPane().setBackground(Color.orange);
        this.setLayout(null); // Set layout to null for absolute positioning

        this.Copyright = new JLabel("Created by Renos Bardis");
        this.Copyright.setBounds(15, 550, 350, 20);  // Adjusted position to avoid overlap

        // DR File
        this.ArxeioDR = new JLabel("Import the DR file:");
        this.ArxeioDR.setBounds(10, 100, 180, 20);
        this.OnomaDr = new JTextField();
        this.OnomaDr.setBounds(150, 80, 180, 20);
        this.DRbutton = new JButton("Choose file");
        this.DRbutton.setBounds(180, 100, 150, 20);

        // PRD File
        this.ArxeioPRD = new JLabel("Import the PRD file:");
        this.ArxeioPRD.setBounds(10, 150, 180, 20);
        this.OnomaPRD = new JTextField();
        this.OnomaPRD.setBounds(150, 130, 180, 20);
        this.PRDbutton = new JButton("Choose file");
        this.PRDbutton.setBounds(180, 150, 150, 20);

        // New Both Files (DR-PRD) Section
        this.ArxeioPRD_DR = new JLabel("Insert the path of DR and PRD files:");
        this.ArxeioPRD_DR.setBounds(10, 210, 220, 20);  // Adjusted position for this section
        this.OnomaPRD_DR = new JTextField();
        this.OnomaPRD_DR.setBounds(150, 240, 180, 20);
        this.PRD_DR_Checkbox = new JCheckBox("Folder");
        this.PRD_DR_Checkbox.setBounds(340, 240, 80, 20); // Checkbox for PRD-DR
        this.PRD_DR_Checkbox.setBounds(340, 240, 80, 20); // Checkbox for PRD-DR

        // New File Name
        this.OnomaArxieou = new JTextField("Give name to new File");
        this.OnomaArxieou.setBounds(180, 290, 150, 20);
        this.OnomaNeouArxeiou = new JLabel("Give a name:");
        this.OnomaNeouArxeiou.setBounds(50, 290, 120, 20);

        // Destination and Buttons
        this.Destination = new JButton("Destin. of Export");
        this.Destination.setBounds(10, 360, 170, 20);
        this.OnomaEksagogis = new JTextField();
        this.OnomaEksagogis.setBounds(200, 360, 150, 20);
        this.exit = new JButton("Exit");
        this.exit.setBounds(300, 430, 65, 30);
        this.Apotelesma = new JButton("Compare Files");
        this.Apotelesma.setBounds(15, 410, 160, 20);
        this.back = new JButton("Back");
        this.back.setBounds(210, 430, 75, 30);

        // Add components to frame
        this.add(this.back);
        this.add(this.ArxeioDR);
        this.add(this.ArxeioPRD);
        this.add(this.exit);
        this.add(this.DRbutton);
        this.add(this.PRDbutton);
        this.add(this.Apotelesma);
        this.add(this.Destination);
        this.add(this.OnomaArxieou);
        this.add(this.OnomaDr);
        this.add(this.OnomaPRD);
        this.add(this.OnomaEksagogis);
        this.add(this.OnomaNeouArxeiou);
        this.add(this.Copyright);
        this.add(this.PRD_DR_Checkbox);
        this.add(this.OnomaPRD_DR);
        this.add(this.ArxeioPRD_DR);

        // Center the frame
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setVisible(true);
    }
}
