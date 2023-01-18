package com.kluevja.bankappweb.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Account sender;
    @ManyToOne
    private Account getter;
    private double valueOfPayment;
    private Date dateOfPayment;
    private TransactionType transactionType;
}
