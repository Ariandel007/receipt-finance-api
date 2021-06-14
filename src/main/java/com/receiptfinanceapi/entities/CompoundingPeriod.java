package com.receiptfinanceapi.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "compounding_period")
@Data
public class CompoundingPeriod {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "number_days")
    private Integer numberDays;
}
