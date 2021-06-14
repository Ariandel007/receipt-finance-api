package com.receiptfinanceapi.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "rate_term")
@Data
public class RateTerm {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "number_days")
    private Integer numberDays;

}
