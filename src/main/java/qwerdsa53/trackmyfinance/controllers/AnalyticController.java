package qwerdsa53.trackmyfinance.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import qwerdsa53.trackmyfinance.analytics.AnalyticService;

import java.time.LocalDate;
import java.util.List;
import qwerdsa53.trackmyfinance.analytics.CategoryStatisticsDTO;

@RestController
@RequestMapping("api/v1/analytic")
@AllArgsConstructor
public class AnalyticController {

    private final AnalyticService analyticService;

    // Общая сумма транзакций пользователя
    @GetMapping("/total/{userId}")
    public Double getTotalAmountByUser(@PathVariable Long userId) {
        return analyticService.getTotalAmountByUser(userId);
    }

    // Количество транзакций за период
    @GetMapping("/count")
    public Long getTransactionCountByDateRange(@RequestParam String startDate,
                                               @RequestParam String endDate) {
        return analyticService.getTransactionCountByDateRange(
                LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    // Общая сумма по категориям
    //*TODO исправить проблему с разрешением
    @GetMapping("/category/{userId}")
    public List<CategoryStatisticsDTO> getTotalAmountByCategory(@PathVariable Long userId) {
        return analyticService.getTotalAmountByCategory(userId);
    }


    // Транзакции пользователя за период
    @GetMapping("/transactions/{userId}")
    public List<Object[]> getTransactionsByUserAndDateRange(
            @PathVariable Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return analyticService.getTransactionsByUserAndDateRange(
                userId, LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    // Сумма расходов пользователя за неделю/месяц
    @GetMapping("/expenses/{userId}")
    public Double getTotalExpensesByDateRange(
            @PathVariable Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return analyticService.getTotalExpensesByUserAndDateRange(
                userId, LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    // Статистика доходов/расходов по категориям за период
    @GetMapping("/statistics/{userId}")
    public List<CategoryStatisticsDTO> getCategoryStatisticsByDateRange(
            @PathVariable Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return analyticService.getCategoryStatisticsByDateRange(
                userId, LocalDate.parse(startDate), LocalDate.parse(endDate));
    }
}
