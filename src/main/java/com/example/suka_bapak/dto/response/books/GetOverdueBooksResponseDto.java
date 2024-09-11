package com.example.suka_bapak.dto.response.books;

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
public class GetOverdueBooksResponseDto {
    @JsonProperty("book_title")
    private String bookTitle;

    @JsonProperty("patron_name")
    private String patronName;

    @JsonProperty("due_date")
    private LocalDate dueDate;

    @JsonProperty("days_overdue")
    private Integer daysOverdue;

    @JsonProperty("fine")
    private Double fine;

}
