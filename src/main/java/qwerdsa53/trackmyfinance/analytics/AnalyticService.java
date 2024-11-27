package qwerdsa53.trackmyfinance.analytics;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import qwerdsa53.trackmyfinance.transaction.CategoryType;
import qwerdsa53.trackmyfinance.transaction.TransactionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnalyticService {

    private final TransactionRepository transactionRepository;

    // Общая сумма транзакций пользователя
    public Double getTotalAmountByUser(Long userId) {
        return transactionRepository.getTotalAmountByUser(userId);
    }

    // Количество транзакций за период
    public Long getTransactionCountByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactionRepository.getTransactionCountByDateRange(startDate, endDate);
    }

    // Общая сумма по категориям (возвращаем DTO)
    public List<CategoryStatisticsDTO> getTotalAmountByCategory(Long userId) {
        return transactionRepository.getTotalAmountByCategory(userId)
                .stream()
                .map(result -> new CategoryStatisticsDTO((CategoryType) result[0], (int) result[1], null))
                .collect(Collectors.toList());
    }

    // Все транзакции пользователя за период
    public List<Object[]> getTransactionsByUserAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return transactionRepository.getTransactionsByUserAndDateRange(userId, startDate, endDate);
    }

    // Сумма расходов пользователя за неделю/месяц
    public Double getTotalExpensesByUserAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        Double totalExpenses = transactionRepository.getTotalExpensesByUserAndDateRange(userId, startDate, endDate);
        return totalExpenses != null ? totalExpenses : 0.0;
    }

    // Статистика доходов/расходов по категориям за период
    public List<CategoryStatisticsDTO> getCategoryStatisticsByDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return transactionRepository.getCategoryStatisticsByDateRange(userId, startDate, endDate)
                .stream()
                .map(result -> new CategoryStatisticsDTO(
                        (CategoryType) result[0],  // Category
                        (int) result[1],  // Total Income
                        (Integer) result[2]   // Total Expense
                ))
                .collect(Collectors.toList());
    }
}
