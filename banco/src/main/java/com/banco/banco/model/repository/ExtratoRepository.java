package com.banco.banco.model.repository;

import java.util.List;

import com.banco.banco.model.entity.Conta;
import com.banco.banco.model.entity.Extrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ExtratoRepository extends JpaRepository<Extrato, Long> {

    public List<Extrato> findByContaOrigem(Conta conta);

    public List<Extrato> findByContaDestino(Conta conta);

}