import java.util.ArrayList;
import java.util.HashMap;

public class YearReportService {
    HashMap<String, YearReport> Reports;
    FileReader fileReader;

    YearReportService(){
        Reports = new HashMap<>();
        fileReader = new FileReader();
    }

    YearReport GetYearReport(String year, boolean refreshData){ //сохранение годового отчёта

        if(Reports.containsKey(year) && !refreshData){
            return Reports.get(year);
        }
        ArrayList<String> lines = fileReader.readFileContents("y."+ year + ".csv");
        if (lines.size() != 0) {
            lines.remove(0);
            var report = new YearReport();
            report.records = new HashMap<>();
            report.year = year;
            for(String item : lines){
                var record = new YearReportRecord(item);
                if(report.records.containsKey(record.monthNumber)){
                    var existingRecord = report.records.get(record.monthNumber);
                    if(record.isExpense){ //сохранение значения прибыли
                        existingRecord.expenseAmount += record.expenseAmount;
                    }
                    else {
                        existingRecord.profitAmount += record.profitAmount;
                    }
                }
                else {
                    report.records.put(record.monthNumber, record);
                }
            }
            Reports.put(year, report);
            return report;
        }

        return null;
    }

    void PopulateYearReport(String year) { //вывод информации из годового отчёта
        if (!Reports.containsKey(year)) {
            System.out.println("Отчёт отсутствует");
            return;
        } else {
            var report = Reports.get(year);

            System.out.println(report.year);
            System.out.println("Прибыль по каждому месяцу:");

            for (int i = 1; i <= 12; i++) {
                if (report.records.containsKey(i)) {
                    var record = report.records.get(i);
                    var profit = record.profitAmount - record.expenseAmount;
                    System.out.println(record.monthName + ": " + profit + " рублей.");
                }
            }

            System.out.println("Средний расход за все имеющиеся операции в году: " + GetAverageExpense(report) + " рублей.");
            System.out.println("Средний доход за все имеющиеся операции в году: " + GetAverageIncome(report) + " рублей.");
        }
    }

    Integer GetAverageIncome(YearReport report){ //получение величины доходов за год
        var income = 0;
        for(YearReportRecord record : report.records.values()){
            income += record.profitAmount;
        }

        return income / report.records.size();
    }
    Integer GetAverageExpense(YearReport report){ //получение величины расходов за год
        var income = 0;
        for(YearReportRecord record : report.records.values()){
            income += record.expenseAmount;
        }

        return income / report.records.size();
    }


}
