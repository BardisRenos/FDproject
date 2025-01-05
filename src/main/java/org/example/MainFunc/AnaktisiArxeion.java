package org.example.MainFunc;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AnaktisiArxeion extends DiskGUI {
    private static final long serialVersionUID = 1L;
    public static String line = null;
    public static String[] column = null;
    public static String onomatouarxeiouString;
    public static String onomaproorismou;
    public static String onomaEpilogiArxeiou;
    private boolean deleteText = true;
    private List<File> droppedFiles;
    private ArrayList<String> listaArxeion = new ArrayList();
    private File[] selectedFiles;
    private StringBuilder fileNames = new StringBuilder();
    boolean isSelected = false;

    public static void main(String[] args) {
        new AnaktisiArxeion();
    }

    public AnaktisiArxeion() {
        this.ExitButton.addActionListener(arg0 -> System.exit(0));

        this.fileChooser.addActionListener(arg0 -> {
            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setAcceptAllFileFilterUsed(true);
            fileChooser.setPreferredSize(new Dimension(700, 700));

            int rval = fileChooser.showOpenDialog(null);
            if (rval == JFileChooser.APPROVE_OPTION) {
                AnaktisiArxeion.this.EpilogiArxeiou.setText(fileChooser.getSelectedFile().toString());
            }
        });

        this.checkbox.addActionListener(e -> {
            isSelected = checkbox.isSelected();

            if (isSelected) {
                fileChooser.setBackground(Color.LIGHT_GRAY);
                fileChooser.setEnabled(false);
                fileChooser.setText("");
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);  // Enable multi-selection
                int returnValue = fileChooser.showOpenDialog(AnaktisiArxeion.this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFiles = fileChooser.getSelectedFiles();  // Get the selected files
                    for (File file : selectedFiles) {
                        fileNames.append(file.getAbsolutePath()).append("; ");
                    }
                }
                AnaktisiArxeion.this.EpilogiArxeiou.setText(fileNames.toString());
                AnaktisiArxeion.this.OnomasiaArxeiou.setBackground(Color.DARK_GRAY);
                AnaktisiArxeion.this.OnomasiaArxeiou.setEditable(false);
            } else {
                OnomasiaArxeiou.setBackground(Color.WHITE);
                OnomasiaArxeiou.setEditable(true);
                OnomasiaArxeiou.setText("Give name to file");
            }
        });

        this.fileExpBackUport.addActionListener(arg0 -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(1);
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setPreferredSize(new Dimension(700, 700));
            int rval = fileChooser.showOpenDialog((Component)null);
            if (rval == 0) {
                AnaktisiArxeion.this.EksagogiArxeiou.setText(fileChooser.getSelectedFile().toString());
            }

        });

        this.Apotelesma.addActionListener(arg0 -> {
            try {
                // Retrieve input values and sanitize paths
                AnaktisiArxeion.onomaEpilogiArxeiou = AnaktisiArxeion.this.EpilogiArxeiou.getText().replace("\\", "/");
                AnaktisiArxeion.onomatouarxeiouString = AnaktisiArxeion.this.OnomasiaArxeiou.getText();
                AnaktisiArxeion.onomaproorismou = AnaktisiArxeion.this.EksagogiArxeiou.getText().replace("\\", "/");

                if (isSelected) {
                    // Handle multiple files
                    if (selectedFiles.length >= 1) {
                        for (File datFile : selectedFiles) {
                            String fileName = datFile.getName().replace(".DAT", "");
                            int result = AnaktisiArxeion.this.exportToExcel(
                                    AnaktisiArxeion.onomaproorismou,
                                    fileName,
                                    datFile.getAbsolutePath()
                            );
                            if (result == 1) {
                                System.out.println("Exported: " + datFile.getName());
                            } else {
                                System.err.println("Failed to export: " + datFile.getName());
                            }
                        }
                        JOptionPane.showMessageDialog(null, "Extraction completed for all .DAT files.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No files selected. Please select .DAT files to process.");
                    }
                } else {
                    // Handle single file
                    int result = AnaktisiArxeion.this.exportToExcel(
                            AnaktisiArxeion.onomaproorismou,
                            AnaktisiArxeion.onomatouarxeiouString,
                            AnaktisiArxeion.onomaEpilogiArxeiou
                    );

                    if (result == 1) {
                        JOptionPane.showMessageDialog(null, "The extraction is complete.");
                    } else {
                        JOptionPane.showMessageDialog(null, "The extraction failed.");
                    }
                }

                // Reset fields after processing
                AnaktisiArxeion.this.EpilogiArxeiou.setText("");
                AnaktisiArxeion.this.EksagogiArxeiou.setText("");
                AnaktisiArxeion.this.OnomasiaArxeiou.setText("");
            } catch (IOException var3) {
                var3.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred: " + var3.getMessage());
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });

        this.EpilogiArxeiou.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(1);
                    AnaktisiArxeion.this.droppedFiles = (List)evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    AnaktisiArxeion.this.listaArxeion.add(((File)AnaktisiArxeion.this.droppedFiles.get(0)).toString());

                    for (File file : AnaktisiArxeion.this.droppedFiles) {
                        if (AnaktisiArxeion.this.deleteText) {
                            AnaktisiArxeion.this.EpilogiArxeiou.setText("");
                            AnaktisiArxeion.this.EpilogiArxeiou.setText(file.toString() + "\n");
                            AnaktisiArxeion.this.deleteText = false;
                        } else {
                            AnaktisiArxeion.this.EpilogiArxeiou.setText(file.toString() + "\n");
                        }
                    }
                } catch (Exception var4) {
                    var4.printStackTrace();
                }

            }
        });

        this.OnomasiaArxeiou.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                OnomasiaArxeiou.setText("");
            }
        });

        this.back.addActionListener(arg0 -> {
            new MainGUI();
            AnaktisiArxeion.this.dispose();
        });
    }

    public int exportToExcel(String destination, String name, String epilogiarxeiou) throws Throwable {
        int counter = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(epilogiarxeiou)));
        ArrayList<String> stili1 = new ArrayList<>();
        ArrayList<String> stili2 = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            if (line.contains("PACKID:")) {
                column = line.split("\\s+");
                stili1.add(column[1]); // First column data
                stili2.add(column[4]); // Second column data
                counter++;
            }
        }

        String[][] Pinakas = new String[counter][2];
        for (int i = 0; i < Pinakas.length; i++) {
            Pinakas[i][0] = stili1.get(i);
            Pinakas[i][1] = stili2.get(i);
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("FileSize");

        int rowCount = 0;

        for (String[] rowStrings : Pinakas) {
            HSSFRow row = sheet.createRow(rowCount);
            int columnCount = 0;

            for (String columnString : rowStrings) {
                Cell cell = row.createCell(columnCount);

                // Check for numeric data
                if (columnString.matches("-?\\d+")) {
                    int number = Integer.parseInt(columnString);
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(number);
                } else {
                    cell.setCellValue(columnString);
                }

                // Auto-size the column to prevent ####
                sheet.autoSizeColumn(columnCount);

                columnCount++;
            }

            rowCount++;
        }

        // Write to file
        try (FileOutputStream outputStream = new FileOutputStream(destination + "/" + name + ".xls")) {
            workbook.write(outputStream);
        }

        return 1; // Success
    }

}
