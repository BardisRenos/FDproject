package org.example.MainFunc;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class FormGUI extends GUI {
    public static String folder = null;
    public static String DRfileName = null;
    public static String PRDfileName = null;
    private static final long serialVersionUID = 1L;
    public static String onomatouarxeiouString;
    public static String onomaproorismou;
    public static String onomaArxikouDR;
    public static String onomaArxikouPRD;
    public static String destination = null;
    private boolean deleteText = true;
    private List<File> droppedFiles;
    private ArrayList<String> listaArxeion = new ArrayList<>();
    private File[] selectedFiles;
    private StringBuilder fileNames = new StringBuilder();

    FormGUI() {
        this.exit.addActionListener(e -> {
            Thread threadExit = new Thread(() -> System.exit(0));
            threadExit.start();
        });
        this.OnomaArxieou.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                FormGUI.this.OnomaArxieou.setText("");
            }
        });

        this.Destination.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(1);
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setPreferredSize(new Dimension(700, 700));
            int rval = fileChooser.showOpenDialog((Component)null);
            if (rval == 0) {
                FormGUI.this.OnomaEksagogis.setText(fileChooser.getSelectedFile().toString());
            }

        });
        this.DRbutton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(0);
            fileChooser.setAcceptAllFileFilterUsed(true);
            fileChooser.setPreferredSize(new Dimension(700, 700));
            int rval = fileChooser.showOpenDialog((Component)null);
            if (rval == 0) {
                FormGUI.this.OnomaDr.setText(fileChooser.getSelectedFile().toString());
                FormGUI.folder = fileChooser.getCurrentDirectory().toString();
            }
        });
        this.PRDbutton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(FormGUI.folder);
            fileChooser.setFileSelectionMode(0);
            fileChooser.setAcceptAllFileFilterUsed(true);
            fileChooser.setPreferredSize(new Dimension(700, 700));
            int rval = fileChooser.showOpenDialog((Component)null);
            if (rval == 0) {
                FormGUI.this.OnomaPRD.setText(fileChooser.getSelectedFile().toString());
            }
        });
        this.OnomaDr.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(1);
                    FormGUI.this.droppedFiles = (List)evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    FormGUI.this.listaArxeion.add(((File)FormGUI.this.droppedFiles.get(0)).toString());
                    Iterator var3 = FormGUI.this.droppedFiles.iterator();

                    while(var3.hasNext()) {
                        File file = (File)var3.next();
                        if (FormGUI.this.deleteText) {
                            FormGUI.this.OnomaDr.setText("");
                            FormGUI.this.OnomaDr.setText(file.toString() + "\n");
                            FormGUI.this.deleteText = false;
                        } else {
                            FormGUI.this.OnomaDr.setText(file.toString() + "\n");
                        }
                    }
                } catch (Exception var4) {
                    var4.printStackTrace();
                }

            }
        });
        this.OnomaPRD.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(1);
                    FormGUI.this.droppedFiles = (List)evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    FormGUI.this.listaArxeion.add(((File)FormGUI.this.droppedFiles.get(0)).toString());
                    Iterator var3 = FormGUI.this.droppedFiles.iterator();

                    while(var3.hasNext()) {
                        File file = (File)var3.next();
                        if (FormGUI.this.deleteText) {
                            FormGUI.this.OnomaPRD.setText("");
                            FormGUI.this.OnomaPRD.setText(file.toString() + "\n");
                            FormGUI.this.deleteText = false;
                        } else {
                            FormGUI.this.OnomaPRD.setText(file.toString() + "\n");
                        }
                    }
                } catch (Exception var4) {
                    var4.printStackTrace();
                }

            }
        });

        this.PRD_DR_Checkbox.addActionListener(e -> {
            // Check if PRD_DR_Checkbox is selected
            boolean isSelected = PRD_DR_Checkbox.isSelected();

            // Logic for handling multiple files when PRD_DR_Checkbox is selected
            if (isSelected) {
                // Disable/Enable the text fields based on checkbox state
                FormGUI.this.OnomaDr.setEditable(false);
                FormGUI.this.OnomaDr.setBackground(Color.DARK_GRAY);
                FormGUI.this.DRbutton.setEnabled(false);
                FormGUI.this.DRbutton.setBackground(Color.DARK_GRAY);

                FormGUI.this.OnomaPRD.setEditable(false);
                FormGUI.this.OnomaPRD.setBackground(Color.DARK_GRAY);
                FormGUI.this.PRDbutton.setEnabled(false);
                FormGUI.this.PRDbutton.setBackground(Color.DARK_GRAY);

                FormGUI.this.OnomaArxieou.setEditable(false);
                FormGUI.this.OnomaArxieou.setBackground(Color.DARK_GRAY);
                FormGUI.this.OnomaArxieou.setEnabled(false);

                // You can add a file chooser or logic for selecting multiple files when the checkbox is selected
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);  // Enable multi-selection

                int returnValue = fileChooser.showOpenDialog(FormGUI.this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFiles = fileChooser.getSelectedFiles();  // Get the selected files
                    for (File file : selectedFiles) {
                        fileNames.append(file.getAbsolutePath()).append("; ");
                    }
                }
                FormGUI.this.OnomaPRD_DR.setText(fileNames.toString());
            } else {
                FormGUI.this.OnomaDr.setEditable(true);
                FormGUI.this.OnomaDr.setBackground(Color.WHITE);
                FormGUI.this.DRbutton.setEnabled(true);
                FormGUI.this.DRbutton.setBackground(Color.WHITE);

                FormGUI.this.OnomaPRD.setEditable(true);
                FormGUI.this.OnomaPRD.setBackground(Color.WHITE);
                FormGUI.this.PRDbutton.setEnabled(true);
                FormGUI.this.PRDbutton.setBackground(Color.WHITE);
            }
        });

        this.Apotelesma.addActionListener(e -> {
            FormGUI.onomaproorismou = FormGUI.this.OnomaEksagogis.getText().toString();
            FormGUI.onomaproorismou = FormGUI.onomaproorismou.replace("\\", "/");
            FormGUI.onomatouarxeiouString = FormGUI.this.OnomaArxieou.getText().toString();
            FormGUI.onomaArxikouDR = FormGUI.this.OnomaDr.getText().toString();
            FormGUI.onomaArxikouDR = FormGUI.onomaArxikouDR.replace("\\", "/");
            FormGUI.onomaArxikouPRD = FormGUI.this.OnomaPRD.getText().toString();
            FormGUI.onomaArxikouPRD = FormGUI.onomaArxikouPRD.replace("\\", "/");

            Thread threadButton;
            if (!PRD_DR_Checkbox.isSelected()) {
                threadButton = new Thread(() -> {
                    try {
                        File file = new File(FormGUI.onomaproorismou + "/" + "Compare_Files-" + FormGUI.onomatouarxeiouString);
                        if (file.exists()) {
                            JOptionPane.showMessageDialog(null, "The Directory already exists");
                            FormGUI.this.OnomaArxieou.setText("");
                        } else if (!FormGUI.onomaArxikouDR.equalsIgnoreCase("MFD_DR.txt") && !FormGUI.onomaArxikouPRD.equalsIgnoreCase("MFD_PRD.txt")) {
                            boolean isFolderEmpty = FormGUI.this.savetofile(FormGUI.onomaArxikouDR, FormGUI.onomaArxikouPRD, FormGUI.onomatouarxeiouString, FormGUI.onomaproorismou);
                            if (isFolderEmpty) {
                                JOptionPane.showMessageDialog(null, "There is no different no need to create files");
                            } else {
                                FormGUI.this.exportToExcelDadE(FormGUI.onomaproorismou, FormGUI.onomatouarxeiouString);
                                FormGUI.this.exportToExcelDadT(FormGUI.onomaproorismou, FormGUI.onomatouarxeiouString);
                                FormGUI.this.exportToExcelSize(FormGUI.onomaproorismou, FormGUI.onomatouarxeiouString);
                                JOptionPane.showMessageDialog(null, "The comparison finished successfully");
                                FormGUI.this.OnomaDr.setText("");
                                FormGUI.this.OnomaPRD.setText("");
                                FormGUI.this.OnomaEksagogis.setText("");
                                FormGUI.this.OnomaArxieou.setText("");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Files are not compatible. Please choose a MFD_DR or MFD_PRD files");
                            FormGUI.this.OnomaDr.setText("");
                            FormGUI.this.OnomaPRD.setText("");
                            FormGUI.this.OnomaEksagogis.setText("");
                            FormGUI.this.OnomaArxieou.setText("");
                        }
                    } catch (Throwable var2) {
                        var2.printStackTrace();
                    }
                });
            }
            else {
                threadButton = new Thread(() -> {
                    String[] items = fileNames.toString().split("; ");
                    // Lists to store "DR" and "PRD" items
                    List<String> drList = new ArrayList<>();
                    List<String> prdList = new ArrayList<>();
                    // Iterate through the items and classify them into respective lists
                    for (String item : items) {
                        if (item.contains("MFD_DR")) {
                            drList.add(item); // Add to DR list
                        } else if (item.contains("MFD_PRD")) {
                            prdList.add(item); // Add to PRD list
                        }
                    }
                    boolean isFolderEmpty;
//                    int index = 0;
                    for (String drItem : drList) {
//                        index++;
                        for (String prdItem : prdList) {
                            if (drItem.split("-")[1].substring(0, 6).equals(prdItem.split("-")[1].substring(0, 6))) {
                                FormGUI.onomatouarxeiouString = drItem.split("-")[1].substring(0, 6);
                                try {
                                    isFolderEmpty = FormGUI.this.savetofile(drItem, prdItem, FormGUI.onomatouarxeiouString, FormGUI.onomaproorismou);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                                if (isFolderEmpty) {
                                    JOptionPane.showMessageDialog(null, "There is no different no need to create files");
                                } else {
                                    try {
                                        FormGUI.this.exportToExcelDadE(FormGUI.onomaproorismou, FormGUI.onomatouarxeiouString);
                                        FormGUI.this.exportToExcelDadT(FormGUI.onomaproorismou, FormGUI.onomatouarxeiouString);
                                        FormGUI.this.exportToExcelSize(FormGUI.onomaproorismou, FormGUI.onomatouarxeiouString);
                                    } catch (Throwable ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    FormGUI.this.OnomaEksagogis.setText("");
                                    FormGUI.this.OnomaArxieou.setText("");
                                }
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(null, "The comparison finished successfully");
                });
            }
            threadButton.start();
        });

        this.back.addActionListener(arg0 -> {
            new MainGUI();
            FormGUI.this.dispose();
        });
    }

    public void exportToExcelDadE(String destination, String name) throws Throwable {
        String[] headerStrings = new String[]{"QualifierDR", "FilenameDR", "FcycDR", "DadEDR", "DadTDR", "SizeDR", "F/LDR", "Last Ref DateDR", "Last Ref TimeDR", "QualifierPRD", "FilenamePRD", "FcycPRD", "DadEPRD", "DadTPRD", "SizePRD", "F/LPRD", "Last Ref DatePRD", "Last Ref TimeDR", "Result"};
        String[] column = null;
        LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(destination + "/" + "Compare_Files-" + name + "/" + "Compare_Files-" + name + "DadE" + ".txt"));
        lineNumberReader.skip(Long.MAX_VALUE);
        int resultoflines = lineNumberReader.getLineNumber();
        lineNumberReader.close();
        String[][] pinakas = new String[resultoflines][19];
        FileInputStream file = new FileInputStream(destination + "/" + "Compare_Files-" + name + "/" + "Compare_Files-" + name + "DadE" + ".txt");
        Scanner inScanner = new Scanner(file);

        for(int lineCount = 0; inScanner.hasNextLine(); ++lineCount) {
            column = inScanner.nextLine().split("\\s+");

            for(int i = 0; i < column.length; ++i) {
                pinakas[lineCount][i] = column[i];
            }
        }

        inScanner.close();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("DadE");
        CellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        style.setFont(font);
        int firstrow = 0;
        Row row1 = sheet.createRow(firstrow);
        int columnCount1 = 0;
        String[] rowStrings = headerStrings;
        int rowCount = headerStrings.length;

        String path;
        Cell cell;
        for(int var19 = 0; var19 < rowCount; ++var19) {
            path = rowStrings[var19];
            cell = row1.createCell(columnCount1);
            sheet.autoSizeColumn(path.length());
            cell.setCellValue(path);
            cell.setCellStyle(style);
            ++columnCount1;
        }

        path = destination + "/" + "Compare_Files-" + name + "/" + "Compare_Files-" + name + "DadE" + ".txt";
        Path xpath = Paths.get(path);
        Files.delete(xpath);
        rowCount = 1;
        String[][] var24 = pinakas;
        int var23 = pinakas.length;

        for(int var45 = 0; var45 < var23; ++var45) {
            rowStrings = var24[var45];
            Row row = sheet.createRow(rowCount);
            int columnCount = 0;
            String[] var30 = rowStrings;
            int var29 = rowStrings.length;

            for(int var28 = 0; var28 < var29; ++var28) {
                String columnString = var30[var28];
                Cell cell3 = row.createCell(columnCount);
                if (columnString instanceof String) {
                    long number;
                    if (columnString.startsWith("-") && columnString.matches("-?\\d+")) {
                        number = Long.valueOf(columnString);
                        CellStyle style2 = workbook.createCellStyle();
                        HSSFFont font2 = workbook.createFont();
                        font2.setColor(IndexedColors.RED.getIndex());
                        style2.setFont(font2);
                        cell3.setCellType(CellType.forInt(0));
                        cell3.setCellValue((double)number);
                        cell3.setCellStyle(style2);
                    } else if (columnString.matches("-?\\d+")) {
                        number = Long.valueOf(columnString);
                        cell3.setCellType(CellType.forInt(0));
                        cell3.setCellValue((double)number);
                    } else {
                        cell3.setCellValue(columnString);
                    }
                }

                ++columnCount;
            }

            ++rowCount;
        }

        Throwable var44 = null;
        cell = null;

        try {
            try (FileOutputStream outputStream = new FileOutputStream(destination + "/" + "Compare_Files-" + name + "/" + "Compare_Files-" + name + "DadE" + ".xls")) {
                workbook.write(outputStream);
            }

        } catch (Throwable var41) {
            var44 = var41;

            throw var44;
        }
    }

    public void exportToExcelDadT(String destination, String name) throws Throwable {
        String[] headerStrings = new String[]{"QualifierDR", "FilenameDR", "FcycDR", "DadEDR", "DadTDR", "SizeDR", "F/LDR", "Last Ref DateDR", "Last Ref TimeDR", "QualifierPRD", "FilenamePRD", "FcycPRD", "DadEPRD", "DadTPRD", "SizePRD", "F/LPRD", "Last Ref DatePRD", "Last Ref TimeDR", "Result"};
        String[] column = null;
        LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(new File(destination + "/" + "Compare_Files-" + name + "/" + "Compare_Files-" + name + "DadT" + ".txt")));
        lineNumberReader.skip(Long.MAX_VALUE);
        int resultoflines = lineNumberReader.getLineNumber();
        lineNumberReader.close();
        String[][] pinakas = new String[resultoflines][19];
        FileInputStream file = new FileInputStream(destination + "/" + "Compare_Files-" + name + "/" + "Compare_Files-" + name + "DadT" + ".txt");
        Scanner inScanner = new Scanner(file);

        for(int lineCount = 0; inScanner.hasNextLine(); ++lineCount) {
            column = inScanner.nextLine().split("\\s+");

            for(int i = 0; i < column.length; ++i) {
                pinakas[lineCount][i] = column[i];
            }
        }

        inScanner.close();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("DadT");
        CellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        style.setFont(font);
        int firstrow = 0;
        Row row1 = sheet.createRow(firstrow);
        int columnCount1 = 0;
        String[] rowStrings = headerStrings;
        int rowCount = headerStrings.length;

        String path;
        Cell cell;
        for(int var19 = 0; var19 < rowCount; ++var19) {
            path = rowStrings[var19];
            cell = row1.createCell(columnCount1);
            sheet.autoSizeColumn(path.length());
            cell.setCellValue(path);
            cell.setCellStyle(style);
            ++columnCount1;
        }

        path = destination + "/" + "Compare_Files-" + name + "/" + "Compare_Files-" + name + "DadT" + ".txt";
        Path xpath = Paths.get(path);
        Files.delete(xpath);
        rowCount = 1;
        String[][] var24 = pinakas;
        int var23 = pinakas.length;

        for(int var45 = 0; var45 < var23; ++var45) {
            rowStrings = var24[var45];
            Row row = sheet.createRow(rowCount);
            int columnCount = 0;
            String[] var30 = rowStrings;
            int var29 = rowStrings.length;

            for(int var28 = 0; var28 < var29; ++var28) {
                String columnString = var30[var28];
                Cell cell1 = row.createCell(columnCount);
                if (columnString != null) {
                    long number;
                    if (columnString.startsWith("-") && columnString.matches("-?\\d+")) {
                        number = Long.valueOf(columnString);
                        CellStyle style2 = workbook.createCellStyle();
                        HSSFFont font2 = workbook.createFont();
                        font2.setColor(IndexedColors.RED.getIndex());
                        style2.setFont(font2);
                        cell1.setCellType(CellType.forInt(0));
                        cell1.setCellValue((double)number);
                        cell1.setCellStyle(style2);
                    } else if (columnString.matches("-?\\d+")) {
                        number = Long.valueOf(columnString);
                        cell1.setCellType(CellType.forInt(0));
                        cell1.setCellValue((double)number);
                    } else {
                        cell1.setCellValue(columnString);
                    }
                }

                ++columnCount;
            }

            ++rowCount;
        }

        Throwable var44 = null;

        try {

            try (FileOutputStream outputStream = new FileOutputStream(destination + "/" + "Compare_Files-" + name + "/" + "Compare_Files-" + name + "DadT" + ".xls")) {
                workbook.write(outputStream);
            }

        } catch (Throwable var41) {
            if (var44 == null) {
                var44 = var41;
            } else if (var44 != var41) {
                var44.addSuppressed(var41);
            }

            throw var44;
        }
    }

    public void exportToExcelSize(String destination, String name) throws Throwable {
        String[] headerStrings = new String[]{"QualifierDR", "FilenameDR", "FcycDR", "DadEDR", "DadTDR", "SizeDR", "F/LDR", "Last Ref DateDR", "Last Ref TimeDR", "QualifierPRD", "FilenamePRD", "FcycPRD", "DadEPRD", "DadTPRD", "SizePRD", "F/LPRD", "Last Ref DatePRD", "Last Ref TimeDR", "Result"};
        String[] column = null;
        LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(new File(destination + "/" + "Compare_Files-" + name + "/" + "Compare_Files-" + name + "Size" + ".txt")));
        lineNumberReader.skip(Long.MAX_VALUE);
        int resultoflines = lineNumberReader.getLineNumber();
        lineNumberReader.close();
        String[][] pinakas = new String[resultoflines][19];
        FileInputStream file = new FileInputStream(destination + "/" + "Compare_Files-" + name + "/" + "Compare_Files-" + name + "Size" + ".txt");
        Scanner inScanner = new Scanner(file);

        try {
            for(int lineCount = 0; inScanner.hasNextLine(); ++lineCount) {
                column = inScanner.nextLine().split("\\s+");

                for(int i = 0; i < column.length; ++i) {
                    pinakas[lineCount][i] = column[i];
                }
            }

            inScanner.close();
        } catch (ArrayStoreException var43) {
        }

        inScanner.close();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("FileSize");
        CellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        style.setFont(font);
        int firstrow = 0;
        Row row1 = sheet.createRow(firstrow);
        int columnCount1 = 0;
        String[] rowStrings = headerStrings;
        int rowCount = headerStrings.length;

        String path;
        Cell cell;
        for(int var18 = 0; var18 < rowCount; ++var18) {
            path = rowStrings[var18];
            cell = row1.createCell(columnCount1);
            sheet.autoSizeColumn(path.length());
            cell.setCellValue(path);
            cell.setCellStyle(style);
            ++columnCount1;
        }

        path = destination + "/" + "Compare_Files-" + name + "/" + "Compare_Files-" + name + "Size" + ".txt";
        Path xpath = Paths.get(path);
        Files.delete(xpath);
        rowCount = 1;
        String[][] var23 = pinakas;
        int var22 = pinakas.length;

        for(int var48 = 0; var48 < var22; ++var48) {
            rowStrings = var23[var48];
            Row row = sheet.createRow(rowCount);
            int columnCount = 0;
            String[] var29 = rowStrings;
            int var28 = rowStrings.length;

            for(int var27 = 0; var27 < var28; ++var27) {
                String columnString = var29[var27];
                Cell cell2 = row.createCell(columnCount);
                if (columnString instanceof String) {
                    long number;
                    if (columnString.startsWith("-") && columnString.matches("-?\\d+")) {
                        number = Long.valueOf(columnString);
                        CellStyle style2 = workbook.createCellStyle();
                        HSSFFont font2 = workbook.createFont();
                        font2.setColor(IndexedColors.RED.getIndex());
                        style2.setFont(font2);
                        cell2.setCellType(CellType.forInt(0));
                        cell2.setCellValue((double)number);
                        cell2.setCellStyle(style2);
                    } else if (columnString.matches("-?\\d+")) {
                        number = Long.valueOf(columnString);
                        cell2.setCellType(CellType.forInt(0));
                        cell2.setCellValue((double)number);
                    } else {
                        cell2.setCellValue(columnString);
                    }
                }

                ++columnCount;
            }

            ++rowCount;
        }

        Throwable var47 = null;
        cell = null;

        try {
            FileOutputStream outputStream = new FileOutputStream(destination + "/" + "Compare_Files-" + name + "/" + "Compare_Files-" + name + "Size" + ".xls");

            try {
                workbook.write(outputStream);
            } finally {
                outputStream.close();

            }

        } catch (Throwable var42) {
            if (var47 == null) {
                var47 = var42;
            } else if (var47 != var42) {
                var47.addSuppressed(var42);
            }

            throw var47;
        }
    }

    public boolean savetofile(String onomaDR, String onomaPRD, String name, String destination) throws IOException {
        ArrayList<String> lista1 = new ArrayList();
        ArrayList<String> lista2 = new ArrayList();
        ArrayList<String> lista3 = new ArrayList();
        ArrayList<String> lista4 = new ArrayList();
        ArrayList<String> lista5 = new ArrayList();
        ArrayList<String> lista6 = new ArrayList();
        ArrayList<String> lista7 = new ArrayList();
        ArrayList<String> lista8 = new ArrayList();
        ArrayList<String> lista9 = new ArrayList();
        ArrayList<String> lista11 = new ArrayList();
        ArrayList<String> lista12 = new ArrayList();
        ArrayList<String> lista13 = new ArrayList();
        ArrayList<String> lista14 = new ArrayList();
        ArrayList<String> lista15 = new ArrayList();
        ArrayList<String> lista16 = new ArrayList();
        ArrayList<String> lista17 = new ArrayList();
        ArrayList<String> lista18 = new ArrayList();
        ArrayList<String> lista19 = new ArrayList();
        int count = 0;
        int count1 = 0;
        int countgrammata = 0;
        int countgrammataPRD = 0;
        int vrikaxaraktira = 0;
        String line;
        String line1;
        String[] onomata = null;
        String[] onomata2;
        destination = destination + "/" + "Compare_Files-" + name;
        (new File(destination.trim())).mkdir();

        BufferedReader brDR = getBufferedReader(onomaDR);
        BufferedReader brPrd = getBufferedReader(onomaPRD);

        while(true) {
            while((line = brDR.readLine()) != null) {
                if (line.contains("Qualifier") && !line.contains("Generated:") && !line.contains("------------")) {
                    brDR.readLine();
                    ++count;
                } else if (line.contains("Generated:")) {
                    count = 0;
                } else if (count != 0 && !line.contains("------------") && !line.contains("Files:")) {
                    onomata = line.split("\\s+");

                    try {
                        lista1.add(onomata[2]);
                        if (onomata[3].equals("LOAD")) {
                            if (onomata[4].matches("^[+-]?\\d+$")) {
                                lista2.add(onomata[3]);
                            } else {
                                lista2.add("LOAD " + onomata[4]);
                                ++vrikaxaraktira;
                            }
                        } else {
                            lista2.add(onomata[3]);
                        }

                        if (vrikaxaraktira != 0) {
                            lista3.add(onomata[5]);
                            lista4.add(onomata[6]);
                            lista5.add(onomata[7]);
                            lista6.add(onomata[8]);
                            lista7.add(onomata[9]);
                            if (onomata[10].matches("^[+-]?\\d+$")) {
                                lista8.add(onomata[10]);
                            } else {
                                lista8.add(onomata[11]);
                                ++countgrammata;
                            }

                            if (countgrammata != 0) {
                                lista9.add(onomata[12]);
                                countgrammata = 0;
                                vrikaxaraktira = 0;
                            } else {
                                lista9.add(onomata[11]);
                                vrikaxaraktira = 0;
                            }
                        } else {
                            lista3.add(onomata[4]);
                            lista4.add(onomata[5]);
                            lista5.add(onomata[6]);
                            lista6.add(onomata[7]);
                            lista7.add(onomata[8]);
                            if (onomata[9].matches("^[+-]?\\d+$")) {
                                lista8.add(onomata[9]);
                            } else {
                                lista8.add(onomata[10]);
                                ++countgrammata;
                            }

                            if (countgrammata != 0) {
                                lista9.add(onomata[11]);
                                countgrammata = 0;
                            } else {
                                lista9.add(onomata[10]);
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                    }
                }
            }

            brDR.close();

            while(true) {
                while((line1 = brPrd.readLine()) != null) {
//                    System.out.println("The line is: "+ line1);
                    if (line1.contains("Qualifier") && !line1.contains("Generated:") && !line1.contains("------------")) {
                        brPrd.readLine();
                        ++count1;
                    } else if (line1.contains("Generated:")) {
                        count1 = 0;
                    } else if (count1 != 0 && !line1.contains("------------") && !line1.contains("Files:")) {
                        onomata2 = line1.split("\\s+");

                        try {
                            lista11.add(onomata2[2]);
                            if (onomata2[3].equals("LOAD")) {
                                if (onomata2[4].matches("^[+-]?\\d+$")) {
                                    lista12.add(onomata2[3]);
                                } else {
                                    lista12.add("LOAD " + onomata2[4]);
                                    ++vrikaxaraktira;
                                }
                            } else {
                                lista12.add(onomata2[3]);
                            }

                            if (vrikaxaraktira != 0) {
                                lista13.add(onomata2[5]);
                                lista14.add(onomata2[6]);
                                lista15.add(onomata2[7]);
                                lista16.add(onomata2[8]);
                                lista17.add(onomata2[9]);
                                if (onomata2[10].matches("^[+-]?\\d+$")) {
                                    lista18.add(onomata2[10]);
                                } else {
                                    lista18.add(onomata2[11]);
                                    ++countgrammata;
                                }

                                if (countgrammata != 0) {
                                    lista19.add(onomata2[12]);
                                    countgrammata = 0;
                                    vrikaxaraktira = 0;
                                } else {
                                    lista9.add(onomata[11]);
                                    vrikaxaraktira = 0;
                                }
                            } else {
                                lista13.add(onomata2[4]);
                                lista14.add(onomata2[5]);
                                lista15.add(onomata2[6]);
                                lista16.add(onomata2[7]);
                                lista17.add(onomata2[8]);
                                if (onomata2[9].matches("^[+-]?\\d+$")) {
                                    lista18.add(onomata2[9]);
                                } else {
                                    lista18.add(onomata2[10]);
                                    ++countgrammataPRD;
                                }

                                if (countgrammataPRD != 0) {
                                    lista19.add(onomata2[11]);
                                    countgrammataPRD = 0;
                                } else {
                                    lista19.add(onomata2[10]);
                                }
                            }
                        } catch (ArrayIndexOutOfBoundsException ignored) {
                        }
                    }
                }

                brPrd.close();

                int akeraiosDR;
                for(akeraiosDR = 0; akeraiosDR < lista6.size(); ++akeraiosDR) {
                    if ((lista6.get(akeraiosDR)).equals("U") || (lista6.get(akeraiosDR)).equals("L")) {
                        System.out.println(lista6.get(akeraiosDR) + " : " + lista1.get(akeraiosDR) + " : " + lista2.get(akeraiosDR));
                    }
                }
//
//                int akeraiosDR = 0;
                int akeraiosPRD = 0;
                int movesecondarray = 0;
                int vrikeSizegiaArxeio = 0;
                String dRString = null;
                String pRDString = null;
                String lista1String = null;
                String lista2String = null;
                String lista3String = null;
                String lista11String = null;
                String lista12String = null;
                String lista13String = null;

                label448:
                for(int i = 0; i < lista1.size(); ++i) {
                    int j = movesecondarray;

                    while(true) {
                        while(true) {
                            if (j >= lista11.size()) {
                                continue label448;
                            }

                            lista1String = lista1.get(i);
                            lista2String = lista2.get(i);
                            lista3String = lista3.get(i);
                            lista11String = lista11.get(j);
                            lista12String = lista12.get(j);
                            lista13String = lista13.get(j);
                            int IntegerDR;
                            int IntegetPRD;
                            int IntegerDRPRD;
                            File arxeioeggrafisSize;
                            FileWriter fwSize;
                            BufferedWriter outputSize;
//                            int akeraiosPRD = 0;

                            if ((lista1.get(i)).startsWith("$") && (lista11.get(j)).startsWith("$")) {
                                if ((lista1.get(i)).equals(lista11.get(j)) && (lista2.get(i)).equals(lista12.get(j)) && (lista3.get(i)).equals(lista13.get(j))) {
                                    if (!(lista4.get(i)).equals(lista14.get(j))) {
                                        IntegerDR = Integer.parseInt(lista4.get(i));
                                        IntegetPRD = Integer.parseInt(lista14.get(j));
                                        IntegerDRPRD = IntegerDR - IntegetPRD;
                                        System.out.println("Before the file: "+ lista1.get(i) + lista2.get(i) + lista3.get(i) + lista4.get(i) + IntegerDRPRD );
                                        arxeioeggrafisSize = new File(destination + "/" + "Compare_Files-" + name + "DadE.txt");
                                        fwSize = null;

                                        try {
                                            fwSize = new FileWriter(arxeioeggrafisSize, true);
                                        } catch (IOException var58) {
                                            var58.printStackTrace();
                                        }

                                        outputSize = new BufferedWriter(fwSize);

                                        try {
                                            outputSize.write(String.format("%-12s %-12s %-5s %-5s %-5s %-5s %-2s %-5s %-10s %-12s %-12s %-5s %-5s %-5s %-5s %-2s %-5s %-10s %-10s", lista1.get(i), lista2.get(i), lista3.get(i), lista4.get(i), lista5.get(i), lista6.get(i), lista7.get(i), lista8.get(i), lista9.get(i), lista11.get(j), lista12.get(j), lista13.get(j), lista14.get(j), lista15.get(j), lista16.get(j), lista17.get(j), lista18.get(j), lista19.get(j), IntegerDRPRD));
                                            System.out.println(lista1.get(i) + lista2.get(i) + lista3.get(i) + lista4.get(i) + IntegerDRPRD );
                                            outputSize.newLine();
                                            outputSize.close();
                                        } catch (IndexOutOfBoundsException | IOException var57) {
                                            var57.printStackTrace();
                                        }

                                        System.out.println("DadE : " + lista1.get(i) + " " + lista2.get(i) + " " + lista3.get(i) + " " + lista4.get(i) + " " + lista11.get(j) + " " + lista12.get(j) + " " + lista13.get(j) + " " + lista14.get(j) + " " + IntegerDRPRD);
                                    }

                                    if (!(lista5.get(i)).equals(lista15.get(j))) {
                                        IntegerDR = Integer.parseInt(lista5.get(i));
                                        IntegetPRD = Integer.parseInt(lista15.get(j));
                                        IntegerDRPRD = IntegerDR - IntegetPRD;
                                        arxeioeggrafisSize = new File(destination + "/" + "Compare_Files-" + name + "DadT.txt");
                                        fwSize = null;

                                        try {
                                            fwSize = new FileWriter(arxeioeggrafisSize, true);
                                        } catch (IOException var60) {
                                            var60.printStackTrace();
                                        }

                                        outputSize = new BufferedWriter(fwSize);

                                        try {
                                            outputSize.write(String.format("%-12s %-12s %-5s %-5s %-5s %-5s %-2s %-5s %-10s %-12s %-12s %-5s %-5s %-5s %-5s %-2s %-5s %-10s %-10s", lista1.get(i), lista2.get(i), lista3.get(i), lista4.get(i), lista5.get(i), lista6.get(i), lista7.get(i), lista8.get(i), lista9.get(i), lista11.get(j), lista12.get(j), lista13.get(j), lista14.get(j), lista15.get(j), lista16.get(j), lista17.get(j), lista18.get(j), lista19.get(j), IntegerDRPRD));
                                            outputSize.newLine();
                                            outputSize.close();
                                        } catch (IndexOutOfBoundsException | IOException var59) {
                                            var59.printStackTrace();
                                        }

                                        System.out.println("DadE : " + lista1.get(i) + " " + lista2.get(i) + " " + lista3.get(i) + " " + lista5.get(i) + " " + lista11.get(j) + " " + lista12.get(j) + " " + lista13.get(j) + " " + lista15.get(j) + " " + IntegerDRPRD);
                                    }

                                    if (lista6.get(i).equals(lista16.get(j))) {
                                        ++count;
                                        movesecondarray = j + 1;
                                    } else {
                                        IntegerDR = Integer.parseInt(lista6.get(i));
                                        IntegetPRD = Integer.parseInt(lista16.get(j));
                                        IntegerDRPRD = IntegerDR - IntegetPRD;
                                        ++vrikeSizegiaArxeio;
                                        arxeioeggrafisSize = new File(destination + "/" + "Compare_Files-" + name + "Size" + ".txt");
                                        fwSize = null;

                                        try {
                                            fwSize = new FileWriter(arxeioeggrafisSize, true);
                                        } catch (IOException var74) {
                                            var74.printStackTrace();
                                        }

                                        outputSize = new BufferedWriter(fwSize);

                                        try {
                                            outputSize.write(String.format("%-12s %-12s %-5s %-5s %-5s %-5s %-2s %-5s %-10s %-12s %-12s %-5s %-5s %-5s %-5s %-2s %-5s %-10s %-10s", lista1.get(i), lista2.get(i), lista3.get(i), lista4.get(i), lista5.get(i), lista6.get(i), lista7.get(i), lista8.get(i), lista9.get(i), lista11.get(j), lista12.get(j), lista13.get(j), lista14.get(j), lista15.get(j), lista16.get(j), lista17.get(j), lista18.get(j), lista19.get(j), IntegerDRPRD));
                                            outputSize.newLine();
                                            outputSize.close();
                                        } catch (IndexOutOfBoundsException | IOException var73) {
                                            var73.printStackTrace();
                                        }

                                        movesecondarray = j + 1;
                                    }
                                    continue label448;
                                }

                                if (!lista1.get(i).equals(lista11.get(j)) || !((String)lista2.get(i)).equals(lista12.get(j))) {
                                    akeraiosDR = Integer.parseInt(lista3.get(i));
                                    akeraiosPRD = Integer.parseInt(lista13.get(j));
                                    if (akeraiosDR > akeraiosPRD) {
                                        continue label448;
                                    }

                                    ++j;
                                    continue;
                                }

                                if (Character.isLetter(lista2.get(i).charAt(0)) && Character.isLetter(lista12.get(j).charAt(0)) && lista12.get(j).equals("PFM")) {
                                    continue label448;
                                }
                            }

                            if (lista1.get(i).startsWith("$") && Character.isDigit(lista11.get(j).charAt(0))) {
                                continue label448;
                            }

                            if (Character.isDigit(lista1.get(i).charAt(0)) && lista11.get(j).startsWith("$")) {
                                ++j;
                            } else {
                                if (Character.isDigit(lista1.get(i).charAt(0)) && Character.isLetter(lista11.get(j).charAt(0))) {
                                    continue label448;
                                }

                                if (Character.isDigit(lista1.get(i).charAt(0)) && Character.isDigit(lista11.get(j).charAt(0))) {
                                    if (lista1.get(i).equals(lista11.get(j)) && lista2.get(i).equals(lista12.get(j))) {
                                        if (lista3.get(i).equals(lista13.get(j))) {
                                            if (!lista4.get(i).equals(lista14.get(j))) {
                                                IntegerDR = Integer.parseInt(lista4.get(i));
                                                IntegetPRD = Integer.parseInt(lista14.get(j));
                                                IntegerDRPRD = IntegerDR - IntegetPRD;
                                                arxeioeggrafisSize = new File(destination + "/" + "Compare_Files-" + name + "DadE" + ".txt");
                                                fwSize = null;

                                                try {
                                                    fwSize = new FileWriter(arxeioeggrafisSize, true);
                                                } catch (IOException var72) {
                                                    var72.printStackTrace();
                                                }

                                                outputSize = new BufferedWriter(fwSize);

                                                try {
                                                    outputSize.write(String.format("%-12s %-12s %-5s %-5s %-5s %-5s %-2s %-5s %-10s %-12s %-12s %-5s %-5s %-5s %-5s %-2s %-5s %-10s %-10s", lista1.get(i), lista2.get(i), lista3.get(i), lista4.get(i), lista5.get(i), lista6.get(i), lista7.get(i), lista8.get(i), lista9.get(i), lista11.get(j), lista12.get(j), lista13.get(j), lista14.get(j), lista15.get(j), lista16.get(j), lista17.get(j), lista18.get(j), lista19.get(j), IntegerDRPRD));
                                                    outputSize.newLine();
                                                    outputSize.close();
                                                } catch (IndexOutOfBoundsException | IOException var71) {
                                                    var71.printStackTrace();
                                                }

                                                System.out.println("DadE : " + lista1.get(i) + " " + lista2.get(i) + " " + lista3.get(i) + " " + lista4.get(i) + " " + lista11.get(j) + " " + lista12.get(j) + " " + lista13.get(j) + " " + lista14.get(j) + " " + IntegerDRPRD);
                                            }

                                            if (!lista5.get(i).equals(lista15.get(j))) {
                                                IntegerDR = Integer.parseInt(lista5.get(i));
                                                IntegetPRD = Integer.parseInt(lista15.get(j));
                                                IntegerDRPRD = IntegerDR - IntegetPRD;
                                                arxeioeggrafisSize = new File(destination + "/" + "Compare_Files-" + name + "DadT" + ".txt");
                                                fwSize = null;

                                                try {
                                                    fwSize = new FileWriter(arxeioeggrafisSize, true);
                                                } catch (IOException var70) {
                                                    var70.printStackTrace();
                                                }

                                                outputSize = new BufferedWriter(fwSize);

                                                try {
                                                    outputSize.write(String.format("%-12s %-12s %-5s %-5s %-5s %-5s %-2s %-5s %-10s %-12s %-12s %-5s %-5s %-5s %-5s %-2s %-5s %-10s %-10s", lista1.get(i), lista2.get(i), lista3.get(i), lista4.get(i), lista5.get(i), lista6.get(i), lista7.get(i), lista8.get(i), lista9.get(i), lista11.get(j), lista12.get(j), lista13.get(j), lista14.get(j), lista15.get(j), lista16.get(j), lista17.get(j), lista18.get(j), lista19.get(j), IntegerDRPRD));
                                                    outputSize.newLine();
                                                    outputSize.close();
                                                } catch (IndexOutOfBoundsException | IOException var69) {
                                                    var69.printStackTrace();
                                                }

                                                System.out.println("DadE : " + (String)lista1.get(i) + " " + (String)lista2.get(i) + " " + (String)lista3.get(i) + " " + (String)lista5.get(i) + " " + (String)lista11.get(j) + " " + (String)lista12.get(j) + " " + (String)lista13.get(j) + " " + (String)lista15.get(j) + " " + IntegerDRPRD);
                                            }

                                            if (lista6.get(i).equals(lista16.get(j))) {
                                                movesecondarray = j + 1;
                                            } else {
                                                IntegerDR = Integer.parseInt(lista6.get(i));
                                                IntegetPRD = Integer.parseInt(lista16.get(j));
                                                IntegerDRPRD = IntegerDR - IntegetPRD;
                                                System.out.println("Size : " + lista1.get(i) + " " + (String)lista2.get(i) + " " + (String)lista3.get(i) + " " + (String)lista11.get(j) + " " + (String)lista12.get(j) + " " + (String)lista13.get(j) + " " + IntegerDRPRD);
                                                arxeioeggrafisSize = new File(destination + "/" + "Compare_Files-" + name + "Size" + ".txt");
                                                fwSize = null;

                                                try {
                                                    fwSize = new FileWriter(arxeioeggrafisSize, true);
                                                } catch (IOException var68) {
                                                    var68.printStackTrace();
                                                }

                                                outputSize = new BufferedWriter(fwSize);

                                                try {
                                                    outputSize.write(String.format("%-12s %-12s %-5s %-5s %-5s %-5s %-2s %-5s %-10s %-12s %-12s %-5s %-5s %-5s %-5s %-2s %-5s %-10s %-10s", lista1.get(i), lista2.get(i), lista3.get(i), lista4.get(i), lista5.get(i), lista6.get(i), lista7.get(i), lista8.get(i), lista9.get(i), lista11.get(j), lista12.get(j), lista13.get(j), lista14.get(j), lista15.get(j), lista16.get(j), lista17.get(j), lista18.get(j), lista19.get(j), IntegerDRPRD));
                                                    outputSize.newLine();
                                                    outputSize.close();
                                                } catch (IndexOutOfBoundsException | IOException var67) {
                                                    var67.printStackTrace();
                                                }

                                                movesecondarray = j + 1;
                                            }
                                            continue label448;
                                        }
                                    } else if (!lista1.get(i).equals(lista11.get(j))) {
                                        IntegerDR = lista1.get(i).charAt(0);
                                        IntegetPRD = lista11.get(j).charAt(0);
                                        akeraiosDR = Integer.parseInt(String.valueOf((char)IntegerDR));
                                        akeraiosPRD = Integer.parseInt(String.valueOf((char)IntegetPRD));
                                        if (akeraiosDR < akeraiosPRD) {
                                            continue label448;
                                        }

                                        if (akeraiosDR > akeraiosPRD) {
                                            movesecondarray = j + 1;
                                            continue label448;
                                        }
                                    }
                                }

                                if (Character.isLetter(lista1.get(i).charAt(0)) && Character.isLetter(lista11.get(j).charAt(0))) {
                                    String tolathos;
                                    if (lista1.get(i).equals(lista11.get(j)) && lista2.get(i).equals(lista12.get(j))) {
                                        if (lista3.get(i).equals(lista13.get(j))) {
                                            if (!lista4.get(i).equals(lista14.get(j))) {
                                                IntegerDR = Integer.parseInt(lista4.get(i));
                                                IntegetPRD = Integer.parseInt(lista14.get(j));
                                                IntegerDRPRD = IntegerDR - IntegetPRD;
                                                arxeioeggrafisSize = new File(destination + "/" + "Compare_Files-" + name + "DadE" + ".txt");
                                                fwSize = null;

                                                try {
                                                    fwSize = new FileWriter(arxeioeggrafisSize, true);
                                                } catch (IOException var66) {
                                                    var66.printStackTrace();
                                                }

                                                outputSize = new BufferedWriter(fwSize);

                                                try {
                                                    outputSize.write(String.format("%-12s %-12s %-5s %-5s %-5s %-5s %-2s %-5s %-10s %-12s %-12s %-5s %-5s %-5s %-5s %-2s %-5s %-10s %-10s", lista1.get(i), lista2.get(i), lista3.get(i), lista4.get(i), lista5.get(i), lista6.get(i), lista7.get(i), lista8.get(i), lista9.get(i), lista11.get(j), lista12.get(j), lista13.get(j), lista14.get(j), lista15.get(j), lista16.get(j), lista17.get(j), lista18.get(j), lista19.get(j), IntegerDRPRD));
                                                    outputSize.newLine();
                                                    outputSize.close();
                                                } catch (IndexOutOfBoundsException | IOException var65) {
                                                    var65.printStackTrace();
                                                }

                                                System.out.println("DadE : " + lista1.get(i) + " " + lista2.get(i) + " " + lista3.get(i) + " " + lista4.get(i) + " " + lista11.get(j) + " " + lista12.get(j) + " " + lista13.get(j) + " " + lista14.get(j) + " " + IntegerDRPRD);
                                            }

                                            if (!lista5.get(i).equals(lista15.get(j))) {
                                                IntegerDR = Integer.parseInt(lista5.get(i));
                                                IntegetPRD = Integer.parseInt(lista15.get(j));
                                                IntegerDRPRD = IntegerDR - IntegetPRD;
                                                arxeioeggrafisSize = new File(destination + "/" + "Compare_Files-" + name + "DadT" + ".txt");
                                                fwSize = null;

                                                try {
                                                    fwSize = new FileWriter(arxeioeggrafisSize, true);
                                                } catch (IOException var64) {
                                                    var64.printStackTrace();
                                                }

                                                outputSize = new BufferedWriter(fwSize);

                                                try {
                                                    outputSize.write(String.format("%-12s %-12s %-5s %-5s %-5s %-5s %-2s %-5s %-10s %-12s %-12s %-5s %-5s %-5s %-5s %-2s %-5s %-10s %-10s", lista1.get(i), lista2.get(i), lista3.get(i), lista4.get(i), lista5.get(i), lista6.get(i), lista7.get(i), lista8.get(i), lista9.get(i), lista11.get(j), lista12.get(j), lista13.get(j), lista14.get(j), lista15.get(j), lista16.get(j), lista17.get(j), lista18.get(j), lista19.get(j), IntegerDRPRD));
                                                    outputSize.newLine();
                                                    outputSize.close();
                                                } catch (IndexOutOfBoundsException | IOException var63) {
                                                    var63.printStackTrace();
                                                }

                                                System.out.println("DadT : " + lista1.get(i) + " " + lista2.get(i) + " " + lista3.get(i) + " " + lista5.get(i) + " " + lista11.get(j) + " " + lista12.get(j) + " " + lista13.get(j) + " " + lista15.get(j) + " " + IntegerDRPRD);
                                            }

                                            if (lista6.get(i).equals(lista16.get(j))) {
                                                ++count;
                                                movesecondarray = j + 1;
                                            } else {
                                                IntegerDR = Integer.parseInt(lista6.get(i).trim());
                                                if (Character.isLetter(lista6.get(i).charAt(0))) {
                                                    System.out.println(lista6.get(i) + " : " + lista1.get(i) + " : " + (String)lista2.get(i) + " : " + lista3.get(i) + ":" + lista4.get(i) + " : " + lista5.get(i));
                                                }

                                                if (Character.isLetter(lista16.get(j).charAt(0))) {
                                                    tolathos = lista16.get(j);
                                                    System.out.println(lista16.get(j) + " : " + lista11.get(j) + " : " + (String)lista12.get(j) + " : " + lista13.get(j) + ":" + lista14.get(j) + " : " + lista15.get(j));
                                                }

                                                IntegetPRD = Integer.parseInt(lista16.get(j).trim());
                                                System.out.println("The IntegerPrd value :" + IntegetPRD + " : " + IntegerDR);
                                                IntegerDRPRD = IntegerDR - IntegetPRD;
                                                System.out.println("Size :" + lista1.get(i) + " " + (String)lista2.get(i) + " " + (String)lista3.get(i) + " " + (String)lista11.get(j) + " " + (String)lista12.get(j) + " " + (String)lista13.get(j) + " " + IntegerDRPRD);
                                                arxeioeggrafisSize = new File(destination + "/" + "Compare_Files-" + name + "Size" + ".txt");
                                                fwSize = null;

                                                try {
                                                    fwSize = new FileWriter(arxeioeggrafisSize, true);
                                                } catch (IOException var62) {
                                                    var62.printStackTrace();
                                                }

                                                outputSize = new BufferedWriter(fwSize);

                                                try {
                                                    outputSize.write(String.format("%-12s %-12s %-5s %-5s %-5s %-5s %-2s %-5s %-10s %-12s %-12s %-5s %-5s %-5s %-5s %-2s %-5s %-10s %-10s", lista1.get(i), lista2.get(i), lista3.get(i), lista4.get(i), lista5.get(i), lista6.get(i), lista7.get(i), lista8.get(i), lista9.get(i), lista11.get(j), lista12.get(j), lista13.get(j), lista14.get(j), lista15.get(j), lista16.get(j), lista17.get(j), lista18.get(j), lista19.get(j), IntegerDRPRD));
                                                    outputSize.newLine();
                                                    outputSize.close();
                                                } catch (IndexOutOfBoundsException | IOException var61) {
                                                    var61.printStackTrace();
                                                }

                                                movesecondarray = j + 1;
                                            }
                                            continue label448;
                                        }

                                        if (!lista3.get(i).equals(lista13.get(j))) {
                                            akeraiosDR = Integer.parseInt(lista3.get(i));
                                            akeraiosPRD = Integer.parseInt(lista13.get(j));
                                            if (akeraiosDR < akeraiosPRD) {
                                                continue label448;
                                            }

                                            ++j;
                                            continue;
                                        }
                                    }

                                    if (!lista1.get(i).equals(lista11.get(j))) {
                                        if (lista1.get(i).compareTo(lista11.get(j)) < 0) {
                                            continue label448;
                                        }

                                        if (lista1.get(i).compareTo(lista11.get(j)) > 0) {
                                            ++j;
                                            continue;
                                        }
                                    }

                                    if (lista1.get(i).equals(lista11.get(j)) && !lista2.get(i).equals(lista12.get(j))) {
                                        String DR = lista2.get(i).substring(0, 1);
                                        tolathos = lista12.get(j).substring(0, 1);
                                        if (lista2.get(i).compareTo(lista12.get(j)) < 0) {
                                            movesecondarray = j;
                                            continue label448;
                                        }

                                        ++j;
                                    }
                                }
                            }
                        }
                    }
                }
                return isEmpty(Path.of(destination));
            }
        }
    }

    private BufferedReader getBufferedReader(String filePath) throws FileNotFoundException {
        File file = new File(filePath.trim());
        FileReader fileReader = new FileReader(file);
        return new BufferedReader(fileReader);
    }

    public boolean isEmpty(Path path) {
        return path.toFile().listFiles().length == 0;
    }

    public static void main(String[] args) {
        new FormGUI();
    }
}