package com.banco.banco.model.service;


import java.util.ArrayList;
import java.util.List;

import com.banco.banco.model.dto.DepositoDTO;
import com.banco.banco.model.dto.SaqueDTO;
import com.banco.banco.model.dto.TransferenciaDTO;
import com.banco.banco.model.exception.BadRequest;
import com.banco.banco.model.exception.ConteudoInvalido;
import com.banco.banco.model.exception.RegisterNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banco.banco.model.entity.Conta;
import com.banco.banco.model.repository.ContaRepository;



import com.banco.banco.model.dto.ContaClienteDTO;
import com.banco.banco.util.CpfUtil;


@Service
public class ContaService{


    @Autowired
    private ContaRepository contaBancariaRepository;

    @Autowired
    private ExtratoService extratoService;

    public List<ContaClienteDTO> listarContasDoCliente(String cpf){

        boolean cpfValido = CpfUtil.validaCPF(cpf);
        if (!cpfValido) {
            throw new ConteudoInvalido("CPF invalido");
        }

        List<Conta> contasBancarias = contaBancariaRepository.findByClienteCpf(cpf);

        if (contasBancarias== null || contasBancarias.isEmpty()) {
            throw new RegisterNull("Nenhuma conta encontrada");
        }

        List<ContaClienteDTO> listaRetornoDTO = new ArrayList<>();

        for (Conta conta : contasBancarias) {
            ContaClienteDTO dto = new ContaClienteDTO();
            BeanUtils.copyProperties(conta, dto);
            dto.setNomeCliente(conta.getCliente().getNome());
            listaRetornoDTO.add(dto);
        }

        return listaRetornoDTO;
    }

    public void depositar(DepositoDTO depositoDTO) {
        Conta contaBancaria = this.carregarConta(depositoDTO.getAgencia(), depositoDTO.getNumero());
        contaBancaria.setSaldo(contaBancaria.getSaldo() + depositoDTO.getValor());
        contaBancariaRepository.save(contaBancaria);

        extratoService.salvarExtrato(depositoDTO.getAgencia(), depositoDTO.getNumero(),depositoDTO.getValor(), "DEPOSITO");


    }

    public void sacar(SaqueDTO saqueDTO) {
        Conta contaBancaria = this.carregarConta(saqueDTO.getAgencia(), saqueDTO.getNumero());
        if (contaBancaria.getSaldo() < saqueDTO.getValor()) {
            throw new BadRequest("Saldo Insuficiente");
        }
        contaBancaria.setSaldo(contaBancaria.getSaldo() - saqueDTO.getValor());
        contaBancariaRepository.save(contaBancaria);

        extratoService.salvarExtrato(saqueDTO.getAgencia(), saqueDTO.getNumero(), saqueDTO.getValor(), "SAQUE");
    }

    @Transactional(rollbackFor = Exception.class)
    public void transferir(TransferenciaDTO transferenciaDTO) {


        Conta contaOrigem = carregarConta(transferenciaDTO.getAgenciaOrigem(), transferenciaDTO.getNumeroOrigem());
        Conta contaDestino = carregarConta(transferenciaDTO.getAgenciaDestino(), transferenciaDTO.getNumeroDestino());
        if (contaOrigem.getSaldo() < transferenciaDTO.getValor()) {
            throw new BadRequest("Saldo Insuficiente");
        }
        contaOrigem.setSaldo(contaOrigem.getSaldo() - transferenciaDTO.getValor());
        contaDestino.setSaldo(contaDestino.getSaldo() + transferenciaDTO.getValor());
        contaBancariaRepository.save(contaDestino);
        contaBancariaRepository.save(contaOrigem);

        extratoService.salvarExtratoTransferencia(transferenciaDTO.getAgenciaOrigem(), transferenciaDTO.getNumeroOrigem(),
                  transferenciaDTO.getAgenciaDestino(), transferenciaDTO.getNumeroDestino(), transferenciaDTO.getValor());


    }

    public Conta carregarConta(String agencia, String numero) {
        Conta conta = contaBancariaRepository.findByAgenciaAndNumero(agencia, numero);

        if (conta == null) {
            throw new RegisterNull("Conta Invalida");
        }

        return conta;
    }

}