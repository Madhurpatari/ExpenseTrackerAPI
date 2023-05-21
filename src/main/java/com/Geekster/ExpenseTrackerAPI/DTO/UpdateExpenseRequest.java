package com.Geekster.ExpenseTrackerAPI.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateExpenseRequest {
    private String title;
    private String description;
    private Double price;
    private LocalDate date;
    private LocalTime time;
}
