package org.example.MainFunc;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class AnaktisiArxeion extends DiskGUI {
    private static final long serialVersionUID = 1L;
    // FDH999 sorts before FDH1000
    private static final Comparator<String> DISK_ORDER = Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder());
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
                fileNames.setLength(0);
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
            } else {
                OnomasiaArxeiou.setText("Give name to file");
            }
        });

        this.fileExpBackUport.addActionListener(arg0 -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(1);
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setPreferredSize(new Dimension(700, 700));
            int rval = fileChooser.showOpenDialog(null);
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

                String outputFilePath = ((!AnaktisiArxeion.onomatouarxeiouString.isBlank()) && !"Give name to file".equals(AnaktisiArxeion.onomatouarxeiouString)) ? onomatouarxeiouString + ".xlsx" : "merged_output.xlsx";

                if (isSelected) {
                    // Handle multiple files
                    String givenName = AnaktisiArxeion.onomatouarxeiouString.trim();
                    if (selectedFiles != null && selectedFiles.length >= 1 && !givenName.isEmpty() && !givenName.equals("Give name to file")) {
                        // A name was given: one final file with the disks of all the selected files
                        AnaktisiArxeion.this.exportMergedToExcel(AnaktisiArxeion.onomaproorismou, givenName, selectedFiles);
                        JOptionPane.showMessageDialog(null, "The extraction is complete: " + givenName + ".xls");
                    } else if (selectedFiles != null && selectedFiles.length >= 1) {
                        File[] files = selectedFiles.clone();
                        Arrays.sort(files, Comparator.comparing(File::getName));
                        List<String> xlsFiles = new ArrayList<>();
                        for (File datFile : files) {
                            String fileName = datFile.getName().replace(".DAT", "");
                            int result = AnaktisiArxeion.this.exportToExcel(
                                    AnaktisiArxeion.onomaproorismou,
                                    fileName,
                                    datFile.getAbsolutePath());
                            if (result == 1) {
                                System.out.println("Exported: " + datFile.getName());
                                xlsFiles.add(AnaktisiArxeion.onomaproorismou + "/" + fileName + ".xls");
                            } else {
                                System.err.println("Failed to export: " + datFile.getName());
                            }
                        }
                        createSummaryOfSheets(xlsFiles, AnaktisiArxeion.onomaproorismou + "/" + outputFilePath);
                        JOptionPane.showMessageDialog(null, "Extraction completed for all .DAT files and summary report.");
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
                    AnaktisiArxeion.this.listaArxeion.add((AnaktisiArxeion.this.droppedFiles.get(0)).toString());

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
//                    cell.setCellType(CellType.NUMERIC);
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

        // Summary row: total of the second column, below the last disk
        if (rowCount > 0) {
            HSSFRow rowFinal = sheet.createRow(rowCount + 2);
            Cell cellFinal = rowFinal.createCell(1);
            cellFinal.setCellFormula("SUM(B1:B" + rowCount + ")");
        }

        // Write to file
        try (FileOutputStream outputStream = new FileOutputStream(destination + "/" + name + ".xls")) {
            workbook.write(outputStream);
        }

        return 1; // Success
    }

    public int exportMergedToExcel(String destination, String name, File[] files) throws IOException {
        // Disk -> value per selected file
        Map<String, String[]> disks = new TreeMap<>(DISK_ORDER);

        for (int f = 0; f < files.length; f++) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(files[f])))) {
                String fileLine;
                while ((fileLine = br.readLine()) != null) {
                    if (fileLine.contains("PACKID:")) {
                        String[] parts = fileLine.trim().split("\\s+");
                        disks.computeIfAbsent(parts[1], k -> new String[files.length])[f] = parts[4];
                    }
                }
            }
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("FileSize");

        // One column per selected file, named after it
        HSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("PACKID");
        for (int f = 0; f < files.length; f++) {
            header.createCell(f + 1).setCellValue(files[f].getName().replaceAll("(?i)\\.(dat|txt)$", ""));
        }

        int rowCount = 1;
        for (Map.Entry<String, String[]> disk : disks.entrySet()) {
            HSSFRow row = sheet.createRow(rowCount);
            row.createCell(0).setCellValue(disk.getKey());
            for (int f = 0; f < files.length; f++) {
                String value = disk.getValue()[f];
                if (value == null) {
                    continue; // This disk does not exist in this file
                }
                Cell cell = row.createCell(f + 1);
                if (value.matches("-?\\d+")) {
                    cell.setCellValue(Long.parseLong(value));
                } else {
                    cell.setCellValue(value);
                }
            }
            rowCount++;
        }

        // Summary row: total of each file, below the last disk
        if (!disks.isEmpty()) {
            HSSFRow rowFinal = sheet.createRow(rowCount + 2);
            rowFinal.createCell(0).setCellValue("TOTAL");
            for (int f = 0; f < files.length; f++) {
                String col = CellReference.convertNumToColString(f + 1);
                rowFinal.createCell(f + 1).setCellFormula("SUM(" + col + "2:" + col + rowCount + ")");
            }
        }

        for (int c = 0; c <= files.length; c++) {
            sheet.autoSizeColumn(c);
        }

        try (FileOutputStream outputStream = new FileOutputStream(destination + "/" + name + ".xls")) {
            workbook.write(outputStream);
        }

        return 1; // Success
    }

    // One row per disk found in the given files, one column per file, then Sum V and Sum H
    public static void createSummaryOfSheets(List<String> inputFilePaths, String outputFilePath) throws IOException {
        int fileCount = inputFilePaths.size();
        Map<String, Object[]> disks = new TreeMap<>(DISK_ORDER);
        DataFormatter formatter = new DataFormatter();

        for (int i = 0; i < fileCount; i++) {
            try (FileInputStream fis = new FileInputStream(inputFilePaths.get(i));
                 HSSFWorkbook inputWorkbook = new HSSFWorkbook(fis)) {
                for (Row inputRow : inputWorkbook.getSheetAt(0)) {
                    Cell disk = inputRow.getCell(0);
                    Cell value = inputRow.getCell(1);
                    if (disk == null || value == null) {
                        continue; // The total row of the input file is not a disk
                    }
                    disks.computeIfAbsent(formatter.formatCellValue(disk), k -> new Object[fileCount])[i] =
                            value.getCellType() == CellType.NUMERIC ? (Object) value.getNumericCellValue() : formatter.formatCellValue(value);
                }
            }
        }

        try (XSSFWorkbook newWorkbook = new XSSFWorkbook()) {
            Sheet newSheet = newWorkbook.createSheet("MergedColumns");

            Row headerRow = newSheet.createRow(0);
            for (int i = 0; i < fileCount; i++) {
                headerRow.createCell(i + 1).setCellValue(new File(inputFilePaths.get(i)).getName().split("\\.")[0]);
            }

            // The disks start at the third row
            int rowIndex = 2;
            for (Map.Entry<String, Object[]> disk : disks.entrySet()) {
                Row row = newSheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(disk.getKey());
                for (int i = 0; i < fileCount; i++) {
                    Object value = disk.getValue()[i];
                    if (value instanceof Double) {
                        row.createCell(i + 1).setCellValue((Double) value);
                    } else if (value != null) {
                        row.createCell(i + 1).setCellValue(value.toString());
                    }
                }
            }

            if (!disks.isEmpty()) {
                // Sum V: total of each file. Sum H: total of each disk, placed from column B onwards
                Row sumVRow = newSheet.createRow(rowIndex + 3);
                sumVRow.createCell(0).setCellValue("Sum V");
                for (int i = 1; i <= fileCount; i++) {
                    String column = CellReference.convertNumToColString(i);
                    sumVRow.createCell(i).setCellFormula("SUM(" + column + "3:" + column + rowIndex + ")");
                }

                Row sumHRow = newSheet.createRow(rowIndex + 6);
                sumHRow.createCell(0).setCellValue("Sum H");
                String lastColumn = CellReference.convertNumToColString(fileCount);
                for (int i = 1; i <= disks.size(); i++) {
                    int excelRow = i + 2;
                    sumHRow.createCell(i).setCellFormula("SUM(B" + excelRow + ":" + lastColumn + excelRow + ")");
                }

                for (int i = 1; i <= Math.max(fileCount, disks.size()); i++) {
                    newSheet.setColumnWidth(i, 7000); // Wide enough for the numbers
                }
            }
            newSheet.autoSizeColumn(0);

            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                newWorkbook.write(fos);
            }
        }
    }
}
