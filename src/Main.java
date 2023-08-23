import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ReportService reportService = new ReportService();

        while (true) {
            printMenu(); // Вывод доступных пунктов меню в консоль
            int userInput = scanner.nextInt();

            switch (userInput){
                case 1:
                    reportService.getMonthReports(); // Получение месячных отчётов
                    System.out.println();
                    break;
                case 2:
                    reportService.getYearReport("2021"); //Получение отчётов за 2021 год
                    System.out.println();
                    break;
                case 3:
                    reportService.compareReports("2021"); //Сравнение отчётов
                    System.out.println();
                    break;
                case 4:
                    reportService.populateMonthReport(); //Вывод информации о месячных отчётах
                    System.out.println();
                    break;
                case 5:
                    reportService.populateYearReport("2021"); //Вывод информации о годовых отчётах
                    System.out.println();
                    break;
                case 0:
                    System.out.println("Завершение программы.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Извините, такой команды пока нет.");
                    break;
            }
        }
    }
    static void printMenu(){
        System.out.println("Введите команду:");
        System.out.println("1 - Считать все месячные отчёты.");
        System.out.println("2 - Считать годовой отчёт.");
        System.out.println("3 - Сверить отчёты.");
        System.out.println("4 - Вывести информацию обо всех месячных отчётах.");
        System.out.println("5 - Вывести информацию о годовом отчёте.");
        System.out.println("0 - Выйти из программы.");
    }
}

