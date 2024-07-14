package com.banco.banco.model.dto;

import lombok.Data;

import java.util.Calendar;

@Data
public class ExtratoDTO  {

    private String nomeOrigem;
    private String agenciaOrigem;
    private String numeroOrigem;
    private String cpfMascaradoOrigem;
    private Double Valor;
    private Calendar data;
    private String nomeDestino;
    private String agenciaDestino;
    private String numeroDestino;
    private String cpfMascaradoDestino;
    private String tipoExtrato;



}
