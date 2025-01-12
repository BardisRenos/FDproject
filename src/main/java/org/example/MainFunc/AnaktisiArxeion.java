package org.example.MainFunc;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
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

    // Add all required first column values
    List<String> firstColumnValues = Arrays.asList(
            "FDH005", "FDH006", "FDH007", "FDH008", "FDH009",
            "FDH010", "FDH011", "FDH012", "FDH013", "FDH014",
            "FDH015", "FDH016", "FDH017", "FDH018", "FDH019",
            "FDH020", "FDH021", "FDH022", "FDH023", "FDH024",
            "FDH025", "FDH026", "FDH027", "FDH028", "FDH029",
            "FDH030", "FDH031", "FDH032", "FDH033", "FDH034",
            "FDH035", "FDH036", "FDH037", "FDH038", "FDH039",
            "FDH040", "FDH041", "FDH042", "FDH043", "FDH044",
            "FDH045", "FDH046", "FDH047", "FDH048", "FDH049",
            "FDH050", "FDH051", "FDH052", "FDH053", "FDH054",
            "FDH055", "FDH056", "FDH057", "FDH058", "FDH059",
            "FDH060", "FDH061", "FDH062", "FDH063", "FDH064",
            "FDH065", "FDH066", "FDH067", "FDH068", "FDH069",
            "FDH070", "FDH071", "FDH072", "FDH073", "FDH074",
            "FDH075", "FDH076", "FDH077", "FDH078", "FDH079",
            "FDH080", "FDH081", "FDH082", "FDH083", "FDH084",
            "FDH086", "FDH087", "FDH088", "FDH089", "FDH090",
            "FDH091", "FDH092", "FDH093", "FDH094", "FDH095",
            "FDH096", "FDH097", "FDH098", "FDH099", "FDH100",
            "FDH101", "FDH102", "FDH103", "FDH104", "FDH105",
            "FDH106", "FDH107", "FDH108", "FDH109", "FDH110",
            "FDH111", "FDH112", "FDH113", "FDH114", "FDH115",
            "FDH116", "FDH117", "FDH118", "FDH119", "FDH120",
            "FDH121", "FDH122", "FDH123", "FDH124", "FDH125",
            "FDH126", "FDH127", "FDH128", "FDH129", "FDH130",
            "FDH131", "FDH132", "FDH133", "FDH134", "FDH135",
            "FDH136", "FDH137", "FDH138", "FDH139", "FDH140",
            "FDH141", "FDH142", "FDH143", "FDH144", "FDH145",
            "FDH146", "FDH147", "FDH148", "FDH149", "FDH150",
            "FDH151", "FDH152", "FDH153", "FDH154", "FDH155",
            "FDH156", "FDH157", "FDH158", "FDH159", "FDH160",
            "FDH161", "FDH162", "FDH163", "FDH164", "FDH165",
            "FDH166", "FDH167", "FDH168", "FDH169", "FDH170",
            "FDH171", "FDH172", "FDH173", "FDH174", "FDH175",
            "FDH176", "FDH177", "FDH178", "FDH179", "FDH180",
            "FDH181", "FDH182", "FDH183", "FDH184", "FDH185",
            "FDH186", "FDH187", "FDH188", "FDH189", "FDH190",
            "FDH191", "FDH192", "FDH193", "FDH194", "FDH195",
            "FDH196", "FDH197", "FDH198", "FDH199", "FDH200",
            "FDH201", "FDH207", "FDH208", "FDH209", "FDH210",
            "FDH211", "FDH212", "FDH213", "FDH214", "FDH215",
            "FDH216", "FDH217", "FDH218", "FDH219", "FDH220",
            "FDH221", "FDH222", "FDH223", "FDH224", "FDH225",
            "FDH226", "FDH227", "FDH228", "FDH229", "FDH230",
            "FDH231", "FDH232", "FDH233", "FDH234", "FDH235",
            "FDH236", "FDH237", "FDH238", "FDH239", "FDH240",
            "FDH241", "FDH242", "FDH243", "FDH244", "FDH245",
            "FDH246", "FDH247", "FDH248", "FDH249", "FDH250",
            "FDH251", "FDH252", "FDH253", "FDH254", "FDH255",
            "FDH256", "FDH257", "FDH258", "FDH259", "FDH260",
            "FDH261", "FDH262", "FDH263", "FDH264", "FDH265",
            "FDH266", "FDH267", "FDH268", "FDH269", "FDH270",
            "FDH271", "FDH272", "FDH273", "FDH274", "FDH275",
            "FDH276", "FDH277", "FDH278", "FDH279", "FDH280",
            "FDH281", "FDH282", "FDH283", "FDH284", "FDH285",
            "FDH286", "FDH287", "FDH288", "FDH289", "FDH290",
            "FDH291", "FDH292", "FDH293", "FDH294", "FDH295",
            "FDH296", "FDH297", "FDH298", "FDH299", "FDH300",
            "FDH301", "FDH302", "FDH303", "FDH304", "FDH305",
            "FDH306", "FDH307", "FDH308", "FDH309", "FDH310",
            "FDH311", "FDH312", "FDH313", "FDH314", "FDH315",
            "FDH316", "FDH317", "FDH318", "FDH319", "FDH320",
            "FDH321", "FDH322", "FDH323", "FDH324", "FDH325",
            "FDH326", "FDH327", "FDH328", "FDH329", "FDH330",
            "FDH331", "FDH332", "FDH333", "FDH334", "FDH335",
            "FDH336", "FDH337", "FDH338", "FDH339", "FDH340",
            "FDH341", "FDH342", "FDH343", "FDH344", "FDH345",
            "FDH346", "FDH347", "FDH348", "FDH349", "FDH350",
            "FDH351", "FDH352", "FDH353", "FDH354", "FDH355"
    );

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
                    if (selectedFiles.length >= 1) {
                        for (File datFile : selectedFiles) {
                            String fileName = datFile.getName().replace(".DAT", "");
                            int result = AnaktisiArxeion.this.exportToExcel(
                                    AnaktisiArxeion.onomaproorismou,
                                    fileName,
                                    datFile.getAbsolutePath());
                            if (result == 1) {
                                System.out.println("Exported: " + datFile.getName());
                            } else {
                                System.err.println("Failed to export: " + datFile.getName());
                            }
                        }
                        List<String> listOfXlsFiles = listXLSFiles(AnaktisiArxeion.onomaproorismou);
                        createSummaryOfSheets(listOfXlsFiles, AnaktisiArxeion.onomaproorismou+"/"+outputFilePath, listOfXlsFiles, firstColumnValues);
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

        // Write to file
        try (FileOutputStream outputStream = new FileOutputStream(destination + "/" + name + ".xls")) {
            workbook.write(outputStream);
        }

        return 1; // Success
    }

    public static List<String> listXLSFiles(String folderPath) {
        List<String> xlsFiles = new ArrayList<>();
        File folder = new File(folderPath);

        // Ensure the provided path is a directory
        if (folder.isDirectory()) {
            // Iterate over the files in the directory
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    // Check if the file has a .xls extension
                    if (file.isFile() && file.getName().endsWith(".xls")) {
                        xlsFiles.add(file.getAbsolutePath());
                    }
                }
            }
        } else {
            System.err.println("The provided path is not a directory: " + folderPath);
        }
        return xlsFiles;
    }

    public static void createSummaryOfSheets(List<String> inputFilePaths, String outputFilePath, List<String> columnNames, List<String> firstColumnValues) {
        try (XSSFWorkbook newWorkbook = new XSSFWorkbook()) {
            // Create a new sheet in the output workbook
            Sheet newSheet = newWorkbook.createSheet("MergedColumns");

            // Leave two empty rows at the top for column headers
            int headerRowIndex = 0;
            Row headerRow = newSheet.createRow(headerRowIndex);

            // Populate the first column with provided values
            for (int i = 0; i < firstColumnValues.size(); i++) {
                Row row = newSheet.getRow(i + 2); // Skipping two rows above for the headers
                if (row == null) {
                    row = newSheet.createRow(i + 2); // Start populating data from row 2
                }
                Cell firstCell = row.createCell(0); // First column (index 0)
                firstCell.setCellValue(firstColumnValues.get(i));
            }

            // Create headers for all columns
            for (int i = 0; i < columnNames.size(); i++) {
                Cell headerCell = headerRow.createCell(i + 1); // Second column onwards
                String result = columnNames.get(i).split("\\\\")[5].split("\\.")[0];
                headerCell.setCellValue(result); // Set header from columnHeaders list
            }

            // Iterate through input file paths and merge columns
            for (int i = 0; i < inputFilePaths.size(); i++) {
                String filePath = inputFilePaths.get(i);
                try (FileInputStream fis = new FileInputStream(filePath);
                     HSSFWorkbook inputWorkbook = new HSSFWorkbook(fis)) {

                    // Get the first sheet of the current input workbook
                    Sheet inputSheet = inputWorkbook.getSheetAt(0);

                    // Copy data from input file into the new sheet
                    int rowIndex = 0; // Row index in the new sheet
                    for (Row inputRow : inputSheet) {
                        if (rowIndex >= firstColumnValues.size()) {
                            break; // Prevent overwriting if rows exceed provided first column values
                        }

                        Cell inputCell = inputRow.getCell(1); // Assuming data starts from the second column of input files
                        Row newRow = newSheet.getRow(rowIndex + 2); // Start adding data from row 2
                        if (newRow == null) {
                            newRow = newSheet.createRow(rowIndex + 2); // Create new row if necessary
                        }
                        Cell newCell = newRow.createCell(i + 1);
                        if (inputCell != null) {
                            switch (inputCell.getCellType()) {
                                case STRING:
                                    newCell.setCellValue(inputCell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    newCell.setCellValue(inputCell.getNumericCellValue());
                                    break;
                                case BOOLEAN:
                                    newCell.setCellValue(inputCell.getBooleanCellValue());
                                    break;
                                case FORMULA:
                                    newCell.setCellFormula(inputCell.getCellFormula());
                                    break;
                                default:
                                    newCell.setCellValue("");
                            }
                        }
                        rowIndex++;
                    }
                }
            }

            // Row and column indices for Sum V and Sum H
            int sumVRowIndex = 350; // Row 350 for Sum V
            int sumHRowIndex = 353; // Row 353 for Sum H (starting from this row)

            // Create the "Sum V" row at row 350
            Row sumVRow = newSheet.createRow(sumVRowIndex);
            sumVRow.createCell(0).setCellValue("Sum V"); // Label for the vertical sum

            // Create the "Sum H" row at row 353 (Sum H starts at row 353, and sum values start from column B)
            Row sumHRow = newSheet.createRow(sumHRowIndex);
            sumHRow.createCell(0).setCellValue("Sum H"); // Label for the horizontal sum

            // Calculate and place vertical sums (Sum V) in row 350
            for (int col = 1; col <= columnNames.size(); col++) {
                String columnLetter = CellReference.convertNumToColString(col); // Convert column index to Excel column letter
                String sumFormula = "SUM(" + columnLetter + "3:" + columnLetter + (sumVRowIndex - 1) + ")"; // Sum vertically
                sumVRow.createCell(col).setCellFormula(sumFormula); // Place vertical sum formula
                newSheet.autoSizeColumn(col); // Adjust column width
            }

            // Loop to calculate the horizontal sums for rows B3 to BAF (3 to 302)
            for (int row = 3; row <= firstColumnValues.size(); row++) {  // Loop through rows from 3 to 302
                Row dataRow = newSheet.getRow(row);
                if (dataRow == null) dataRow = newSheet.createRow(row); // Create the row if it doesn't exist

                // Sum formula for horizontal sum from B to BAF (Column 2 to Column 56)
                String rowSumFormula = "SUM(B" + row + ":BAF" + row + ")"; // Sum from B3 to BAF3, B4 to BAF4, ..., B302 to BAF302

                // Calculate the column index to place the sum in row 353 (B = 1, C = 2, ... AF = 56)
                int colIndex = row - 2; // Column index for placing the Sum H value (row 3 -> index 1, row 4 -> index 2, ...)

                // Add the sum formula
                sumHRow.createCell(colIndex).setCellFormula(rowSumFormula); // Add horizontal sum formula in columns B to AF

                // Adjust column width for the corresponding column
                newSheet.setColumnWidth(colIndex, 7000);  // Increase column width to make sure numbers fit in the cell
            }

            // Optional: Write the new workbook to the output file
            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                newWorkbook.write(fos);
                newWorkbook.close();
                System.out.println("Merged Excel file created successfully at: " + outputFilePath);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("An error occurred while merging Excel files.");
        }
    }
}
