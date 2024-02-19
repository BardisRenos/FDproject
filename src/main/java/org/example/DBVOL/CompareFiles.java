package org.example.DBVOL;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CompareFiles {
    public CompareFiles() {
    }

    public List<Object> ExtractReportAll(String file) {
        String fileNname = file;
        String[] column = null;
        BufferedReader bReader = null;
        FileReader fReader = null;
        List<Object> lista = new ArrayList();
        int page = 0;
        int usedpage = 0;
        int availablepage = 0;

        try {
            fReader = new FileReader(fileNname);
            bReader = new BufferedReader(fReader);
            System.out.println();

            boolean counter = false;

            while(true) {
                while(true) {
                    String lines;
                    do {
                        if ((lines = bReader.readLine()) == null) {
                            return lista;
                        }
                    } while(!lines.contains("storagearea"));

                    column = lines.split("storagearea");
                    String tableName = column[1].replace(";", "").trim();

                    while(!lines.contains("Total Number of Pages.........") || !lines.contains("Number of Used Pages..........") || !lines.contains("Number of Available Pages.....") || !lines.contains("Percent of File Full..........")) {
                        lines = bReader.readLine();
                        String[] FileFull;
                        if (lines.contains("Total Number of Pages.........")) {
                            FileFull = lines.split("Total Number of Pages.........");
                            page = Integer.parseInt(FileFull[1].trim());
                        }

                        if (lines.contains("Number of Used Pages..........")) {
                            FileFull = lines.split("Number of Used Pages..........");
                            usedpage = Integer.parseInt(FileFull[1].trim());
                        }

                        if (lines.contains("Number of Available Pages.....")) {
                            FileFull = lines.split("Number of Available Pages.....");
                            availablepage = Integer.parseInt(FileFull[1].trim());
                        }

                        if (lines.contains("Percent of File Full..........")) {
                            FileFull = lines.split("Percent of File Full..........");
                            double filefull = Double.parseDouble(FileFull[1].trim());
                            Data data = new Data(tableName, page, usedpage, availablepage, filefull);
                            lista.add(data.getTableName());
                            lista.add(data.getNumOfPages());
                            lista.add(data.getUsedPages());
                            lista.add(data.getAvailablePages());
                            lista.add(data.getFileFull());
                            break;
                        }
                    }
                }
            }
        } catch (Exception var17) {
            var17.printStackTrace();
            return lista;
        }
    }

    public List<Object> ExtractReportThresold(String file, Double limit) {
        String fileNname = file;
        String[] column = null;
        BufferedReader bReader = null;
        FileReader fReader = null;
        List<Object> PercentageLista = new ArrayList();
        int page = 0;
        int usedpage = 0;
        int availablepage = 0;

        try {
            fReader = new FileReader(fileNname);
            bReader = new BufferedReader(fReader);
            System.out.println();

            int counter = 0;

            while(true) {
                while(true) {
                    String lines;
                    do {
                        if ((lines = bReader.readLine()) == null) {
                            return PercentageLista;
                        }
                    } while(!lines.contains("storagearea"));

                    column = lines.split("storagearea");
                    String tableName = column[1].replace(";", "").trim();

                    while(!lines.contains("Total Number of Pages.........") || !lines.contains("Number of Used Pages..........") || !lines.contains("Number of Available Pages.....") || !lines.contains("Percent of File Full..........")) {
                        lines = bReader.readLine();
                        String[] FileFull;
                        if (lines.contains("Total Number of Pages.........")) {
                            FileFull = lines.split("Total Number of Pages.........");
                            page = Integer.parseInt(FileFull[1].trim());
                        }

                        if (lines.contains("Number of Used Pages..........")) {
                            FileFull = lines.split("Number of Used Pages..........");
                            usedpage = Integer.parseInt(FileFull[1].trim());
                        }

                        if (lines.contains("Number of Available Pages.....")) {
                            FileFull = lines.split("Number of Available Pages.....");
                            availablepage = Integer.parseInt(FileFull[1].trim());
                        }

                        if (lines.contains("Percent of File Full..........")) {
                            FileFull = lines.split("Percent of File Full..........");
                            double filefull = Double.parseDouble(FileFull[1].trim());
                            Data data = new Data(tableName, page, usedpage, availablepage, filefull);
                            if (data.getFileFull() >= limit) {
                                PercentageLista.add(data.getTableName());
                                PercentageLista.add(data.getNumOfPages());
                                PercentageLista.add(data.getUsedPages());
                                PercentageLista.add(data.getAvailablePages());
                                PercentageLista.add(data.getFileFull());
                            }
                            break;
                        }
                    }
                }
            }
        } catch (Exception var18) {
            var18.printStackTrace();
            return PercentageLista;
        }
    }

    public void AllExtract(List<Object> pinakasAllon, String destination, String filenameExport) throws Throwable {
        String[] headerStrings = new String[]{"TableName", "# of Pages", "Used Pages", "Available Pages", "% of Capacity"};
        int size = pinakasAllon.size() / 5;
        Object[][] pinakas = new Object[size][5];
        int i = 0;
        int j = 0;
        Iterator var10 = pinakasAllon.iterator();

        while(var10.hasNext()) {
            Object value = var10.next();
            pinakas[i][j] = value;
            ++j;
            if (j % 5 == 0 && j != 0) {
                j = 0;
                ++i;
            }
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        CellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.getBoldweight();
        style.setFont(font);
        int firstrow = 0;
        Row row1 = sheet.createRow(firstrow);
        int columnCount1 = 0;
        String[] var19 = headerStrings;
        int var18 = headerStrings.length;

        for(int var17 = 0; var17 < var18; ++var17) {
            String wordheader = var19[var17];
            Cell cell = row1.createCell(columnCount1);
            sheet.autoSizeColumn(columnCount1);
            cell.setCellValue(wordheader);
            cell.setCellStyle(style);
            ++columnCount1;
        }

        int rowCount = 1;
        Object[][] var42 = pinakas;
        int var40 = pinakas.length;

        for(var18 = 0; var18 < var40; ++var18) {
            Object[] rowStrings = var42[var18];
            Row row = sheet.createRow(rowCount);
            int columnCount = 0;
            Object[] var26 = rowStrings;
            int var25 = rowStrings.length;

            for(int var24 = 0; var24 < var25; ++var24) {
                Object columnString = var26[var24];
                Cell cell = row.createCell(columnCount);
                cell.setCellValue(columnString.toString());
                ++columnCount;
            }

            ++rowCount;
        }

        Throwable var38 = null;
        Object var39 = null;

        try {
            FileOutputStream outputStream = new FileOutputStream(destination + "\\" + filenameExport + "All.xls");

            try {
                workbook.write(outputStream);
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }

            }

        } catch (Throwable var33) {
            if (var38 == null) {
                var38 = var33;
            } else if (var38 != var33) {
                var38.addSuppressed(var33);
            }

            throw var38;
        }
    }

    public void AllExtractThreshold(List<Object> pinakasAllon, String destination, String filenameExport) throws FileNotFoundException, IOException {
        String[] headerStrings = new String[]{"TableName", "# of Pages", "Used Pages", "Available Pages", "% of Capacity"};
        int sizePercentage = pinakasAllon.size() / 5;
        Object[][] PercentagePinakas = new Object[sizePercentage][5];
        System.out.println("TO MEGETHOS EINAI:" + sizePercentage);
        int iPercentage = 0;
        int jPercentage = 0;
        Iterator var10 = pinakasAllon.iterator();

        while(var10.hasNext()) {
            Object valuePercentage = var10.next();
            PercentagePinakas[iPercentage][jPercentage] = valuePercentage;
            ++jPercentage;
            if (jPercentage % 5 == 0 && jPercentage != 0) {
                jPercentage = 0;
                ++iPercentage;
            }
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        CellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.getBoldweight();
        style.setFont(font);
        int firstrow = 0;
        Row row1 = sheet.createRow(firstrow);
        int columnCount1 = 0;
        String[] var19 = headerStrings;
        int var18 = headerStrings.length;

        for(int var17 = 0; var17 < var18; ++var17) {
            String wordheader = var19[var17];
            Cell cell = row1.createCell(columnCount1);
            sheet.autoSizeColumn(columnCount1);
            cell.setCellValue(wordheader);
            cell.setCellStyle(style);
            ++columnCount1;
        }

        int rowCount = 1;
        Object[][] var45 = PercentagePinakas;
        int var43 = PercentagePinakas.length;

        for(var18 = 0; var18 < var43; ++var18) {
            Object[] rowStrings = var45[var18];
            Row row = sheet.createRow(rowCount);
            int columnCount = 0;
            Object[] var26 = rowStrings;
            int var25 = rowStrings.length;

            for(int var24 = 0; var24 < var25; ++var24) {
                Object columnString = var26[var24];
                Cell cell = row.createCell(columnCount);
                cell.setCellValue(columnString.toString());
                ++columnCount;
            }

            ++rowCount;
        }

        try {
            Throwable var41 = null;
            Object var42 = null;

            try {
                FileOutputStream outputStream1 = new FileOutputStream(destination + "\\" + filenameExport + ".xls");

                try {
                    workbook.write(outputStream1);
                } finally {
                    if (outputStream1 != null) {
                        outputStream1.close();
                    }

                }
            } catch (Throwable var35) {
                if (var41 == null) {
                    var41 = var35;
                } else if (var41 != var35) {
                    var41.addSuppressed(var35);
                }

                throw var41;
            }
        } catch (Throwable var36) {
            var36.printStackTrace();
        }

    }
}
