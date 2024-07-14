package com.banco.banco.model.repository;

import java.util.List;

import com.banco.banco.model.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    public List<Conta> findByClienteCpf(String cpf);

    public Conta findByAgenciaAndNumero(String agencia, String numero);

    public Boolean existsByNumero(String numero);

}