package com.banco.banco.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.banco.banco.model.entity.Cliente;
import org.springframework.stereotype.Repository;


@Repository
public interface clienteRepository extends JpaRepository<Cliente, Long> {


    public Cliente findByCpf(String cpf);

    public List<Cliente> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome);

    @Query("select c from Cliente c where upper(c.nome) like upper(:name) and ativo=true ")
    public List<Cliente> findByName(@Param("name") String name);

    public Boolean existsByCpf(String cpf);

}