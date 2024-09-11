package com.example.suka_bapak.dto.response.patrons;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GetPatronDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("membership_type")
    private String membership_type;

    @JsonProperty("created_at")
    private LocalDate created_at;

    @JsonProperty("updated_at")
    private LocalDate updated_at;

}
