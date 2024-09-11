package com.example.suka_bapak.dto.response.patrons;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPatronTransactionHistoryResponseDto {
    @JsonProperty("book_title")
    private String bookTitle;

    @JsonProperty("borrowed_date")
    private LocalDate borrowDate;

    @JsonProperty("returned_date")
    private LocalDate returneDate;

    @JsonProperty("fine")
    private Double fine;
}
