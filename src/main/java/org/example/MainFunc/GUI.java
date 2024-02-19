package org.example.MainFunc;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
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

    public GUI() {
        super("File Compare");
        this.setSize(420, 420);
        this.getContentPane().setBackground(Color.orange);
        this.setLayout((LayoutManager)null);
        this.Copyright = new JLabel("Created by Renos Bardis");
        this.Copyright.setBounds(15, 350, 350, 20);
        this.ArxeioDR = new JLabel("Import the DR file:");
        this.ArxeioDR.setBounds(30, 100, 180, 20);
        this.OnomaDr = new JTextField();
        this.OnomaDr.setBounds(150, 80, 180, 20);
        this.ArxeioPRD = new JLabel("Import the PRD file:");
        this.ArxeioPRD.setBounds(30, 150, 180, 20);
        this.OnomaPRD = new JTextField();
        this.OnomaPRD.setBounds(150, 130, 180, 20);
        this.DRbutton = new JButton("Choose the file");
        this.DRbutton.setBounds(150, 100, 150, 20);
        this.PRDbutton = new JButton("Choose the file");
        this.PRDbutton.setBounds(150, 150, 150, 20);
        this.OnomaArxieou = new JTextField("Give name to new File");
        this.OnomaArxieou.setBounds(150, 200, 150, 20);
        this.OnomaNeouArxeiou = new JLabel("Give a name:");
        this.OnomaNeouArxeiou.setBounds(50, 200, 120, 20);
        this.Destination = new JButton("Desti. of Export");
        this.Destination.setBounds(15, 250, 120, 20);
        this.OnomaEksagogis = new JTextField();
        this.OnomaEksagogis.setBounds(150, 250, 150, 20);
        this.exit = new JButton("Exit");
        this.exit.setBounds(300, 300, 55, 30);
        this.Apotelesma = new JButton("Compare Files");
        this.Apotelesma.setBounds(15, 300, 120, 20);
        this.back = new JButton("Back");
        this.back.setBounds(210, 300, 75, 30);
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
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.setVisible(true);
    }
}
