package entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@Table(name = "books")

public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonProperty("title")
    private String title;

    @Column(nullable = false)
    @JsonProperty("author")
    private String author;

    @Column(nullable = false)
    @JsonProperty("isbn")
    private String isbn;

    @Column(nullable = false)
    @JsonProperty("quantity")
    private int quantity;

    @Column(nullable = false)
    @JsonProperty("available_copies")
    private int available_copies;

    @Column(nullable = false)
    @JsonProperty("created_at")
    private LocalDate created_at;

    @Column(nullable = false)
    @JsonProperty("updated_at")
    private LocalDate updated_at;

}
