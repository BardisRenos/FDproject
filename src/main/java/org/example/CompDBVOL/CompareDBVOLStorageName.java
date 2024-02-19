package org.example.CompDBVOL;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CompareDBVOLStorageName extends GUICompareDBVOL {
    private static final long serialVersionUID = 1L;

    public CompareDBVOLStorageName() {
    }

    public static List<List> PrintStoragename(String file1, String file2, String file3, String file4) throws IOException {
        String[] column1 = null;
        BufferedReader bReader1 = null;
        FileReader fReader1 = null;
        List<String> lista1 = new ArrayList();
        int counter = 0;
        fReader1 = new FileReader(file1);
        bReader1 = new BufferedReader(fReader1);
        String[] column2 = null;
        BufferedReader bReader2 = null;
        FileReader fReader2 = null;
        List<String> lista2 = new ArrayList();
        int counter2 = 0;
        if (file2.contains("DEV") && !file2.equals("Dev")) {
            fReader2 = new FileReader(file2);
            bReader2 = new BufferedReader(fReader2);

            String lines2;
            while((lines2 = bReader2.readLine()) != null) {
                if (lines2.contains("storagearea")) {
                    column2 = lines2.split("storagearea");
                    lista2.add(column2[1].replace(";", "").trim());
                }
            }

            bReader2.close();
        }

        String[] column3 = null;
        BufferedReader bReader3 = null;
        FileReader fReader3 = null;
        List<String> lista3 = new ArrayList();
        int counter3 = 0;
        if (file3.contains("SIT") && !file3.equals("Sit")) {
            fReader3 = new FileReader(file3);
            bReader3 = new BufferedReader(fReader3);

            String lines3;
            while((lines3 = bReader3.readLine()) != null) {
                if (lines3.contains("storagearea")) {
                    column3 = lines3.split("storagearea");
                    lista3.add(column3[1].replace(";", "").trim());
                }
            }

            bReader3.close();
        }

        String[] column4 = null;
        BufferedReader bReader4 = null;
        FileReader fReader4 = null;
        List<String> lista4 = new ArrayList();
        if (file4.contains("UAT") && !file4.equals("Uat")) {
            fReader4 = new FileReader(file4);
            bReader4 = new BufferedReader(fReader4);

            String lines4;
            while((lines4 = bReader4.readLine()) != null) {
                if (lines4.contains("storagearea")) {
                    column4 = lines4.split("storagearea");
                    lista4.add(column4[1].replace(";", "").trim());
                }
            }

            bReader4.close();
        }

        List<List> AllList = new ArrayList();

        String lines1;
        while((lines1 = bReader1.readLine()) != null) {
            if (lines1.contains("storagearea")) {
                column1 = lines1.split("storagearea");
                lista1.add(column1[1].replace(";", "").trim());
            }
        }

        bReader1.close();
        System.out.println(lista1.size() + " : " + lista2.size());

        int i;
        int j;
        for(i = 0; i < lista1.size(); ++i) {
            for(j = 0; j < lista2.size(); ++j) {
                if (((String)lista1.get(i)).equals(lista2.get(j))) {
                    lista2.remove(j);
                    ++counter;
                }
            }
        }

        AllList.add(lista2);
        System.out.println("to sinoliko idion arxeion einai: " + counter);
        System.out.println("to megethos tis listas 2 einai: " + lista2.size());
        System.out.println(lista1.size() + " : " + lista3.size());

        for(i = 0; i < lista1.size(); ++i) {
            for(j = 0; j < lista3.size(); ++j) {
                if (((String)lista1.get(i)).equals(lista3.get(j))) {
                    lista3.remove(j);
                    ++counter2;
                }
            }
        }

        AllList.add(lista3);
        System.out.println("to sinoliko idion arxeion einai: " + counter2);
        System.out.println("to megethos tis listas 3 einai: " + lista3.size());
        System.out.println(lista1.size() + " : " + lista4.size());

        for(i = 0; i < lista1.size(); ++i) {
            for(j = 0; j < lista4.size(); ++j) {
                if (((String)lista1.get(i)).equals(lista4.get(j))) {
                    lista4.remove(j);
                    ++counter3;
                }
            }
        }

        AllList.add(lista4);
        System.out.println("to sinoliko idion arxeion einai: " + counter3);
        System.out.println("to megethos tis listas 4 einai: " + lista4.size());
        return AllList;
    }

    public static void ExtractExcelDev(List<List> Lista, String Destination, String filename) throws IOException {
        String[] headerStrings = new String[]{"TableName"};
        String[] var10000 = new String[]{"Dev", "Sit", "Uat"};
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        CellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.getBoldweight();
        style.setFont(font);
        int firstrow = 0;
        Row row1 = sheet.createRow(firstrow);
        int columnCount1 = 0;
        String[] var15 = headerStrings;
        int var14 = headerStrings.length;

        for(int var13 = 0; var13 < var14; ++var13) {
            String wordheader = var15[var13];
            Cell cell = row1.createCell(columnCount1);
            sheet.autoSizeColumn(columnCount1);
            cell.setCellValue(wordheader);
            cell.setCellStyle(style);
            ++columnCount1;
        }

        int rowCount = 1;
        boolean columnCount = false;

        for(Iterator var31 = ((List)Lista.get(0)).iterator(); var31.hasNext(); ++rowCount) {
            Object columnString = var31.next();
            Row row = sheet.createRow(rowCount);
            Cell cell = row.createCell(0);
            sheet.autoSizeColumn(((String)columnString).length());
            cell.setCellValue(columnString.toString());
        }

        try {
            Throwable var30 = null;
            var15 = null;

            try {
                FileOutputStream outputStream1 = new FileOutputStream(Destination + "\\" + filename + "_DEV.xls");

                try {
                    workbook.write(outputStream1);
                } finally {
                    if (outputStream1 != null) {
                        outputStream1.close();
                    }

                }
            } catch (Throwable var25) {
                if (var30 == null) {
                    var30 = var25;
                } else if (var30 != var25) {
                    var30.addSuppressed(var25);
                }

                throw var30;
            }
        } catch (Throwable var26) {
            var26.printStackTrace();
        }

    }

    public static void ExtractExcelSit(List<List> Lista, String Destination, String filename) throws IOException {
        String[] headerStrings = new String[]{"TableName"};
        String[] var10000 = new String[]{"Dev", "Sit", "Uat"};
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        CellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.getBoldweight();
        style.setFont(font);
        int firstrow = 0;
        Row row1 = sheet.createRow(firstrow);
        int columnCount1 = 0;
        String[] var15 = headerStrings;
        int var14 = headerStrings.length;

        for(int var13 = 0; var13 < var14; ++var13) {
            String wordheader = var15[var13];
            Cell cell = row1.createCell(columnCount1);
            sheet.autoSizeColumn(columnCount1);
            cell.setCellValue(wordheader);
            cell.setCellStyle(style);
            ++columnCount1;
        }

        int rowCount = 1;
        boolean columnCount = false;

        for(Iterator var31 = ((List)Lista.get(1)).iterator(); var31.hasNext(); ++rowCount) {
            Object columnString = var31.next();
            Row row = sheet.createRow(rowCount);
            Cell cell = row.createCell(0);
            sheet.autoSizeColumn(((String)columnString).length());
            cell.setCellValue(columnString.toString());
        }

        try {
            Throwable var30 = null;
            var15 = null;

            try {
                FileOutputStream outputStream1 = new FileOutputStream(Destination + "\\" + filename + "_SIT.xls");

                try {
                    workbook.write(outputStream1);
                } finally {
                    if (outputStream1 != null) {
                        outputStream1.close();
                    }

                }
            } catch (Throwable var25) {
                if (var30 == null) {
                    var30 = var25;
                } else if (var30 != var25) {
                    var30.addSuppressed(var25);
                }

                throw var30;
            }
        } catch (Throwable var26) {
            var26.printStackTrace();
        }

    }

    public static void ExtractExcelUat(List<List> Lista, String Destination, String filename) throws IOException {
        String[] headerStrings = new String[]{"TableName"};
        String[] var10000 = new String[]{"Dev", "Sit", "Uat"};
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        CellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.getBoldweight();
        style.setFont(font);
        int firstrow = 0;
        Row row1 = sheet.createRow(firstrow);
        int columnCount1 = 0;
        String[] var15 = headerStrings;
        int var14 = headerStrings.length;

        Cell cell;
        for(int var13 = 0; var13 < var14; ++var13) {
            String wordheader = var15[var13];
            cell = row1.createCell(columnCount1);
            sheet.autoSizeColumn(columnCount1);
            cell.setCellValue(wordheader);
            cell.setCellStyle(style);
            ++columnCount1;
        }

        int rowCount = 1;

        Iterator var29;
        for(var29 = ((List)Lista.get(2)).iterator(); var29.hasNext(); ++rowCount) {
            Object columnString = var29.next();
            Row row = sheet.createRow(rowCount);
            cell = row.createCell(0);
            sheet.autoSizeColumn(((String)columnString).length());
            cell.setCellValue(columnString.toString());
        }

        try {
            Throwable var28 = null;
            var29 = null;

            try {
                FileOutputStream outputStream2 = new FileOutputStream(Destination + "\\" + filename + "_UAT.xls");

                try {
                    workbook.write(outputStream2);
                } finally {
                    if (outputStream2 != null) {
                        outputStream2.close();
                    }

                }
            } catch (Throwable var24) {
                if (var28 == null) {
                    var28 = var24;
                } else if (var28 != var24) {
                    var28.addSuppressed(var24);
                }

                throw var28;
            }
        } catch (Throwable var25) {
            var25.printStackTrace();
        }

    }
}