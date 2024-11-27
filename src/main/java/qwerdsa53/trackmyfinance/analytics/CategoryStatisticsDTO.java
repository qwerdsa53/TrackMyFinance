package qwerdsa53.trackmyfinance.analytics;

import lombok.AllArgsConstructor;
import qwerdsa53.trackmyfinance.transaction.CategoryType;

@AllArgsConstructor
public class CategoryStatisticsDTO {
    public CategoryType category;
    public int totalIncome;
    public Integer totalExpense;


    // Конструктор, геттеры и сеттеры
}
