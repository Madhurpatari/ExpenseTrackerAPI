package com.Geekster.ExpenseTrackerAPI.Repository;

import com.Geekster.ExpenseTrackerAPI.Model.Expense;
import com.Geekster.ExpenseTrackerAPI.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface IExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByUserId(Long userId);

    List<Expense> findByDate(LocalDate parsedDate);

    List<Expense> findByUserAndDateBetween(User user, LocalDate startOfMonth, LocalDate endOfMonth);

    @Modifying
    @Query(value = "DELETE FROM Expense WHERE user_id = :userId AND expense_id = :expenseId", nativeQuery = true)
    void deleteByUserId(Long userId,Long expenseId);
}
