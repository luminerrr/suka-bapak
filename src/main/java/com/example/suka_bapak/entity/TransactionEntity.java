package com.example.suka_bapak.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "transactions")
@Data

public class TransactionEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // patron id
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patron_id", referencedColumnName = "id")
    private PatronEntity patron;

    // book_id
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private BookEntity book;

    // borrow_date
    @Column(name = "borrow_date")
    @JsonProperty("borrow_date")
    private Date borrowDate;
    
    // due date
    @Column(name = "due_date")
    @JsonProperty("due_date")
    private Date dueDate;
    
    // return date
    @Column(name = "return_date")
    @JsonProperty("return_date")
    private Date returnDate;

    // fine
    @Column(name = "fine")
    @JsonProperty("fine")
    private Double fine;
}
