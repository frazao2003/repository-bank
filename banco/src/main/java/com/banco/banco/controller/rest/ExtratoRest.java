package com.banco.banco.controller.rest;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.banco.banco.model.dto.ExtratoDTO;
import com.banco.banco.model.entity.Extrato;
import com.banco.banco.model.service.ExtratoService;

@RestController
@RequestMapping("rest/extratos")
public class ExtratoRest{

    @Autowired
    private ExtratoService extratoService;

    @GetMapping(value = "/consultarExtratoDeposito/{agencia}{numero}{dataInicio}{dataFim}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<ExtratoDTO>> consultarExtratoDeposito(@PathVariable  String agencia, @PathVariable String numero,@PathVariable Calendar dataInicio, @PathVariable Calendar dataFim){
        List<ExtratoDTO> lista = extratoService.consultarExtratosDeposito(agencia,  numero, dataInicio, dataFim);
        if (lista == null || lista.isEmpty()) {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarExtratoSaque/{agencia}{numero}{dataInicio}{dataFim}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<ExtratoDTO>> consultarExtratoSaque(@PathVariable  String agencia, @PathVariable String numero,@PathVariable Calendar dataInicio, @PathVariable Calendar dataFim){
        List<ExtratoDTO> lista = extratoService.consultarExtratosSaque(agencia,  numero, dataInicio, dataFim);
        if (lista == null || lista.isEmpty()) {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarExtratoTransferencia/{agencia}{numero}{dataInicio}{dataFim}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<ExtratoDTO>> consultarExtratoTransferencia(@PathVariable  String agencia, @PathVariable String numero,@PathVariable Calendar dataInicio, @PathVariable Calendar dataFim){
        List<ExtratoDTO> lista = extratoService.consultarExtratosTransferencia(agencia,  numero, dataInicio, dataFim);
        if (lista == null || lista.isEmpty()) {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarExtrato/{agencia}{numero}{dataInicio}{dataFim}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<ExtratoDTO>> consultarExtrato(@PathVariable  String agencia, @PathVariable String numero,@PathVariable Calendar dataInicio, @PathVariable Calendar dataFim){
        List<ExtratoDTO> lista = extratoService.consultarExtratos(agencia, numero, dataInicio, dataFim);
        if (lista == null || lista.isEmpty()) {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
}
