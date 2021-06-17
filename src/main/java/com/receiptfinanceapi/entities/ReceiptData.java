package com.receiptfinanceapi.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "receipt_data")
@Data
public class ReceiptData {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_of_issue")
    private Date dateOfIssue;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "total_value", precision = 10, scale = 2)
    private Double totalValue;

    @Column(precision = 10, scale = 2)
    private Double retainage;

    @Column(name = "is_dolar")
    private Boolean isDolar;

    private String name;

    private String description;

    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "id_user", nullable = false)
    private Long idUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @Column(name = "id_rate", nullable = false)
    private Long idRate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rate", referencedColumnName = "id", insertable = false, updatable = false, nullable = false)
    private Rate rate;

}
