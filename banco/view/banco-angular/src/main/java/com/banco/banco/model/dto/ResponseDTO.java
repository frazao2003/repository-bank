package com.banco.banco.model.dto;

import com.banco.banco.model.entity.Conta;
import lombok.Data;

@Data
public class ResponseDTO{

    private Conta conta;
    private String token;
}
