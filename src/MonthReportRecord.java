public class MonthReportRecord {
    String itemName;
    int quantity;
    int unitPrice;
    boolean isExpense;
    MonthReportRecord(String line){ //конструктор для обработки строк из файла отчёта
        String[] lineContents = line.split(",");
        this.itemName = lineContents[0];
        this.quantity = Integer.parseInt(lineContents[2]);
        this.unitPrice = Integer.parseInt(lineContents[3]);
        this.isExpense = lineContents[1].equals("TRUE");
    }
}
