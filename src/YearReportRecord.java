public class YearReportRecord {
    Integer monthNumber;
    String monthName;
    Integer amount;
    boolean isExpense;
    Integer expenseAmount;
    Integer profitAmount;

    YearReportRecord(String line){ //конструктор для обработки строк из файла отчёта
        String[] lineContents = line.split(",");
        this.monthNumber = Integer.parseInt(lineContents[0]);
        this.monthName = MonthNameHelper.MonthNames.get(this.monthNumber); //получение имени месяца из хэш-таблицы NameHelper'a
        this.amount = Integer.parseInt(lineContents[1]);
        this.isExpense = lineContents[2].equals("true");
        if(this.isExpense){ //получение значений прибыли из списка
            this.expenseAmount = this.amount;
            this.profitAmount = 0;
        }
        else {
            this.expenseAmount = 0;
            this.profitAmount = this.amount;
        }
    }
}
