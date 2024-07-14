package com.banco.banco.controller.rest;

import java.util.List;

import com.banco.banco.model.dto.DepositoDTO;
import com.banco.banco.model.dto.SaqueDTO;
import com.banco.banco.model.dto.TransferenciaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banco.banco.model.dto.ContaClienteDTO;
import com.banco.banco.model.entity.Conta;
import com.banco.banco.model.service.ContaService;

@RestController
@RequestMapping("rest/contas")
public class ContaRest{

    @Autowired
    private ContaService contaBancariaService;

    @GetMapping(value = "/buscarContasDoCliente/{cpf}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<ContaClienteDTO>> buscarContasDoCliente(@PathVariable String cpf){
        List<ContaClienteDTO> lista = contaBancariaService.listarContasDoCliente(cpf);
        if (lista == null || lista.isEmpty()) {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarSaldo/{agencia}/{numeroConta}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Double> consultarSaldo (@PathVariable String agencia, @PathVariable String numeroConta){
        Conta conta = contaBancariaService.carregarConta(agencia, numeroConta);
        return new ResponseEntity<>(conta.getSaldo(), HttpStatus.OK);
    }

    @PutMapping(value = "/deposito")
    public @ResponseBody ResponseEntity<Void> depositar(@RequestBody DepositoDTO depositoDTO){
        contaBancariaService.depositar(depositoDTO);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    @PutMapping(value = "/sacar", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Void> sacar (@RequestBody SaqueDTO saqueDTO){
        contaBancariaService.sacar(saqueDTO);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    @PutMapping(value = "/transferencia", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Void> transferir (@RequestBody TransferenciaDTO transferenciaDTO){
        contaBancariaService.transferir(transferenciaDTO);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
