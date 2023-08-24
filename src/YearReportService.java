import java.util.ArrayList;
import java.util.HashMap;

public class YearReportService {
    HashMap<String, YearReport> reports;
    FileReader fileReader;

    YearReportService(){
        reports = new HashMap<>();
        fileReader = new FileReader();
    }

    YearReport getYearReport(String year){ //сохранение годового отчёта
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
            reports.put(year, report);
            return report;
        }

        return null;
    }

    void populateYearReport(String year) { //вывод информации из годового отчёта
        if (!reports.containsKey(year)) {
            System.out.println("Отчёт отсутствует");
            return;
        } else {
            var report = reports.get(year);

            System.out.println(report.year);
            System.out.println("Прибыль по каждому месяцу:");

            for (int i = 1; i <= 12; i++) {
                if (report.records.containsKey(i)) {
                    var record = report.records.get(i);
                    var profit = record.profitAmount - record.expenseAmount;
                    System.out.println(record.monthName + ": " + profit + " рублей.");
                }
            }

            System.out.println("Средний расход за все имеющиеся операции в году: " + getAverageExpense(report) + " рублей.");
            System.out.println("Средний доход за все имеющиеся операции в году: " + getAverageIncome(report) + " рублей.");
        }
    }

    Integer getAverageIncome(YearReport report){ //получение величины доходов за год
        var income = 0;
        for(YearReportRecord record : report.records.values()){
            income += record.profitAmount;
        }

        return income / report.records.size();
    }
    Integer getAverageExpense(YearReport report){ //получение величины расходов за год
        var income = 0;
        for(YearReportRecord record : report.records.values()){
            income += record.expenseAmount;
        }

        return income / report.records.size();
    }


}
