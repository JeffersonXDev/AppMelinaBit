package com.appmelinabit.repository;

import com.appmelinabit.model.Cliente;
import com.appmelinabit.model.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	List<Cliente> findByUsuario(Usuario usuarioLogado);
    // Você pode adicionar métodos personalizados aqui, se necessário.
}