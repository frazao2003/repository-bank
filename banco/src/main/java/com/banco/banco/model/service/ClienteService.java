package com.banco.banco.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.banco.banco.model.dto.SaveClientDTO;
import com.banco.banco.model.entity.Conta;
import com.banco.banco.model.exception.ConteudoInvalido;
import com.banco.banco.model.exception.RegisterNull;
import com.banco.banco.model.repository.ContaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.banco.model.repository.clienteRepository;
import  com.banco.banco.model.dto.ClienteDTO;
import  com.banco.banco.model.entity.Cliente;
import com.banco.banco.util.CpfUtil;




@Service
public class ClienteService{

    @Autowired
    private clienteRepository clientRepo;

    @Autowired
    private ContaRepository contaRepo;

    public ClienteDTO buscarClientePorCpf(String cpf) {

        boolean cpfValido = CpfUtil.validaCPF(cpf);
        if (!cpfValido) {
            throw new ConteudoInvalido("CPF invalido");
        }
        Cliente cli = clientRepo.findByCpf(cpf);
        if (cli == null) {
            throw new RegisterNull("Nenhum registro encontrado");
        }

        ClienteDTO dto = new ClienteDTO();
        BeanUtils.copyProperties(cli, dto);
        dto.setCpfMascarado(cli.getCpf().substring(0, 3)+"***");
        return dto;

    }

    public List<ClienteDTO> buscarClientePorNome(String nome) {
        List<Cliente> listaCliente = clientRepo.findByNomeContainingIgnoreCaseAndAtivoTrue(nome);
        List<ClienteDTO> listaRetornoDto = new ArrayList<>();
        for (Cliente c : listaCliente) {
            ClienteDTO dto = new ClienteDTO();
            BeanUtils.copyProperties(c, dto);
            dto.setCpfMascarado(c.getCpf().substring(0, 3)+"***");
            listaRetornoDto.add(dto);
        }

        return listaRetornoDto;
    }
    public Conta saveCliente(SaveClientDTO clienteDTO) {
        boolean cpfValido = CpfUtil.validaCPF(clienteDTO.getCpf());
        if (!cpfValido) {
            throw new ConteudoInvalido("CPF invalido");
        }
        if(clientRepo.existsByCpf(clienteDTO.getCpf())){
            throw new RuntimeException("Cliente já cadastrado");
        }
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setObservacoes(clienteDTO.getObservacoes());
        cliente.setAtivo(true);
        clientRepo.save(cliente);
        Conta conta = new Conta();

        Random random = new Random();
        StringBuilder agenciaSB = new StringBuilder();
        StringBuilder numeroSB = new StringBuilder();

        // Gera 4 números aleatórios
        for (int i = 0; i < 4; i++) {
            int randomNumber = random.nextInt(10);
            agenciaSB.append(randomNumber);
        }
        String agencia = agenciaSB.toString();
        String numero;
        do{
        for (int i = 0; i < 6; i++) {
            int randomNumber = random.nextInt(10);
            numeroSB.append(randomNumber);
        }
        numero = numeroSB.toString();
        } while (contaRepo.existsByNumero(numero));
        conta.setCliente(cliente);
        conta.setAgencia(agencia);
        conta.setNumero(numero);
        conta.setSaldo(0);
        contaRepo.save(conta);
        return conta;
    }
}
