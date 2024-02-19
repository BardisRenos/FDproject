package org.example.CompDBVOL;

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

public class GUICompareDBVOL extends JFrame {
    private static final long serialVersionUID = 1L;
    JButton reportbutton;
    JButton Exit;
    JButton fileToExport;
    JButton chooseDestination;
    JButton BackButton;
    JTextField ImportFile1;
    JTextField ImportFile2;
    JTextField ImportFile3;
    JTextField ImportFile4;
    JTextField PercentageText;
    JTextField destinationToExtract;
    JTextField FileName;
    JLabel percentage;
    JLabel FileNameLabel;
    private List<File> droppedFiles;
    private ArrayList<String> listaArxeion = new ArrayList();
    private boolean deleteText = true;
    private boolean deleteText2 = true;
    private boolean deleteText3 = true;
    private boolean deleteText4 = true;
    private List<File> droppedFiles2;
    private List<File> droppedFiles3;
    private List<File> droppedFiles4;
    private ArrayList<String> listaArxeion2 = new ArrayList();
    private ArrayList<String> listaArxeion3 = new ArrayList();
    private ArrayList<String> listaArxeion4 = new ArrayList();

    public GUICompareDBVOL() {
        super("Comp DBVOL");
        this.setSize(350, 350);
        this.setLayout((LayoutManager)null);
        this.reportbutton = new JButton("COMPARE");
        this.reportbutton.setBounds(200, 230, 100, 20);
        this.chooseDestination = new JButton("Select destination");
        this.chooseDestination.setBounds(190, 150, 140, 20);
        this.BackButton = new JButton("Back");
        this.BackButton.setBounds(50, 280, 80, 20);
        this.Exit = new JButton("Exit");
        this.Exit.setBounds(250, 280, 80, 20);
        this.ImportFile1 = new JTextField("Prod");
        this.ImportFile1.setBounds(20, 30, 150, 20);
        this.ImportFile2 = new JTextField("Dev");
        this.ImportFile2.setBounds(20, 55, 150, 20);
        this.ImportFile3 = new JTextField("Sit");
        this.ImportFile3.setBounds(20, 80, 150, 20);
        this.ImportFile4 = new JTextField("Uat");
        this.ImportFile4.setBounds(20, 105, 150, 20);
        this.destinationToExtract = new JTextField("Destination");
        this.destinationToExtract.setBounds(20, 150, 100, 20);
        this.FileName = new JTextField();
        this.FileName.setBounds(200, 200, 80, 20);
        this.FileNameLabel = new JLabel("Give a name:");
        this.FileNameLabel.setBounds(20, 200, 120, 20);
        this.add(this.FileNameLabel);
        this.add(this.FileName);
        this.add(this.reportbutton);
        this.add(this.Exit);
        this.add(this.ImportFile1);
        this.add(this.ImportFile2);
        this.add(this.ImportFile3);
        this.add(this.ImportFile4);
        this.add(this.chooseDestination);
        this.add(this.destinationToExtract);
        this.add(this.BackButton);

        this.BackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MainGUI();
                GUICompareDBVOL.this.dispose();
            }
        });

        this.reportbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String onomaArxeiou = GUICompareDBVOL.this.FileName.getText();
                System.out.println(onomaArxeiou);
                System.out.println(GUICompareDBVOL.this.destinationToExtract.getText());

                try {
                    List<List> listaDedomenon = CompareDBVOLStorageName.PrintStoragename(GUICompareDBVOL.this.ImportFile1.getText(), GUICompareDBVOL.this.ImportFile2.getText(), GUICompareDBVOL.this.ImportFile3.getText(), GUICompareDBVOL.this.ImportFile4.getText());
                    if (GUICompareDBVOL.this.ImportFile2.getText().toString().contains("DEV") && ((List)listaDedomenon.get(0)).size() != 0) {
                        CompareDBVOLStorageName.ExtractExcelDev(listaDedomenon, GUICompareDBVOL.this.destinationToExtract.getText(), onomaArxeiou);
                    }

                    if (GUICompareDBVOL.this.ImportFile3.getText().contains("SIT") && ((List)listaDedomenon.get(1)).size() != 0) {
                        CompareDBVOLStorageName.ExtractExcelSit(listaDedomenon, GUICompareDBVOL.this.destinationToExtract.getText(), onomaArxeiou);
                    }

                    if (GUICompareDBVOL.this.ImportFile4.getText().contains("UAT") && ((List)listaDedomenon.get(2)).size() != 0) {
                        CompareDBVOLStorageName.ExtractExcelUat(listaDedomenon, GUICompareDBVOL.this.destinationToExtract.getText(), onomaArxeiou);
                    }
                } catch (IOException var4) {
                    var4.printStackTrace();
                }

                JOptionPane.showMessageDialog((Component)null, "Exported Successfully");
                GUICompareDBVOL.this.ImportFile2.setText("");
                GUICompareDBVOL.this.ImportFile3.setText("");
                GUICompareDBVOL.this.ImportFile4.setText("");
                GUICompareDBVOL.this.FileName.setText("");
            }
        });
        this.ImportFile1.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(1);
                    GUICompareDBVOL.this.droppedFiles = (List)evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    GUICompareDBVOL.this.listaArxeion.add(((File)GUICompareDBVOL.this.droppedFiles.get(0)).toString());
                    Iterator var3 = GUICompareDBVOL.this.droppedFiles.iterator();

                    while(var3.hasNext()) {
                        File file = (File)var3.next();
                        if (GUICompareDBVOL.this.deleteText) {
                            GUICompareDBVOL.this.ImportFile1.setText("");
                            GUICompareDBVOL.this.ImportFile1.setText(file.toString() + "\n");
                            GUICompareDBVOL.this.deleteText = false;
                        } else {
                            GUICompareDBVOL.this.ImportFile1.setText(file.toString() + "\n");
                        }
                    }
                } catch (Exception var4) {
                    var4.printStackTrace();
                }

            }
        });
        this.ImportFile2.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(1);
                    GUICompareDBVOL.this.droppedFiles2 = (List)evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    GUICompareDBVOL.this.listaArxeion2.add(((File)GUICompareDBVOL.this.droppedFiles2.get(0)).toString());
                    Iterator var3 = GUICompareDBVOL.this.droppedFiles2.iterator();

                    while(var3.hasNext()) {
                        File file2 = (File)var3.next();
                        if (GUICompareDBVOL.this.deleteText2) {
                            GUICompareDBVOL.this.ImportFile2.setText("");
                            GUICompareDBVOL.this.ImportFile2.setText(file2.toString() + "\n");
                            GUICompareDBVOL.this.deleteText2 = false;
                        } else {
                            GUICompareDBVOL.this.ImportFile2.setText(file2.toString() + "\n");
                        }
                    }
                } catch (Exception var4) {
                    var4.printStackTrace();
                }

            }
        });
        this.ImportFile3.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(1);
                    GUICompareDBVOL.this.droppedFiles3 = (List)evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    GUICompareDBVOL.this.listaArxeion3.add(((File)GUICompareDBVOL.this.droppedFiles3.get(0)).toString());
                    Iterator var3 = GUICompareDBVOL.this.droppedFiles3.iterator();

                    while(var3.hasNext()) {
                        File file3 = (File)var3.next();
                        if (GUICompareDBVOL.this.deleteText3) {
                            GUICompareDBVOL.this.ImportFile3.setText("");
                            GUICompareDBVOL.this.ImportFile3.setText(file3.toString() + "\n");
                            GUICompareDBVOL.this.deleteText3 = false;
                        } else {
                            GUICompareDBVOL.this.ImportFile3.setText(file3.toString() + "\n");
                        }
                    }
                } catch (Exception var4) {
                    var4.printStackTrace();
                }

            }
        });
        this.ImportFile4.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(1);
                    GUICompareDBVOL.this.droppedFiles4 = (List)evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    GUICompareDBVOL.this.listaArxeion4.add(((File)GUICompareDBVOL.this.droppedFiles4.get(0)).toString());
                    Iterator var3 = GUICompareDBVOL.this.droppedFiles4.iterator();

                    while(var3.hasNext()) {
                        File file4 = (File)var3.next();
                        if (GUICompareDBVOL.this.deleteText4) {
                            GUICompareDBVOL.this.ImportFile4.setText("");
                            GUICompareDBVOL.this.ImportFile4.setText(file4.toString() + "\n");
                            GUICompareDBVOL.this.deleteText4 = false;
                        } else {
                            GUICompareDBVOL.this.ImportFile4.setText(file4.toString() + "\n");
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
                    GUICompareDBVOL.this.destinationToExtract.setText(fileChooser.getSelectedFile().toString());
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
        new GUICompareDBVOL();
    }
}
