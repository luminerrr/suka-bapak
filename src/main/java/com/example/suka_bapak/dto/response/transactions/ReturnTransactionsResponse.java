package com.example.suka_bapak.dto.response.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnTransactionsResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("fine")
    private Double fine;
}
