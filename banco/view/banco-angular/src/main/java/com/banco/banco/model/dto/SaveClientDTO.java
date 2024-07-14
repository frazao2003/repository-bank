package com.banco.banco.model.dto;

import lombok.Data;

@Data
public class SaveClientDTO {

    private String nome;
    private String cpf;
    private String email;
    private String observacoes;
}
