package com.banco.banco.model.service;

import com.banco.banco.model.exception.RegisterNull;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.banco.model.entity.Conta;
import com.banco.banco.model.entity.Extrato;
import com.banco.banco.model.repository.ExtratoRepository;



import com.banco.banco.model.dto.ExtratoDTO;


@Service
public class ExtratoService {

    @Autowired
    private ExtratoRepository extratoRepository;

    @Autowired
    private ContaService contaService;

    public List<ExtratoDTO> consultarExtratosDeposito(String agencia, String numero, Calendar dataInicio, Calendar dataFim) {
        Conta conta = contaService.carregarConta(agencia, numero);
        if (conta == null) {
            throw new RegisterNull("Conta Invalida");
        }
        List<Extrato> listaExtrato = extratoRepository.findByContaOrigem(conta);
        List<ExtratoDTO> listaRetorno = new ArrayList<ExtratoDTO>();
        if (listaExtrato == null || listaExtrato.isEmpty()) {
            throw new RegisterNull("Nenhum Registro Encontrado");
        }

        for (Extrato e : listaExtrato) {
            if (e.getData().after(dataInicio) || e.getData().equals(dataInicio) || e.getData().before(dataFim) || e.getData().equals(dataFim) && e.getTipoExtrato().equals("Deposito")) {
                ExtratoDTO dto = new ExtratoDTO();
                dto.setAgenciaOrigem(e.getContaOrigem().getAgencia());
                dto.setNumeroOrigem(e.getContaOrigem().getNumero());
                dto.setData(e.getData());
                dto.setNomeOrigem(e.getContaOrigem().getCliente().getNome());
                dto.setValor(e.getValor());
                dto.setCpfMascaradoOrigem(e.getContaOrigem().getCliente().getCpf().substring(0, 3) + "***.***-**");
                dto.setTipoExtrato(e.getTipoExtrato());
                listaRetorno.add(dto);
            }
        }
        if (listaRetorno == null || listaRetorno.isEmpty()) {
            throw new RegisterNull("Nenhum Registro Encontrado");
        }

        return listaRetorno;
    }

    public List<ExtratoDTO> consultarExtratosSaque(String agencia, String numero, Calendar dataInicio, Calendar dataFim) {
        Conta conta = contaService.carregarConta(agencia, numero);
        if (conta == null) {
            throw new RegisterNull("Nenhum Registro Encontrado");
        }
        List<Extrato> listaExtrato = extratoRepository.findByContaOrigem(conta);
        List<ExtratoDTO> listaRetorno = new ArrayList<ExtratoDTO>();
        if (listaExtrato == null || listaExtrato.isEmpty()) {
            throw new RegisterNull("Nenhum Registro Encontrado");
        }

        for (Extrato e : listaExtrato) {
            if (e.getData().after(dataInicio) || e.getData().equals(dataInicio) || e.getData().before(dataFim) || e.getData().equals(dataFim) && e.getTipoExtrato().equals("Saque")) {
                ExtratoDTO dto = new ExtratoDTO();
                dto.setAgenciaOrigem(e.getContaOrigem().getAgencia());
                dto.setNumeroOrigem(e.getContaOrigem().getNumero());
                dto.setData(e.getData());
                dto.setNomeOrigem(e.getContaOrigem().getCliente().getNome());
                dto.setValor(e.getValor());
                dto.setCpfMascaradoOrigem(e.getContaOrigem().getCliente().getCpf().substring(0, 3) + "***.***-**");
                dto.setTipoExtrato(e.getTipoExtrato());
                listaRetorno.add(dto);
            }
        }
        if (listaRetorno == null || listaRetorno.isEmpty()) {
            throw new RegisterNull("Nenhum Registro Encontrado");
        }

        return listaRetorno;
    }

    public List<ExtratoDTO> consultarExtratosTransferencia(String agencia, String numero, Calendar dataInicio, Calendar dataFim) {
        Conta conta = contaService.carregarConta(agencia, numero);
        if (conta == null) {
            throw new RegisterNull("Nenhum Registro Encontrado");
        }
        List<Extrato> listaExtratoTransferenciaEnviadas = extratoRepository.findByContaOrigem(conta);
        List<Extrato> listaExtratoTransferenciaRecebidas = extratoRepository.findByContaDestino(conta);
        List<ExtratoDTO> listaRetorno = new ArrayList<ExtratoDTO>();
        if (listaExtratoTransferenciaEnviadas == null || listaExtratoTransferenciaEnviadas.isEmpty() && listaExtratoTransferenciaRecebidas == null || listaExtratoTransferenciaRecebidas.isEmpty()) {
            throw new RegisterNull("Nenhum Registro Encontrado");
        }

        for (Extrato e : listaExtratoTransferenciaEnviadas) {
            if (e.getData().after(dataInicio) || e.getData().equals(dataInicio) || e.getData().before(dataFim) || e.getData().equals(dataFim) && e.getTipoExtrato().equals("Transferencia")) {
                ExtratoDTO dto = new ExtratoDTO();
                dto.setAgenciaOrigem(e.getContaOrigem().getAgencia());
                dto.setNumeroOrigem(e.getContaOrigem().getNumero());
                dto.setAgenciaDestino(e.getContaDestino().getAgencia());
                dto.setNumeroDestino(e.getContaDestino().getNumero());
                dto.setData(e.getData());
                dto.setNomeOrigem(e.getContaOrigem().getCliente().getNome());
                dto.setNomeDestino(e.getContaDestino().getCliente().getNome());
                dto.setValor(e.getValor() * -1);
                dto.setCpfMascaradoOrigem(e.getContaOrigem().getCliente().getCpf().substring(0, 3) + "***.***-**");
                dto.setCpfMascaradoDestino(e.getContaDestino().getCliente().getCpf().substring(0, 3) + "***.***-**");
                dto.setTipoExtrato(e.getTipoExtrato());
                listaRetorno.add(dto);
            }
        }
        for (Extrato e : listaExtratoTransferenciaRecebidas) {
            if (e.getData().after(dataInicio) || e.getData().equals(dataInicio) || e.getData().before(dataFim) || e.getData().equals(dataFim) && e.getTipoExtrato().equals("Transferencia")) {
                ExtratoDTO dto = new ExtratoDTO();
                dto.setAgenciaOrigem(e.getContaOrigem().getAgencia());
                dto.setNumeroOrigem(e.getContaOrigem().getNumero());
                dto.setAgenciaDestino(e.getContaDestino().getAgencia());
                dto.setNumeroDestino(e.getContaDestino().getNumero());
                dto.setData(e.getData());
                dto.setNomeOrigem(e.getContaOrigem().getCliente().getNome());
                dto.setNomeDestino(e.getContaDestino().getCliente().getNome());
                dto.setValor(e.getValor());
                dto.setCpfMascaradoOrigem(e.getContaOrigem().getCliente().getCpf().substring(0, 3) + "***.***-**");
                dto.setCpfMascaradoDestino(e.getContaDestino().getCliente().getCpf().substring(0, 3) + "***.***-**");
                dto.setTipoExtrato(e.getTipoExtrato());
                listaRetorno.add(dto);
            }
        }

        if (listaRetorno == null || listaRetorno.isEmpty()) {
            throw new RegisterNull("Nenhum Registro Encontrado");
        }


        return listaRetorno;
    }

    public List<ExtratoDTO> consultarExtratos(String agencia, String numero, Calendar dataInicio, Calendar dataFim) {
        Conta conta = contaService.carregarConta(agencia, numero);
        if (conta == null) {
            throw new RegisterNull("Nenhum Registro Encontrado");
        }
        List<ExtratoDTO> listaRetorno = new ArrayList();
        listaRetorno.addAll(this.consultarExtratosDeposito(agencia, numero, dataInicio, dataFim));
        listaRetorno.addAll(this.consultarExtratosSaque(agencia, numero, dataInicio, dataFim));
        listaRetorno.addAll(this.consultarExtratosTransferencia(agencia, numero, dataInicio, dataFim));

        if (listaRetorno == null || listaRetorno.isEmpty()) {
            throw new RegisterNull("Nenhum Registro Encontrado");
        }

        return listaRetorno;
    }

    public void salvarExtrato(String agencia, String numero, Double valor, String tipoExtrato) {
        Conta conta = contaService.carregarConta(agencia, numero);
        Extrato salvarExtrato = new Extrato();

        salvarExtrato.setContaOrigem(conta);
        salvarExtrato.setData(Calendar.getInstance());
        if (tipoExtrato.equals("SAQUE")) {
            salvarExtrato.setValor(valor * -1);
        } else {
            salvarExtrato.setValor(valor);
        }
        salvarExtrato.setTipoExtrato(tipoExtrato);
        extratoRepository.save(salvarExtrato);
    }

    public void salvarExtratoTransferencia(String agenciaOrigem, String numeroOrigem, String agenciaDestino, String numeroDestino, Double valor) {
        Conta contaOrigem = contaService.carregarConta(agenciaOrigem, numeroOrigem);
        Conta contaDestino = contaService.carregarConta(agenciaDestino, numeroDestino);
        Extrato salvarExtrato = new Extrato();

        salvarExtrato.setContaDestino(contaDestino);
        salvarExtrato.setContaOrigem(contaOrigem);
        salvarExtrato.setData(Calendar.getInstance());
        salvarExtrato.setValor(valor);
        salvarExtrato.setTipoExtrato("TRANSFERENCIA");
        extratoRepository.save(salvarExtrato);

    }
}
