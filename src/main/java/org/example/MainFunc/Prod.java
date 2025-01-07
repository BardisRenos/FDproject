package org.example.MainFunc;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import org.apache.poi.hssf.usermodel.HSSFFont;

public class Prod extends JFrame {
    JLabel ArxeioPRD1;
    JLabel ArxeioPRD2;
    JLabel OnomaNeouArxeiou;
    JLabel Copyright;
    JLabel image;
    JTextField Arxeio1;
    JTextField Arxeio2;
    JTextField OnomaNeou;
    JTextField OnomaEksagogis;
    JButton Apotelesma;
    JButton PRD1;
    JButton PRD2;
    JButton exit;
    JButton Destination;
    JButton back;
    JTextField OnomaArxieou;
    public static String OnomaDr = null;
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
    private ArrayList<String> listaArxeion = new ArrayList();

    public static void main(String[] args) {
        new Prod();
    }

    public Prod() {
        super("Prod");
        this.getContentPane().setBackground(Color.LIGHT_GRAY);
        this.setSize(400, 400);
        this.setLayout((LayoutManager)null);
        this.ArxeioPRD1 = new JLabel("Choose the former PRD:");
        this.ArxeioPRD1.setBounds(20, 100, 150, 20);
        this.ArxeioPRD2 = new JLabel("Choose the later PRD:");
        this.ArxeioPRD2.setBounds(20, 150, 150, 20);
        this.Arxeio1 = new JTextField();
        this.Arxeio1.setBounds(158, 100, 100, 20);
        this.PRD1 = new JButton("Choose");
        this.PRD1.setBounds(158, 120, 100, 20);
        this.Arxeio2 = new JTextField();
        this.Arxeio2.setBounds(158, 150, 100, 20);
        this.PRD2 = new JButton("Choose");
        this.PRD2.setBounds(158, 170, 100, 20);
        this.OnomaNeouArxeiou = new JLabel("Give name:");
        this.OnomaNeouArxeiou.setBounds(20, 210, 75, 20);
        this.OnomaNeou = new JTextField("Give name to file");
        this.OnomaNeou.setBounds(158, 210, 100, 20);
        this.OnomaEksagogis = new JTextField();
        this.OnomaEksagogis.setBounds(158, 240, 100, 20);
        this.Destination = new JButton("Location");
        this.Destination.setBounds(158, 260, 100, 20);
        this.Apotelesma = new JButton("Extract");
        this.Apotelesma.setBounds(20, 300, 100, 20);
        this.exit = new JButton("Exit");
        this.exit.setBounds(320, 320, 60, 40);
        this.back = new JButton("Back");
        this.back.setBounds(240, 320, 65, 40);
        this.Copyright = new JLabel("Created by Renos Bardis");
        this.Copyright.setBounds(20, 350, 300, 20);
        this.image = new JLabel("");
        this.image.setBounds(100, 20, 200, 37);
        this.add(this.ArxeioPRD1);
        this.add(this.ArxeioPRD2);
        this.add(this.Arxeio1);
        this.add(this.Arxeio2);
        this.add(this.PRD1);
        this.add(this.PRD2);
        this.add(this.OnomaNeouArxeiou);
        this.add(this.OnomaNeou);
        this.add(this.OnomaEksagogis);
        this.add(this.Destination);
        this.add(this.Apotelesma);
        this.add(this.back);
        this.add(this.exit);
        this.add(this.image);
        this.add(this.Copyright);
        this.OnomaNeou.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Prod.this.OnomaNeou.setText("");
            }
        });
        this.Arxeio1.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(1);
                    Prod.this.droppedFiles = (List)evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    Prod.this.listaArxeion.add(((File)Prod.this.droppedFiles.get(0)).toString());
                    Iterator var3 = Prod.this.droppedFiles.iterator();

                    while(var3.hasNext()) {
                        File file = (File)var3.next();
                        if (Prod.this.deleteText) {
                            Prod.this.Arxeio1.setText("");
                            Prod.this.Arxeio1.setText(file.toString() + "\n");
                            Prod.this.deleteText = false;
                        } else {
                            Prod.this.Arxeio1.setText(file.toString() + "\n");
                        }
                    }
                } catch (Exception var4) {
                    var4.printStackTrace();
                }

            }
        });
        this.PRD1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(0);
                fileChooser.setAcceptAllFileFilterUsed(true);
                fileChooser.setPreferredSize(new Dimension(700, 700));
                int rval = fileChooser.showOpenDialog((Component)null);
                if (rval == 0) {
                    Prod.this.Arxeio1.setText(fileChooser.getSelectedFile().toString());
                    Prod.folder = fileChooser.getCurrentDirectory().toString();
                }

            }
        });
        this.PRD2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(Prod.folder);
                fileChooser.setFileSelectionMode(0);
                fileChooser.setAcceptAllFileFilterUsed(true);
                fileChooser.setPreferredSize(new Dimension(700, 700));
                int rval = fileChooser.showOpenDialog((Component)null);
                if (rval == 0) {
                    Prod.this.Arxeio2.setText(fileChooser.getSelectedFile().toString());
                }

            }
        });
        this.Destination.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(1);
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.setPreferredSize(new Dimension(700, 700));
                int rval = fileChooser.showOpenDialog((Component)null);
                if (rval == 0) {
                    Prod.this.OnomaEksagogis.setText(fileChooser.getSelectedFile().toString());
                }

            }
        });
        this.Apotelesma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Prod.onomaproorismou = Prod.this.OnomaEksagogis.getText().toString();
                Prod.onomaproorismou = Prod.onomaproorismou.replace("\\", "/");
                Prod.onomatouarxeiouString = Prod.this.OnomaNeou.getText().toString();
                Prod.onomaArxikouDR = Prod.this.Arxeio1.getText().toString();
                Prod.onomaArxikouDR = Prod.onomaArxikouDR.replace("\\", "/");
                Prod.onomaArxikouPRD = Prod.this.Arxeio2.getText().toString();
                Prod.onomaArxikouPRD = Prod.onomaArxikouPRD.replace("\\", "/");
                Thread threadButton = new Thread(new Runnable() {
                    public void run() {
                        try {
                            File file = new File(Prod.onomaproorismou + "/" + "Compare_PROD-" + Prod.onomatouarxeiouString);
                            if (file.exists()) {
                                JOptionPane.showMessageDialog((Component)null, "The Directory is already exists");
                                Prod.this.OnomaNeou.setText("");
                            } else if (!Prod.onomaArxikouDR.equalsIgnoreCase("MFD_DR.txt") && !Prod.onomaArxikouPRD.equalsIgnoreCase("MFD_PRD.txt")) {
                                Prod.this.savetofile(Prod.onomaArxikouDR, Prod.onomaArxikouPRD, Prod.onomatouarxeiouString, Prod.onomaproorismou);
                                Prod.this.exportToExcelSize(Prod.onomaproorismou, Prod.onomatouarxeiouString);
                                JOptionPane.showMessageDialog((Component)null, "The comparison finished successfully");
                                Prod.this.Arxeio1.setText("");
                                Prod.this.Arxeio2.setText("");
                                Prod.this.OnomaEksagogis.setText("");
                                Prod.this.OnomaNeou.setText("");
                            } else {
                                JOptionPane.showMessageDialog((Component)null, "Files are not compatible. Please choose a MFD_DR or MFD_PRD files");
                                Prod.this.Arxeio1.setText("");
                                Prod.this.Arxeio2.setText("");
                                Prod.this.OnomaEksagogis.setText("");
                                Prod.this.OnomaArxieou.setText("");
                            }
                        } catch (IOException | IndexOutOfBoundsException var2) {
                            var2.printStackTrace();
                        } catch (Throwable e) {
                            throw new RuntimeException(e);
                        }

                    }
                });
                threadButton.start();
            }
        });
        this.exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });
        this.back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                new MainGUI();
                Prod.this.dispose();
            }
        });
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void exportToExcelSize(String destination, String name) throws Throwable {
        String[] headerStrings = new String[]{"QualifierDR", "FilenameDR", "FcycDR", "DadEDR", "DadTDR", "SizeDR", "F/LDR", "Last Ref DateDR", "Last Ref TimeDR", "QualifierPRD", "FilenamePRD", "FcycPRD", "DadEPRD", "DadTPRD", "SizePRD", "F/LPRD", "Last Ref DatePRD", "Last Ref TimeDR", "Result"};
        String[] column = null;
        LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(new File(destination + "/" + "Compare_PROD-" + name + "/" + "Compare_PROD-" + name + "Size" + ".txt")));
        lineNumberReader.skip(Long.MAX_VALUE);
        int resultoflines = lineNumberReader.getLineNumber();
        lineNumberReader.close();
        String[][] pinakas = new String[resultoflines][19];
        FileInputStream file = new FileInputStream(destination + "/" + "Compare_PROD-" + name + "/" + "Compare_PROD-" + name + "Size" + ".txt");
        Scanner inScanner = new Scanner(file);

        try {
            for(int lineCount = 0; inScanner.hasNextLine(); ++lineCount) {
                column = inScanner.nextLine().split("\\s+");

                for(int i = 0; i < column.length; ++i) {
                    pinakas[lineCount][i] = column[i];
                }
            }

            inScanner.close();
        } catch (ArrayStoreException var42) {
        }

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

        path = destination + "/" + "Compare_PROD-" + name + "/" + "Compare_PROD-" + name + "Size.txt";
        Path xpath = Paths.get(path);
        Files.delete(xpath);
        rowCount = 1;
        String[][] var23 = pinakas;
        int var22 = pinakas.length;

        for(int var47 = 0; var47 < var22; ++var47) {
            rowStrings = var23[var47];
            Row row = sheet.createRow(rowCount);
            int columnCount = 0;
            String[] var29 = rowStrings;
            int var28 = rowStrings.length;

            for(int var27 = 0; var27 < var28; ++var27) {
                String columnString = var29[var27];
                Cell cell1 = row.createCell(columnCount);
                if (columnString instanceof String) {
                    int number;
                    if (columnString.startsWith("-") && columnString.matches("-?\\d+")) {
                        number = Integer.parseInt(columnString);
                        CellStyle style2 = workbook.createCellStyle();
                        HSSFFont font2 = workbook.createFont();
                        font2.setColor(IndexedColors.RED.getIndex());
                        style2.setFont(font2);
                        cell1.setCellType(CellType.forInt(0));
                        cell1.setCellValue((double)number);
                        cell1.setCellStyle(style2);
                    } else if (columnString.matches("-?\\d+")) {
                        number = Integer.parseInt(columnString);
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

        Throwable var46 = null;
        cell = null;

        try {
            FileOutputStream outputStream = new FileOutputStream(destination + "/" + "Compare_PROD-" + name + "/" + "Compare_PROD-" + name + "Size.xls");

            try {
                workbook.write(outputStream);
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }

            }

        } catch (Throwable var41) {
            if (var46 == null) {
                var46 = var41;
            } else if (var46 != var41) {
                var46.addSuppressed(var41);
            }

            throw var46;
        }
    }

    int savetofile(String OnomaDR, String OnomaPRD, String name, String destination) throws IOException {
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
        String line = null;
        String line1 = null;
        String[] onomata = null;
        String[] onomata2 = null;
        destination = destination + "/" + "Compare_PROD-" + name;
        (new File(destination)).mkdir();
        FileInputStream file = null;

        try {
            file = new FileInputStream(OnomaDR);
        } catch (FileNotFoundException var66) {
            var66.printStackTrace();
        }

        BufferedReader brDR = new BufferedReader(new InputStreamReader(file));
        FileInputStream file2 = null;

        try {
            file2 = new FileInputStream(OnomaPRD);
        } catch (IOException var65) {
            var65.printStackTrace();
        }

        BufferedReader brPrd = new BufferedReader(new InputStreamReader(file2));

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
                            lista2.add("LOAD SECURE$");
                            ++vrikaxaraktira;
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
                    } catch (ArrayIndexOutOfBoundsException var64) {
                    }
                }
            }

            brDR.close();

            while(true) {
                while((line1 = brPrd.readLine()) != null) {
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
                                lista12.add("LOAD SECURE$");
                                ++vrikaxaraktira;
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
                        } catch (ArrayIndexOutOfBoundsException var63) {
                        }
                    }
                }

                brPrd.close();
                int akeraiosDR = 0;
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

                label308:
                for(int i = 0; i < lista1.size(); ++i) {
                    int j = movesecondarray;

                    while(true) {
                        while(true) {
                            if (j >= lista11.size()) {
                                continue label308;
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
//                            int akeraiosDR;
//                            int akeraiosPRD;

                            if ((lista1.get(i)).startsWith("$") && (lista11.get(j)).startsWith("$")) {
                                if ((lista1.get(i)).equals(lista11.get(j)) && (lista2.get(i)).equals(lista12.get(j)) && (lista3.get(i)).equals(lista13.get(j))) {
                                    if ((lista6.get(i)).equals(lista16.get(j))) {
                                        ++count;
                                        movesecondarray = j + 1;
                                    } else {
                                        IntegerDR = Integer.parseInt(lista6.get(i));
                                        IntegetPRD = Integer.parseInt(lista16.get(j));
                                        IntegerDRPRD = IntegerDR - IntegetPRD;
                                        System.out.println("ta stoixeia einai :" + lista1.get(i) + " " + lista2.get(i) + " " + lista3.get(i) + " " + lista11.get(j) + " " + lista12.get(j) + " " + lista13.get(j) + " " + IntegerDRPRD);
                                        ++vrikeSizegiaArxeio;
                                        arxeioeggrafisSize = new File(destination + "/" + "Compare_PROD-" + name + "Size" + ".txt");
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
                                    continue label308;
                                }

                                if (!(lista1.get(i)).equals(lista11.get(j)) || !(lista2.get(i)).equals(lista12.get(j))) {
                                    akeraiosDR = Integer.parseInt(lista3.get(i));
                                    akeraiosPRD = Integer.parseInt(lista13.get(j));
                                    if (akeraiosDR > akeraiosPRD) {
                                        continue label308;
                                    }

                                    ++j;
                                    continue;
                                }

                                if (Character.isLetter((lista2.get(i)).charAt(0)) && Character.isLetter((lista12.get(j)).charAt(0)) && (lista12.get(j)).equals("PFM")) {
                                    continue label308;
                                }
                            }

                            if ((lista1.get(i)).startsWith("$") && Character.isDigit((lista11.get(j)).charAt(0))) {
                                continue label308;
                            }

                            if (Character.isDigit((lista1.get(i)).charAt(0)) && (lista11.get(j)).startsWith("$")) {
                                ++j;
                            } else {
                                if (Character.isDigit((lista1.get(i)).charAt(0)) && Character.isLetter((lista11.get(j)).charAt(0))) {
                                    continue label308;
                                }

                                if (Character.isDigit((lista1.get(i)).charAt(0)) && Character.isDigit((lista11.get(j)).charAt(0))) {
                                    if ((lista1.get(i)).equals(lista11.get(j)) && (lista2.get(i)).equals(lista12.get(j))) {
                                        if ((lista3.get(i)).equals(lista13.get(j))) {
                                            if ((lista6.get(i)).equals(lista16.get(j))) {
                                                movesecondarray = j + 1;
                                            } else {
                                                IntegerDR = Integer.parseInt(lista6.get(i));
                                                IntegetPRD = Integer.parseInt(lista16.get(j));
                                                IntegerDRPRD = IntegerDR - IntegetPRD;
                                                System.out.println("Size : " + lista1.get(i) + " " + lista2.get(i) + " " + lista3.get(i) + " " + lista11.get(j) + " " + lista12.get(j) + " " + lista13.get(j) + " " + IntegerDRPRD);
                                                arxeioeggrafisSize = new File(destination + "/" + "Compare_PROD-" + name + "Size" + ".txt");
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

                                                movesecondarray = j + 1;
                                            }
                                            continue label308;
                                        }
                                    } else if (!(lista1.get(i)).equals(lista11.get(j))) {
                                        IntegerDR = (lista1.get(i)).charAt(0);
                                        IntegetPRD = (lista11.get(j)).charAt(0);
                                        akeraiosDR = Integer.parseInt(String.valueOf((char)IntegerDR));
                                        akeraiosPRD = Integer.parseInt(String.valueOf((char)IntegetPRD));
                                        if (akeraiosDR < akeraiosPRD) {
                                            continue label308;
                                        }

                                        if (akeraiosDR > akeraiosPRD) {
                                            movesecondarray = j + 1;
                                            continue label308;
                                        }
                                    }
                                }

                                if (Character.isLetter((lista1.get(i)).charAt(0)) && Character.isLetter((lista11.get(j)).charAt(0))) {
                                    if (((String)lista1.get(i)).equals(lista11.get(j)) && ((String)lista2.get(i)).equals(lista12.get(j))) {
                                        if (((String)lista3.get(i)).equals(lista13.get(j))) {
                                            if (((String)lista6.get(i)).equals(lista16.get(j))) {
                                                ++count;
                                                movesecondarray = j + 1;
                                            } else {
                                                IntegerDR = Integer.parseInt((String)lista6.get(i));
                                                IntegetPRD = Integer.parseInt((String)lista16.get(j));
                                                IntegerDRPRD = IntegerDR - IntegetPRD;
                                                System.out.println("Size :" + (String)lista1.get(i) + " " + (String)lista2.get(i) + " " + (String)lista3.get(i) + " " + (String)lista11.get(j) + " " + (String)lista12.get(j) + " " + (String)lista13.get(j) + " " + IntegerDRPRD);
                                                arxeioeggrafisSize = new File(destination + "/" + "Compare_PROD-" + name + "Size" + ".txt");
                                                fwSize = null;

                                                try {
                                                    fwSize = new FileWriter(arxeioeggrafisSize, true);
                                                } catch (IOException var58) {
                                                    var58.printStackTrace();
                                                }

                                                outputSize = new BufferedWriter(fwSize);

                                                try {
                                                    outputSize.write(String.format("%-12s %-12s %-5s %-5s %-5s %-5s %-2s %-5s %-10s %-12s %-12s %-5s %-5s %-5s %-5s %-2s %-5s %-10s %-10s", lista1.get(i), lista2.get(i), lista3.get(i), lista4.get(i), lista5.get(i), lista6.get(i), lista7.get(i), lista8.get(i), lista9.get(i), lista11.get(j), lista12.get(j), lista13.get(j), lista14.get(j), lista15.get(j), lista16.get(j), lista17.get(j), lista18.get(j), lista19.get(j), IntegerDRPRD));
                                                    outputSize.newLine();
                                                    outputSize.close();
                                                } catch (IndexOutOfBoundsException | IOException var57) {
                                                    var57.printStackTrace();
                                                }

                                                movesecondarray = j + 1;
                                            }
                                            continue label308;
                                        }

                                        if (!((String)lista3.get(i)).equals(lista13.get(j))) {
                                            akeraiosDR = Integer.parseInt((String)lista3.get(i));
                                            akeraiosPRD = Integer.parseInt((String)lista13.get(j));
                                            if (akeraiosDR < akeraiosPRD) {
                                                continue label308;
                                            }

                                            ++j;
                                            continue;
                                        }
                                    }

                                    if (!((String)lista1.get(i)).equals(lista11.get(j))) {
                                        if (((String)lista1.get(i)).compareTo((String)lista11.get(j)) < 0) {
                                            continue label308;
                                        }

                                        if (((String)lista1.get(i)).compareTo((String)lista11.get(j)) > 0) {
                                            ++j;
                                            continue;
                                        }
                                    }

                                    if (((String)lista1.get(i)).equals(lista11.get(j)) && !((String)lista2.get(i)).equals(lista12.get(j))) {
                                        if (((String)lista2.get(i)).compareTo((String)lista12.get(j)) < 0) {
                                            movesecondarray = j;
                                            continue label308;
                                        }

                                        ++j;
                                    }
                                }
                            }
                        }
                    }
                }

                return 1;
            }
        }
    }
}
