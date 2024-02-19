package org.example.DBVOL;

import org.example.MainFunc.MainGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DBVOL extends JFrame {
    private static final long serialVersionUID = 1L;
    JButton reportbutton;
    JButton Exit;
    JButton fileToExport;
    JButton chooseDestination;
    JButton BackButton;
    JTextField ImportFile;
    JTextField PercentageText;
    JTextField destinationToExtract;
    JTextField FileName;
    JLabel percentage;
    JLabel FileNameLabel;
    private List<File> droppedFiles;
    private ArrayList<String> listaArxeion = new ArrayList();
    private boolean deleteText = true;

    public DBVOL() {
        super("DBVOL");
        this.setSize(350, 350);
        this.setLayout((LayoutManager)null);
        this.reportbutton = new JButton("Report");
        this.reportbutton.setBounds(200, 230, 100, 20);
        this.chooseDestination = new JButton("Select destination");
        this.chooseDestination.setBounds(190, 100, 140, 20);
        this.BackButton = new JButton("Back");
        this.BackButton.setBounds(50, 280, 80, 20);
        this.Exit = new JButton("Exit");
        this.Exit.setBounds(250, 280, 80, 20);
        this.ImportFile = new JTextField("Import file");
        this.ImportFile.setBounds(20, 50, 150, 20);
        this.destinationToExtract = new JTextField("Destination");
        this.destinationToExtract.setBounds(20, 100, 100, 20);
        this.PercentageText = new JTextField();
        this.PercentageText.setBounds(200, 150, 30, 20);
        this.FileName = new JTextField();
        this.FileName.setBounds(200, 200, 80, 20);
        this.percentage = new JLabel("Percentage of the report:");
        this.percentage.setBounds(20, 150, 180, 20);
        this.FileNameLabel = new JLabel("Give a name:");
        this.FileNameLabel.setBounds(20, 200, 120, 20);
        this.add(this.FileNameLabel);
        this.add(this.FileName);
        this.add(this.reportbutton);
        this.add(this.Exit);
        this.add(this.ImportFile);
        this.add(this.chooseDestination);
        this.add(this.percentage);
        this.add(this.PercentageText);
        this.add(this.destinationToExtract);
        this.add(this.BackButton);

        this.BackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MainGUI();
                DBVOL.this.dispose();
            }
        });

        this.reportbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String onomaArxeiou = DBVOL.this.FileName.getText();
                System.out.println(onomaArxeiou);
                System.out.println(DBVOL.this.destinationToExtract.getText());
                CompareFiles dokimiArxeion = new CompareFiles();
                List<Object> arxeio = dokimiArxeion.ExtractReportAll(DBVOL.this.ImportFile.getText());

                try {
                    dokimiArxeion.AllExtract(arxeio, DBVOL.this.destinationToExtract.getText(), onomaArxeiou);
                } catch (IOException var10) {
                    var10.printStackTrace();
                } catch (Throwable ex) {
                    throw new RuntimeException(ex);
                }

                if (DBVOL.this.PercentageText.getText().isEmpty()) {
                    List<Object> arxeioThresholdx = dokimiArxeion.ExtractReportThresold(DBVOL.this.ImportFile.getText(), 70.0);

                    try {
                        dokimiArxeion.AllExtractThreshold(arxeioThresholdx, DBVOL.this.destinationToExtract.getText(), onomaArxeiou);
                    } catch (IOException var9) {
                        var9.printStackTrace();
                    }
                } else {
                    Double limit = Double.valueOf(DBVOL.this.PercentageText.getText());
                    List<Object> arxeioThreshold = dokimiArxeion.ExtractReportThresold(onomaArxeiou, limit);

                    try {
                        dokimiArxeion.AllExtractThreshold(arxeioThreshold, DBVOL.this.destinationToExtract.getText(), onomaArxeiou);
                    } catch (IOException var8) {
                        var8.printStackTrace();
                    }
                }

                DBVOL.this.PercentageText.setText("");
                DBVOL.this.ImportFile.setText("");
                DBVOL.this.destinationToExtract.setText("");
                DBVOL.this.FileName.setText("");
                JOptionPane.showMessageDialog((Component)null, "Exported Successfully");
            }
        });
        this.ImportFile.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(1);
                    DBVOL.this.droppedFiles = (List)evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    DBVOL.this.listaArxeion.add(((File)DBVOL.this.droppedFiles.get(0)).toString());
                    Iterator var3 = DBVOL.this.droppedFiles.iterator();

                    while(var3.hasNext()) {
                        File file = (File)var3.next();
                        if (DBVOL.this.deleteText) {
                            DBVOL.this.ImportFile.setText("");
                            DBVOL.this.ImportFile.setText(file.toString() + "\n");
                            DBVOL.this.deleteText = false;
                        } else {
                            DBVOL.this.ImportFile.setText(file.toString() + "\n");
                        }
                    }
                } catch (Exception var4) {
                    var4.printStackTrace();
                }

            }
        });
        this.chooseDestination.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(1);
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.setPreferredSize(new Dimension(700, 700));
                int rval = fileChooser.showOpenDialog((Component)null);
                if (rval == 0) {
                    DBVOL.this.destinationToExtract.setText(fileChooser.getSelectedFile().toString());
                }

            }
        });
        this.Exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new DBVOL();
    }
}
