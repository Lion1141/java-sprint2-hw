import java.util.ArrayList;
import java.util.Comparator;

public class MonthReportService {
    ArrayList<MonthReport> reports;
    FileReader fileReader;

    MonthReportService(){
        reports = new ArrayList<>();
        fileReader = new FileReader();
    }

    ArrayList<MonthReport> getMonthReports(boolean refreshData){ //Сохранение отчётов
        if(reports.size() != 0 && !refreshData){
            return reports;
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
                report.records = new ArrayList<>();
                report.monthName = MonthNameHelper.monthNames.get(i); //получение имени месяца из хэш-таблицы NameHelper'a
                report.monthNumber = i;
                lines.remove(0);
                for(String item : lines){
                    var record = new MonthReportRecord(item);
                    report.records.add(record); //заполнение списка с отчётами
                }
                reports.add(report); //сохранение данных из отчётов
            }
        }
        return reports;
    }

    void populateMonthRecords() { //вывод информации из отчётов
        if (reports.size() == 0) {
            System.out.println("Отчёты отсутствуют.");
        } else {
            for (MonthReport report : reports) {
                System.out.println(report.monthName);
                var mostProfitable = getMostProfitableProduct(report);
                var mostExpensiveProduct = getMostExpensiveProduct(report);
                System.out.println("Самый прибыльный товар: " + mostProfitable.itemName + " " + mostProfitable.quantity * mostProfitable.unitPrice);
                System.out.println("Самая большая трата: " + mostExpensiveProduct.itemName + " " + mostExpensiveProduct.quantity * mostExpensiveProduct.unitPrice);
            }
        }
    }

    private MonthReportRecord getMostProfitableProduct(MonthReport report){ //получение самого прибыльного товара
        var profitableRecords = new ArrayList<MonthReportRecord>();

        for(MonthReportRecord record : report.records){
            if(!record.isExpense){
                profitableRecords.add(record);
            }
        }

        profitableRecords.sort(Comparator.comparing(record -> record.quantity * record.unitPrice));
        var mostProfitable = profitableRecords.get(profitableRecords.size() - 1);
        return mostProfitable;
    }

    MonthReportRecord getMostExpensiveProduct(MonthReport report){ //получение самого дорогого товара
        var expensiveRecords = new ArrayList<MonthReportRecord>();
        for(MonthReportRecord record : report.records){
            if(record.isExpense){
                expensiveRecords.add(record);
            }
        }

        expensiveRecords.sort(Comparator.comparing(record -> record.quantity * record.unitPrice));
        var mostProfitable = expensiveRecords.get(expensiveRecords.size() - 1);
        return mostProfitable;
    }
    Integer getMonthExpenses(MonthReport report){ //получение месячных затрат
        var monthExpenses = 0;
        for(MonthReportRecord record : report.records){
            if(record.isExpense)
                monthExpenses += record.quantity * record.unitPrice;
        }
        return monthExpenses;
    }

    Integer getMonthIncome(MonthReport report){ //получение месячной прибыли
        var monthExpenses = 0;
        for(MonthReportRecord record : report.records){
            if(!record.isExpense)
                monthExpenses += record.quantity * record.unitPrice;
        }
        return monthExpenses;
    }
}
