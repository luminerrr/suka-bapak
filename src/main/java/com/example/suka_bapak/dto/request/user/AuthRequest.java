package com.example.suka_bapak.dto.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;
}
