package com.banco.banco.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="Users")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "password")
    private  String password;

    @Column(name = "cpf")
    private String cpf;

    @OneToOne
    @JoinColumn(name = "fk_conta_id")
    private Conta conta;
}
