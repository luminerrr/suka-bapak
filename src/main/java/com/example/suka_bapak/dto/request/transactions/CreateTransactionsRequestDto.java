package com.example.suka_bapak.dto.request.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionsRequestDto {
    @JsonProperty("patron_id")
    private Long patronId;

    @JsonProperty("book_id")
    private Long bookId;
}
