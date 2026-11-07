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
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    JCheckBox folderCheckbox;
    private File[] selectedFiles;
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
        this.folderCheckbox = new JCheckBox("Folder");
        this.folderCheckbox.setBounds(190, 50, 80, 20);
        this.add(this.folderCheckbox);
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

        this.folderCheckbox.addActionListener(e -> {
            if (this.folderCheckbox.isSelected()) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);
                if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFiles().length > 0) {
                    this.selectedFiles = fileChooser.getSelectedFiles();
                    this.ImportFile.setText(this.selectedFiles.length + " files selected");
                    this.ImportFile.setEditable(false);
                } else {
                    this.folderCheckbox.setSelected(false);
                }
            } else {
                this.selectedFiles = null;
                this.ImportFile.setEditable(true);
                this.ImportFile.setText("Import file");
            }
        });

        this.reportbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (DBVOL.this.folderCheckbox.isSelected()) {
                    DBVOL.this.reportForFiles();
                    return;
                }
                String onomaArxeiou = DBVOL.this.FileName.getText();
                System.out.println(onomaArxeiou);
                System.out.println(DBVOL.this.destinationToExtract.getText());
                CompareFiles dokimiArxeion = new CompareFiles();
                List<Object> arxeio = dokimiArxeion.ExtractReportAll(DBVOL.this.ImportFile.getText());
                if (arxeio.isEmpty()) {
                    JOptionPane.showMessageDialog((Component)null, "No DBVOL data was found in the file." + DBVOL.this.problemsText(dokimiArxeion));
                    return;
                }

                try {
                    dokimiArxeion.AllExtract(arxeio, DBVOL.this.destinationToExtract.getText(), onomaArxeiou);
                } catch (Throwable ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog((Component)null, "The report failed: " + ex.getMessage());
                    return;
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
                    List<Object> arxeioThreshold = dokimiArxeion.ExtractReportThresold(DBVOL.this.ImportFile.getText(), limit);

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
                JOptionPane.showMessageDialog((Component)null, dokimiArxeion.getProblems().isEmpty()
                        ? "Exported Successfully"
                        : "Exported with problems." + DBVOL.this.problemsText(dokimiArxeion));
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

    // One pair of .xls per selected file, plus one final file with the Available Pages of every file
    private void reportForFiles() {
        String finalName = this.FileName.getText().trim();
        String destination = this.destinationToExtract.getText().trim();
        if (finalName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please give a name for the final file.");
            return;
        }
        if (!new File(destination).isDirectory()) {
            JOptionPane.showMessageDialog(null, "Please select a destination folder.");
            return;
        }

        double limit = 70.0;
        String percentageText = this.PercentageText.getText().trim();
        if (!percentageText.isEmpty()) {
            try {
                limit = Double.parseDouble(percentageText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "The percentage is not a number: " + percentageText);
                return;
            }
        }

        File[] files = this.selectedFiles.clone();
        Arrays.sort(files, Comparator.comparing(File::getName));
        for (File file : files) {
            if (baseName(file).equals(finalName)) {
                JOptionPane.showMessageDialog(null, "The final file cannot have the same name as a selected file: " + finalName);
                return;
            }
        }

        CompareFiles report = new CompareFiles();
        Map<String, List<Object>> allData = new LinkedHashMap<>();
        List<String> noData = new ArrayList<>();
        try {
            for (File file : files) {
                List<Object> all = report.ExtractReportAll(file.getPath());
                if (all.isEmpty()) {
                    noData.add(file.getName());
                    continue;
                }
                report.AllExtract(all, destination, baseName(file));
                report.AllExtractThreshold(report.ExtractReportThresold(file.getPath(), limit), destination, baseName(file));
                allData.put(file.getName(), all);
            }
            if (allData.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No DBVOL data was found in the selected files." + problemsText(report));
                return;
            }
            report.FullExtract(allData, destination, finalName);
        } catch (Throwable e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "The report failed: " + e.getMessage());
            return;
        }

        this.folderCheckbox.setSelected(false);
        this.selectedFiles = null;
        this.ImportFile.setEditable(true);
        this.ImportFile.setText("Import file");
        this.PercentageText.setText("");
        this.destinationToExtract.setText("");
        this.FileName.setText("");
        String message = (report.getProblems().isEmpty() ? "Exported Successfully: " : "Exported with problems: ") + finalName + ".xls";
        if (!noData.isEmpty()) {
            message += "\nNo DBVOL data found, skipped: " + String.join(", ", noData);
        }
        JOptionPane.showMessageDialog(null, message + problemsText(report));
    }

    private String problemsText(CompareFiles report) {
        if (report.getProblems().isEmpty()) {
            return "";
        }
        return "\nThe data of these files may be incomplete:\n" + String.join("\n", report.getProblems());
    }

    private String baseName(File file) {
        return file.getName().replaceAll("\\.[^.]+$", "");
    }

    public static void main(String[] args) {
        new DBVOL();
    }
}
