package com.banco.banco.model.entity;

import java.util.Calendar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "extratos_joao")
@Data
@EqualsAndHashCode
public class Extrato  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_conta_destino_id")
    private Conta contaDestino;

    @ManyToOne
    @JoinColumn(name = "fk_conta_origem_id")
    private Conta contaOrigem;

    @Column
    private Double valor;

    @Column
    private Calendar data;

    @Column
    private String tipoExtrato;
}
