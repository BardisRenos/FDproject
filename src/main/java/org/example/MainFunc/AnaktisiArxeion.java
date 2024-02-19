package org.example.MainFunc;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
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

    public static void main(String[] args) throws IOException {
        new AnaktisiArxeion();
    }

    AnaktisiArxeion() {
        this.ExitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });
        this.fileChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(0);
                fileChooser.setAcceptAllFileFilterUsed(true);
                fileChooser.setPreferredSize(new Dimension(700, 700));
                int rval = fileChooser.showOpenDialog((Component)null);
                if (rval == 0) {
                    AnaktisiArxeion.this.EpilogiArxeiou.setText(fileChooser.getSelectedFile().toString());
                }

            }
        });

        this.fileExpBackUport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(1);
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.setPreferredSize(new Dimension(700, 700));
                int rval = fileChooser.showOpenDialog((Component)null);
                if (rval == 0) {
                    AnaktisiArxeion.this.EksagogiArxeiou.setText(fileChooser.getSelectedFile().toString());
                }

            }
        });

        this.Apotelesma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                AnaktisiArxeion.onomaEpilogiArxeiou = AnaktisiArxeion.this.EpilogiArxeiou.getText().toString().replace("\\", "/");
                AnaktisiArxeion.onomatouarxeiouString = AnaktisiArxeion.this.OnomasiaArxeiou.getText().toString();
                AnaktisiArxeion.onomaproorismou = AnaktisiArxeion.this.EksagogiArxeiou.getText().toString().replace("\\", "/");

                try {
                    AnaktisiArxeion.this.exportToExcel(AnaktisiArxeion.onomaproorismou, AnaktisiArxeion.onomatouarxeiouString, AnaktisiArxeion.onomaEpilogiArxeiou);
                    int apotelesma = AnaktisiArxeion.this.exportToExcel(AnaktisiArxeion.onomaproorismou, AnaktisiArxeion.onomatouarxeiouString, AnaktisiArxeion.onomaEpilogiArxeiou);
                    if (apotelesma == 1) {
                        JOptionPane.showMessageDialog((Component)null, "The extraction is complete ");
                        AnaktisiArxeion.this.EpilogiArxeiou.setText("");
                        AnaktisiArxeion.this.EksagogiArxeiou.setText("");
                        AnaktisiArxeion.this.OnomasiaArxeiou.setText("");
                    } else {
                        JOptionPane.showMessageDialog((Component)null, "The extraction failed ");
                    }
                } catch (IOException var3) {
                    var3.printStackTrace();
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }

            }
        });
        this.EpilogiArxeiou.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(1);
                    AnaktisiArxeion.this.droppedFiles = (List)evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    AnaktisiArxeion.this.listaArxeion.add(((File)AnaktisiArxeion.this.droppedFiles.get(0)).toString());
                    Iterator var3 = AnaktisiArxeion.this.droppedFiles.iterator();

                    while(var3.hasNext()) {
                        File file = (File)var3.next();
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
        this.back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                new MainGUI();
                AnaktisiArxeion.this.dispose();
            }
        });
    }

    public int exportToExcel(String destination, String name, String epilogiarxeiou) throws Throwable {
        int counter = 0;
        FileInputStream file = null;

        try {
            file = new FileInputStream(epilogiarxeiou);
        } catch (FileNotFoundException var32) {
            var32.printStackTrace();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(file));
        ArrayList<String> stili1 = new ArrayList();
        ArrayList<String> stili2 = new ArrayList();

        while((line = br.readLine()) != null) {
            if (line.contains("PACKID:")) {
                column = line.split("\\s+");
                stili1.add(column[1]);
                stili2.add(column[4]);
                ++counter;
            }
        }

        String[][] Pinakas = new String[counter][2];

        for(int i = 0; i < Pinakas.length; ++i) {
            int pinakasStili = 0;
            Pinakas[i][pinakasStili] = (String)stili1.get(i);
            String[] var10000 = Pinakas[i];
            ++pinakasStili;
            var10000[pinakasStili] = (String)stili2.get(i);
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("FileSize");
        CellStyle style = workbook.createCellStyle();

        int rowCount = 0;
        String[][] var17 = Pinakas;
        int var16 = Pinakas.length;

        HSSFRow row;
        for(int var15 = 0; var15 < var16; ++var15) {
            String[] rowStrings = var17[var15];
            row = sheet.createRow(rowCount);
            int columnCount = 0;
            String[] var23 = rowStrings;
            int var22 = rowStrings.length;

            for(int var21 = 0; var21 < var22; ++var21) {
                String columnString = var23[var21];
                Cell cell = row.createCell(columnCount);
                if (columnString.matches("-?\\d+")) {
                    int number = Integer.parseInt(columnString);
                    style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
                    sheet.autoSizeColumn(columnString.length());
                    cell.setCellType(0);
                    cell.setCellValue((double)number);
                    cell.setCellStyle(style);
                } else {
                    sheet.autoSizeColumn(columnString.length());
                    cell.setCellValue(columnString);
                }

                ++columnCount;
            }

            ++rowCount;
        }

        Row rowFinal = sheet.createRow(rowCount + 2);
        Cell cell = rowFinal.createCell(1);
        String formula = "SUM(B1:B" + rowCount + ")";
        cell.setCellType(2);
        cell.setCellFormula(formula);
        Throwable var40 = null;
//        row = null;

        try {
            FileOutputStream outputStream = new FileOutputStream(destination + "/" + name + ".xls");

            try {
                workbook.write(outputStream);
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }

            }
        } catch (Throwable var34) {
            if (var40 == null) {
                var40 = var34;
            } else if (var40 != var34) {
                var40.addSuppressed(var34);
            }

            throw var40;
        }

        br.close();
        return 1;
    }
}
