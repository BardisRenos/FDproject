package org.example.MainFunc;

import javax.swing.*;
import java.awt.*;

public class DiskGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    JLabel ArxeioEisodou;
    JLabel ArxeioEksodou;
    JLabel image;
    JLabel copyright;
    JButton Apotelesma;
    JButton fileChooser;
    JButton fileExpBackUport;
    JButton ExitButton;
    JButton back;
    JTextField OnomasiaArxeiou;
    JTextField EpilogiArxeiou;
    JTextField EksagogiArxeiou;
    JCheckBox checkbox;
    ImageIcon icon;

    public DiskGUI() {
        super("Disk's Report");
        this.setSize(550, 450);
        this.getContentPane().setBackground(Color.lightGray);
        this.setLayout(null);

        this.back = new JButton("Back");
        this.back.setBounds(220, 300, 75, 30);

        this.ArxeioEisodou = new JLabel("Choose the file(s):");
        this.ArxeioEisodou.setBounds(20, 150, 150, 20);

        this.ArxeioEksodou = new JLabel("Choose the Destination:");
        this.ArxeioEksodou.setBounds(20, 210, 220, 20);

        this.EpilogiArxeiou = new JTextField();
        this.EpilogiArxeiou.setBounds(250, 150, 100, 20);

        this.checkbox = new JCheckBox("Folder");
        this.checkbox.setBounds(360, 150, 80, 20);

        this.fileChooser = new JButton("Choose");
        this.fileChooser.setBounds(250, 180, 100, 20);

        this.ExitButton = new JButton("Exit");
        this.ExitButton.setBounds(300, 300, 60, 30);

        this.EksagogiArxeiou = new JTextField();
        this.EksagogiArxeiou.setBounds(250, 210, 100, 20);

        this.fileExpBackUport = new JButton("Choose");
        this.fileExpBackUport.setBounds(250, 240, 100, 20);

        this.OnomasiaArxeiou = new JTextField("Give name to file");
        this.OnomasiaArxeiou.setBounds(20, 270, 100, 20);

        this.Apotelesma = new JButton("Extract");
        this.Apotelesma.setBounds(20, 300, 100, 20);

        this.icon = new ImageIcon("img/favicon.jpg");
        this.setIconImage(this.icon.getImage());

        this.copyright = new JLabel("Created by Renos Bardis");
        this.copyright.setBounds(15, 350, 350, 20);

        this.add(this.back);
        this.add(this.ArxeioEisodou);
        this.add(this.EpilogiArxeiou);
        this.add(this.checkbox);
        this.add(this.fileChooser);
        this.add(this.ExitButton);
        this.add(this.fileExpBackUport);
        this.add(this.ArxeioEksodou);
        this.add(this.EksagogiArxeiou);
        this.add(this.OnomasiaArxeiou);
        this.add(this.Apotelesma);
        this.add(this.copyright);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setVisible(true);
    }
}