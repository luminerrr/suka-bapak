package com.example.suka_bapak.dto.response.transactions;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionsResponseDto {
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("due_date")
    private LocalDate dueDate;
}
