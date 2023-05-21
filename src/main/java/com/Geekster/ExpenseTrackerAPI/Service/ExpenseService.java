package com.Geekster.ExpenseTrackerAPI.Service;

import com.Geekster.ExpenseTrackerAPI.DTO.UpdateExpenseRequest;
import com.Geekster.ExpenseTrackerAPI.Model.Expense;
import com.Geekster.ExpenseTrackerAPI.Model.User;
import com.Geekster.ExpenseTrackerAPI.Repository.IExpenseRepository;
import com.Geekster.ExpenseTrackerAPI.Repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
public class ExpenseService {
    @Autowired
    private IExpenseRepository expenseRepository;
    @Autowired
    private IUserRepository userRepository;

    public Expense createExpense(Expense expense) {
        Long userId = expense.getUser().getId();
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()){
            expense.setUser(optionalUser.get());
        }else{
            throw new EntityNotFoundException("User with id : " + userId + " not found");
        }
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses(Long userId) {
        return expenseRepository.findAllById(Collections.singleton(userId));
    }

    public Expense updateExpense(Long userId, UpdateExpenseRequest updatedExpense) {
        Expense expense = expenseRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found"));

        expense.setTitle(updatedExpense.getTitle());
        expense.setDescription(updatedExpense.getDescription());
        expense.setPrice(updatedExpense.getPrice());
        expense.setDate(updatedExpense.getDate());
        expense.setTime(updatedExpense.getTime());

        return expenseRepository.save(expense);
    }

    @Transactional
    public void deleteExpense(Long userId,Long expenseId) {

        if (!expenseRepository.existsById(userId) && !expenseRepository.existsById(expenseId)) {
            throw new EntityNotFoundException("Expense not found");
        }
        expenseRepository.deleteByUserId(userId, expenseId);
    }

    public List<Expense> getExpensesByDate(LocalDate parsedDate) {
        return expenseRepository.findByDate(parsedDate);
    }

    public Map<LocalDate, Double> getMonthlyReport(User user, LocalDate monthDate) {
        LocalDate startOfMonth = monthDate.withDayOfMonth(1);
        LocalDate endOfMonth = monthDate.withDayOfMonth(monthDate.lengthOfMonth());
        Map<LocalDate, Double> expenseMap = new HashMap<>();
        List<Expense> expenses = expenseRepository.findByUserAndDateBetween(user, startOfMonth, endOfMonth);

        for (Expense expense : expenses) {
            LocalDate date = expense.getDate();
            double price = expense.getPrice();
            if (expenseMap.containsKey(date)) {
                double currentTotal = expenseMap.get(date);
                expenseMap.put(date, currentTotal + price);
            } else {
                expenseMap.put(date, price);
            }
        }
        return expenseMap;
    }

    public Map<LocalDate, Double> getWeeklyReport(User user, LocalDate startDate, LocalDate endDate) {
        List<Expense> expenses = expenseRepository.findByUserAndDateBetween(user, startDate, endDate);
        Map<LocalDate, Double> expenseMap = new HashMap<>();
        for (Expense expense : expenses) {
            LocalDate date = expense.getDate();
            Double price = expense.getPrice();
            if (expenseMap.containsKey(date)) {
                Double currentTotal = expenseMap.get(date);
                expenseMap.put(date, currentTotal + price);
            } else {
                expenseMap.put(date, price);
            }
        }
        return expenseMap;


    }

    public Double getTotalExpenditure(User user, LocalDate monthDate) {
        LocalDate startOfMonth = monthDate.withDayOfMonth(1);
        LocalDate endOfMonth = monthDate.withDayOfMonth(monthDate.lengthOfMonth());
        List<Expense> expenses = expenseRepository.findByUserAndDateBetween(user, startOfMonth, endOfMonth);

        double totalExpenditure = 0.0;
        for (Expense expense : expenses) {
            double price = expense.getPrice();
            totalExpenditure += price;
        }
        return totalExpenditure;
    }
}