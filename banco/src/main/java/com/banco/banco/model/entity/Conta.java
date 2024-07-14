package com.banco.banco.model.entity;
import jakarta.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "contas_bancarias_joao")
@Data
@EqualsAndHashCode
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 4)
    private String agencia;

    @Column(length = 6)
    private String numero;

    @Column
    private double saldo;

    @OneToOne
    @JoinColumn(name = "fk_cliente_id")
    private Cliente cliente;

}
