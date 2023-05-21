package com.Geekster.ExpenseTrackerAPI.Controller;

import com.Geekster.ExpenseTrackerAPI.DTO.Month;
import com.Geekster.ExpenseTrackerAPI.DTO.UpdateExpenseRequest;
import com.Geekster.ExpenseTrackerAPI.Model.Expense;
import com.Geekster.ExpenseTrackerAPI.Model.User;
import com.Geekster.ExpenseTrackerAPI.Repository.IUserRepository;
import com.Geekster.ExpenseTrackerAPI.Service.AuthenticationService;
import com.Geekster.ExpenseTrackerAPI.Service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/expense")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private IUserRepository userRepository;

    //add expense by authenticate yourself
    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestParam String email , @RequestParam String token,@RequestBody Expense expense) {
        HttpStatus status;
        Expense createdExpense = null;
        if(authenticationService.authenticate(email,token)) {
            createdExpense = expenseService.createExpense(expense);
            status = HttpStatus.OK;
        }else {
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<>(createdExpense , status);
    }

    // get all expense corresponding to a particular user by a successful authentication
    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses(@RequestParam String email , @RequestParam String token) {
        HttpStatus status;
        List<Expense> expenses = null;
        if(authenticationService.authenticate(email,token)) {
            User user  = userRepository.findFirstByEmail(email);
            if(user != null){
                expenses = expenseService.getAllExpenses(user.getId());
                status = HttpStatus.OK;
            }else{
                throw new IllegalStateException("Invalid User..!!");
            }
        }else {
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<>(expenses , status);
    }

    //update expense by authenticate yourself
    @PutMapping
    public ResponseEntity<Expense> updateExpense(@RequestParam String email , @RequestParam String token, @RequestBody UpdateExpenseRequest updatedExpense) {
        HttpStatus status;
        Expense expense =null;
        if(authenticationService.authenticate(email,token)) {
            User user  = userRepository.findFirstByEmail(email);
            if(user != null){
                expense = expenseService.updateExpense(user.getId(),updatedExpense);
                status = HttpStatus.OK;
            }else{
                throw new IllegalStateException("Invalid User..!!");
            }
        }else {
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<>(expense , status);
    }

    //Delete expense by an expense id and  authenticate user
    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long expenseId,@RequestParam String email , @RequestParam String token) {
        HttpStatus status;
        Expense expense =null;
        if(authenticationService.authenticate(email,token)) {
            User user  = userRepository.findFirstByEmail(email);
            if(user != null){
                expenseService.deleteExpense(user.getId(),expenseId);
                status = HttpStatus.NO_CONTENT;
            }else{
                throw new IllegalStateException("Invalid User..!!");
            }
        }else {
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<>(status);
    }

    //Get expense for a particular date
    @GetMapping("/date")
    public ResponseEntity<List<Expense>> getExpensesByDate(@RequestParam String email , @RequestParam String token, @RequestParam String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        HttpStatus status;
        List<Expense> expenses = null;
        if(authenticationService.authenticate(email,token)) {
            User user  = userRepository.findFirstByEmail(email);
            if(user != null){
                expenses = expenseService.getExpensesByDate(parsedDate);;
                status = HttpStatus.OK;
            }else{
                throw new IllegalStateException("Invalid User..!!");
            }
        }else {
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<>(expenses , status);
    }

    //get your monthly expense report here bro! ,but first authenticate yourself, are you even exist?
    @GetMapping("/report/monthly")
    public ResponseEntity<Map<LocalDate, Double>> generateMonthlyReport(
            @RequestParam String email ,
            @RequestParam String token,
            @RequestParam int year,
            @RequestParam Month month
    ) {
        Map<LocalDate, Double> report = null;
        HttpStatus status;

        if(authenticationService.authenticate(email,token)) {
            User user  = userRepository.findFirstByEmail(email);
            LocalDate monthDate = LocalDate.of(year, month.ordinal(), 1);
            if(user != null){
                report = expenseService.getMonthlyReport(user, monthDate);
                status = HttpStatus.OK;
            }else{
                throw new IllegalStateException("Invalid User..!!");
            }
        }else {
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<>(report , status);
    }

    //get your weekly expense report here bro! ,but wait, first authenticate yourself, are you even exist?
    @GetMapping("/report/weekly")
    public ResponseEntity<Map<LocalDate, Double>> generateWeeklyReport(
            @RequestParam String email ,
            @RequestParam String token,
            @RequestParam int year,
            @RequestParam Month month,
            @RequestParam int startDay,
            @RequestParam int endDay
    ) {
        Map<LocalDate, Double> report = null;
        HttpStatus status;

        if(authenticationService.authenticate(email,token)) {
            User user  = userRepository.findFirstByEmail(email);
            LocalDate startDate = LocalDate.of(year, month.ordinal(), startDay);
            LocalDate endDate = LocalDate.of(year, month.ordinal(), endDay);
            if(user != null){
                report = expenseService.getWeeklyReport(user, startDate, endDate);
                status = HttpStatus.OK;
            }else{
                throw new IllegalStateException("Invalid User..!!");
            }
        }else {
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<>(report , status);
    }

    //get your whole month total expense here bro! ,but wait, first authenticate yourself, are you even exist?
    @GetMapping("/totalExpenditure")
    public ResponseEntity<Double> getTotalExpenditure(
            @RequestParam String email ,
            @RequestParam String token,
            @RequestParam("year") int year,
            @RequestParam("month") Month month
    ) {
        Double totalExpenditure = null;
        HttpStatus status;
        if (authenticationService.authenticate(email, token)) {
            User user = userRepository.findFirstByEmail(email);
            LocalDate monthDate = LocalDate.of(year, month.ordinal(), 1);
            if (user != null) {
                totalExpenditure = expenseService.getTotalExpenditure(user, monthDate);
                status = HttpStatus.OK;
            } else {
                throw new IllegalStateException("Invalid User..!!");
            }
        } else {
            status = HttpStatus.FORBIDDEN;
        }

        return new ResponseEntity<>(totalExpenditure,status);
    }
}
