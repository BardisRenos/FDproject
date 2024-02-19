package org.example.DBVOL;

public class Data {
    String tableName;
    int NumOfPages;
    int UsedPages;
    int AvailablePages;
    double FileFull;

    public Data(String tableName, int numOfPages, int usedPages, int availablePages, double fileFull) {
        this.tableName = tableName;
        this.NumOfPages = numOfPages;
        this.UsedPages = usedPages;
        this.AvailablePages = availablePages;
        this.FileFull = fileFull;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getNumOfPages() {
        return this.NumOfPages;
    }

    public void setNumOfPages(int numOfPages) {
        this.NumOfPages = numOfPages;
    }

    public int getUsedPages() {
        return this.UsedPages;
    }

    public void setUsedPages(int usedPages) {
        this.UsedPages = usedPages;
    }

    public int getAvailablePages() {
        return this.AvailablePages;
    }

    public void setAvailablePages(int availablePages) {
        this.AvailablePages = availablePages;
    }

    public double getFileFull() {
        return this.FileFull;
    }

    public void setFileFull(double fileFull) {
        this.FileFull = fileFull;
    }
}
