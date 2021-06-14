package com.receiptfinanceapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "userapp")
@Data
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String username;

    private String password;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private List<ReceiptData> receiptDataList;
}
