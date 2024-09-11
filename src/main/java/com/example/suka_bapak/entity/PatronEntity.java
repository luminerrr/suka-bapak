package com.example.suka_bapak.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@Table(name = "patrons")
public class PatronEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @JsonProperty("name")
    private String name;

    @Column(name = "email", nullable = false)
    @JsonProperty("email")
    private String email;

    @Column(name = "membership_type", nullable = false)
    @JsonProperty("membership_type")
    private String membership_type;

    @Column(name = "created_at", nullable = false)
    @JsonProperty("created_at")
    private LocalDate created_at;

    @Column(name = "updated_at", nullable = false)
    @JsonProperty("updated_at")
    private LocalDate updated_at;
}
