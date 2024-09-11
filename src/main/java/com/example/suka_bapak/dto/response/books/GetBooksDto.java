package com.example.suka_bapak.dto.response.books;

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

public class GetBooksDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("author")
    private String author;

    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("available_copies")
    private int available_copies;

    @JsonProperty("created_at")
    private LocalDate created_at;

    @JsonProperty("updated_at")
    private LocalDate updated_at;
}
