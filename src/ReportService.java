public class ReportService {
    private YearReportService yearReportService;
    private MonthReportService monthReportService;

    public ReportService(){
        yearReportService = new YearReportService();
        monthReportService = new MonthReportService();
    }

    public void GetYearReport(String year){ //сохранение годового отчёта
        yearReportService.GetYearReport(year, true);
        System.out.println("Годовой отчёт загружен.");
    }

    public void GetMonthReports(){ //сохранение месячных отчётов
        monthReportService.GetMonthReports(true);
        System.out.println("Информация обо всех месячных отчётах загружена.");
    }

    public void PopulateYearReport(String year){ //вывод информации из годового отчёта
        yearReportService.PopulateYearReport(year);

    }

    public void PopulateMonthReport(){ //вывод информации обо всех месячных отчётах
        monthReportService.PopulateMonthRecords();
    }

    public void CompareReports(String year){ //сравнение месячных отчётов и годового
        var yearReport = yearReportService.GetYearReport(year, false);
        var monthlyReports = monthReportService.GetMonthReports(false);
        boolean issues = false;
        for(MonthReport monthReport : monthlyReports){
            var yearlyMonthRecord = yearReport.records.get(monthReport.MonthNumber);
            var monthReportIncome = monthReportService.GetMonthIncome(monthReport);
            var monthReportExpense = monthReportService.GetMonthExpenses(monthReport);
            if(monthReportExpense.equals(yearlyMonthRecord.expenseAmount) ||
                    monthReportIncome.equals(yearlyMonthRecord.profitAmount))
            {
                System.out.println("Не совпадают данные за месяц: " + monthReport.MonthName);
                issues = true;
            }
        }

        if(!issues){
            System.out.println("Данные из отчётов соответствуют друг другу.");
        }
    }
}
