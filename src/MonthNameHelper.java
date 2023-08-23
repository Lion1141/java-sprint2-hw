import java.util.HashMap;

public class MonthNameHelper {
    public static HashMap<Integer, String> MonthNames = new HashMap<>(){ //хэш-таблица для удобства вывода имён месяцев
        {  put(1, "Январь");  }
        {  put(2, "Февраль");  }
        {  put(3, "Март");  }
        {  put(4, "Апрель");  }
        {  put(5, "Май");  }
        {  put(6, "Июнь");  }
        {  put(7, "Июль");  }
        {  put(8, "Август");  }
        {  put(9, "Сентябрь");  }
        {  put(10, "Октябрь");  }
        {  put(11, "Ноябрь");  }
        {  put(12, "Декабрь");  }
    };
}
