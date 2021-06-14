package com.receiptfinanceapi.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "rate")
@Data
public class Rate {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_commercial_year")
    private Boolean isCommercialYear;

    @Column(name = "is_nominal ")
    private Boolean isNominal ;

    @Column(name = "percentage ")
    private Boolean percentage;

    @Column(name = "discount_date ")
    private Date discountDate;

    @Column(name = "id_rate_term", nullable = false)
    private Long idRateTerm;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rate_term", referencedColumnName = "id", insertable = false, updatable = false, nullable = false)
    private RateTerm rateTerm;

    @Column(name = "id_compounding_period", nullable = false)
    private Long idCompoundingPeriod;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_compounding_period", referencedColumnName = "id", insertable = false, updatable = false, nullable = true)
    private CompoundingPeriod compoundingPeriod;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rate", fetch = FetchType.EAGER)
    private List<Expense> expenses;

}
