public class ReportService {
    private YearReportService yearReportService;
    private MonthReportService monthReportService;

    public ReportService(){
        yearReportService = new YearReportService();
        monthReportService = new MonthReportService();
    }

    public void getYearReport(String year){ //сохранение годового отчёта
        yearReportService.getYearReport(year, true);
        System.out.println("Годовой отчёт загружен.");
    }

    public void getMonthReports(){ //сохранение месячных отчётов
        monthReportService.getMonthReports(true);
        System.out.println("Информация обо всех месячных отчётах загружена.");
    }

    public void populateYearReport(String year){ //вывод информации из годового отчёта
        yearReportService.populateYearReport(year);

    }

    public void populateMonthReport(){ //вывод информации обо всех месячных отчётах
        monthReportService.populateMonthRecords();
    }

    public void compareReports(String year){ //сравнение месячных отчётов и годового
        var yearReport = yearReportService.getYearReport(year, false);
        var monthlyReports = monthReportService.getMonthReports(false);
        boolean issues = false;
        for(MonthReport monthReport : monthlyReports){
            var yearlyMonthRecord = yearReport.records.get(monthReport.monthNumber);
            var monthReportIncome = monthReportService.getMonthIncome(monthReport);
            var monthReportExpense = monthReportService.getMonthExpenses(monthReport);
            if(monthReportExpense.equals(yearlyMonthRecord.expenseAmount) ||
                    monthReportIncome.equals(yearlyMonthRecord.profitAmount))
            {
                System.out.println("Не совпадают данные за месяц: " + monthReport.monthName);
                issues = true;
            }
        }

        if(!issues){
            System.out.println("Данные из отчётов соответствуют друг другу.");
        }
    }
}
