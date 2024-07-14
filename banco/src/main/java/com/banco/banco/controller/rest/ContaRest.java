package com.banco.banco.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PutMapping(value = "/deposito/{agencia}/{numero}/{valor}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Void> depositar(@PathVariable String agencia, @PathVariable String numero, @PathVariable Double valor ){
        contaBancariaService.depositar(agencia, numero, valor);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    @PutMapping(value = "/sacar/{agencia}/{numero}/{valor}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Void> sacar (@PathVariable String agencia, @PathVariable String numero, @PathVariable Double valor){
        contaBancariaService.sacar(agencia, numero, valor);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    @PutMapping(value = "/transferencia/{agenciaOrigem}/{numeroOrigem}/{agenciaDestino}/{numeroDestino}/{valor}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Void> transferir (@PathVariable String agenciaOrigem, @PathVariable String numeroOrigem,@PathVariable String agenciaDestino, @PathVariable String numeroDestino, @PathVariable Double valor){
        contaBancariaService.transferir(agenciaOrigem, numeroOrigem, agenciaDestino, numeroDestino, valor);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
