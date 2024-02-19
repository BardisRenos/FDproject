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
    JButton fileExport;
    JButton ExitButton;
    JButton back;
    JTextField OnomasiaArxeiou;
    JTextField EpilogiArxeiou;
    JTextField EksagogiArxeiou;
    ImageIcon icon;

    public DiskGUI() {
        super("Disk's Report");
        this.setSize(400, 400);
        this.getContentPane().setBackground(Color.lightGray);
        this.setLayout((LayoutManager)null);
        this.back = new JButton("Back");
        this.back.setBounds(220, 300, 65, 30);
        this.ArxeioEisodou = new JLabel("Choose the file:");
        this.ArxeioEisodou.setBounds(20, 150, 110, 20);
        this.ArxeioEksodou = new JLabel("Choose the Desti.:");
        this.ArxeioEksodou.setBounds(20, 210, 120, 20);
        this.EpilogiArxeiou = new JTextField();
        this.EpilogiArxeiou.setBounds(150, 150, 100, 20);
        this.fileChooser = new JButton("Choose");
        this.fileChooser.setBounds(150, 180, 100, 20);
        this.ExitButton = new JButton("Exit");
        this.ExitButton.setBounds(300, 300, 60, 30);
        this.EksagogiArxeiou = new JTextField("");
        this.EksagogiArxeiou.setBounds(150, 210, 100, 20);
        this.fileExport = new JButton("Choose");
        this.fileExport.setBounds(150, 240, 100, 20);
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
        this.add(this.fileChooser);
        this.add(this.ExitButton);
        this.add(this.ArxeioEksodou);
        this.add(this.EksagogiArxeiou);
        this.add(this.fileExport);
        this.add(this.OnomasiaArxeiou);
        this.add(this.Apotelesma);
        this.add(this.copyright);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.setVisible(true);
    }
}
