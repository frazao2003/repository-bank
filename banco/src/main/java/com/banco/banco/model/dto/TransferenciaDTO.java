package com.banco.banco.model.dto;

import lombok.Data;

@Data
public class TransferenciaDTO {

    private String agenciaOrigem;
    private String numeroOrigem;
    private Double Valor;
    private String agenciaDestino;
    private String numeroDestino;
}
