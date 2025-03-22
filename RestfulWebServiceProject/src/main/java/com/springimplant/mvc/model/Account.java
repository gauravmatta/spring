package com.springimplant.mvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;


@Entity
@Table(name = "account_details")
public class Account implements Serializable {

    public static final long serialVersionUID = 1L;
    @Id
    @Column(name = "student_id")
    private String id;
    @Column(name = "account_nymber")
    private String number;
    @Column(name = "balance")
    private BigDecimal balance;

    public Account(String id, String number, BigDecimal balance) {
        super();
        this.id = id;
        this.number = number;
        this.balance = balance;
    }

    public Account() {
        super();
    }

    @Override
    public String toString() {
        return "Account [id=" + id + ", name=" + number + ", balance=" + balance + "]";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
