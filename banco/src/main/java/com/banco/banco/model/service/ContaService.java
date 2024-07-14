package com.banco.banco.model.service;


import java.util.ArrayList;
import java.util.List;

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

    public void depositar(String agencia, String numeroConta, Double valor) {
        Conta contaBancaria = this.carregarConta(agencia, numeroConta);
        contaBancaria.setSaldo(contaBancaria.getSaldo() + valor);
        contaBancariaRepository.save(contaBancaria);

        extratoService.salvarExtrato(agencia, numeroConta, valor, "DEPOSITO");


    }

    public void sacar(String agencia, String numero, Double valor) {
        Conta contaBancaria = this.carregarConta(agencia, numero);
        if (contaBancaria.getSaldo() < valor) {
            throw new BadRequest("Saldo Insuficiente");
        }
        contaBancaria.setSaldo(contaBancaria.getSaldo() - valor);
        contaBancariaRepository.save(contaBancaria);

        extratoService.salvarExtrato(agencia, numero, valor, "SAQUE");
    }

    @Transactional(rollbackFor = Exception.class)
    public void transferir(String agenciaOrigem, String numeroOrigem, String agenciaDestino, String numeroDestino, Double valor) {


        Conta contaOrigem = carregarConta(agenciaOrigem, numeroOrigem);
        Conta contaDestino = carregarConta(agenciaDestino, numeroDestino);
        if (contaOrigem.getSaldo() < valor) {
            throw new BadRequest("Saldo Insuficiente");
        }
        contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
        contaDestino.setSaldo(contaDestino.getSaldo() + valor);
        contaBancariaRepository.save(contaDestino);
        contaBancariaRepository.save(contaOrigem);

        extratoService.salvarExtratoTransferencia(agenciaOrigem, numeroOrigem, agenciaDestino, numeroDestino, valor);


    }

    public Conta carregarConta(String agencia, String numero) {
        Conta conta = contaBancariaRepository.findByAgenciaAndNumero(agencia, numero);

        if (conta == null) {
            throw new RegisterNull("Conta Invalida");
        }

        return conta;
    }

}