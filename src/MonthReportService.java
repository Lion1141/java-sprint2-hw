import java.util.ArrayList;
import java.util.Comparator;

public class MonthReportService {
    ArrayList<MonthReport> Reports;
    FileReader fileReader;

    MonthReportService(){
        Reports = new ArrayList<>();
        fileReader = new FileReader();
    }

    ArrayList<MonthReport> GetMonthReports(boolean refreshData){ //Сохранение отчётов
        if(Reports.size() != 0 && !refreshData){
            return Reports;
        }
        for (int i = 1; i <= 12; i++) {
            ArrayList<String> lines;
            if (i < 10) {
                lines = fileReader.readFileContents("m.20210" + i + ".csv");
            } else {
                lines = fileReader.readFileContents("m.2021" + i + ".csv");
            }
            if (lines.size() != 0) {
                var report = new MonthReport(); //вызов списка для сохранения данных месячных отчётов
                report.Records = new ArrayList<>();
                report.MonthName = MonthNameHelper.MonthNames.get(i); //получение имени месяца из хэш-таблицы NameHelper'a
                report.MonthNumber = i;
                lines.remove(0);
                for(String item : lines){
                    var record = new MonthReportRecord(item);
                    report.Records.add(record); //заполнение списка с отчётами
                }
                Reports.add(report); //сохранение данных из отчётов
            }
        }
        return Reports;
    }

    void PopulateMonthRecords() { //вывод информации из отчётов
        if (Reports.size() == 0) {
            System.out.println("Отчёты отсутствуют.");
        } else {
            for (MonthReport report : Reports) {
                System.out.println(report.MonthName);
                var mostProfitable = GetMostProfitableProduct(report);
                var mostExpensiveProduct = GetMostExpensiveProduct(report);
                System.out.println("Самый прибыльный товар: " + mostProfitable.itemName + " " + mostProfitable.quantity * mostProfitable.unitPrice);
                System.out.println("Самая большая трата: " + mostExpensiveProduct.itemName + " " + mostExpensiveProduct.quantity * mostExpensiveProduct.unitPrice);
            }
        }
    }

    private MonthReportRecord GetMostProfitableProduct(MonthReport report){ //получение самого прибыльного товара
        var profitableRecords = new ArrayList<MonthReportRecord>();

        for(MonthReportRecord record : report.Records){
            if(!record.isExpense){
                profitableRecords.add(record);
            }
        }

        profitableRecords.sort(Comparator.comparing(record -> record.quantity * record.unitPrice));
        var mostProfitable = profitableRecords.get(profitableRecords.size() - 1);
        return mostProfitable;
    }

    MonthReportRecord GetMostExpensiveProduct(MonthReport report){ //получение самого дорогого товара
        var expensiveRecords = new ArrayList<MonthReportRecord>();
        for(MonthReportRecord record : report.Records){
            if(record.isExpense){
                expensiveRecords.add(record);
            }
        }

        expensiveRecords.sort(Comparator.comparing(record -> record.quantity * record.unitPrice));
        var mostProfitable = expensiveRecords.get(expensiveRecords.size() - 1);
        return mostProfitable;
    }
    Integer GetMonthExpenses(MonthReport report){ //получение месячных затрат
        var monthExpenses = 0;
        for(MonthReportRecord record : report.Records){
            if(record.isExpense)
                monthExpenses += record.quantity * record.unitPrice;
        }
        return monthExpenses;
    }

    Integer GetMonthIncome(MonthReport report){ //получение месячной прибыли
        var monthExpenses = 0;
        for(MonthReportRecord record : report.Records){
            if(!record.isExpense)
                monthExpenses += record.quantity * record.unitPrice;
        }
        return monthExpenses;
    }
}
