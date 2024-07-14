package com.banco.banco.model.dto;

import lombok.Data;

;

@Data
public class ClienteDTO  {

    private Long id;
    private String nome;
    private String CpfMascarado;
}
