import java.util.Objects;

public class ReportService {
    private YearReportService yearReportService;
    private MonthReportService monthReportService;

    public ReportService() {
        yearReportService = new YearReportService();
        monthReportService = new MonthReportService();
    }

    public void getYearReport(String year) { //сохранение годового отчёта
        yearReportService.getYearReport(year);
        System.out.println("Годовой отчёт загружен.");
    }

    public void getMonthReports() { //сохранение месячных отчётов
        monthReportService.getMonthReports();
        System.out.println("Информация обо всех месячных отчётах загружена.");
    }

    public void populateYearReport(String year) { //вывод информации из годового отчёта
        yearReportService.populateYearReport(year);

    }

    public void populateMonthReport() { //вывод информации обо всех месячных отчётах
        monthReportService.populateMonthRecords();
    }

    public void compareReports(String year) { //сравнение месячных отчётов и годового
        boolean issues = false;
        boolean yearEmptyness = yearReportService.reports.isEmpty();
        boolean monthEmptyness = monthReportService.reports.isEmpty();
        if (yearEmptyness && monthEmptyness) {
            System.out.println("Отсутствует годовой и месячные отчёты.");
        } else if (yearEmptyness) {
            System.out.println("Отсутствует годовой отчёт.");
        } else if (monthEmptyness) {
            System.out.println("Отсутствуют месячные отчёты.");
        } else {
            for (MonthReport monthReport : monthReportService.reports) {
                var yearlyMonthRecord = YearReport.records.get(monthReport.monthNumber);
                var monthReportIncome = monthReportService.getMonthIncome(monthReport);
                var monthReportExpense = monthReportService.getMonthExpenses(monthReport);
                if (!Objects.equals(yearlyMonthRecord.expenseAmount, monthReportExpense) ||
                        !Objects.equals(yearlyMonthRecord.profitAmount, monthReportIncome)) {
                    System.out.println("Не совпадают данные за месяц: " + monthReport.monthName);
                    issues = true;
                }
            }
            if (!issues) {
                System.out.println("Данные из отчётов соответствуют друг другу.");
            }
        }
    }
}
