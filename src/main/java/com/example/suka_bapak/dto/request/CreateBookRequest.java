package com.example.suka_bapak.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CreateBookRequest {
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
