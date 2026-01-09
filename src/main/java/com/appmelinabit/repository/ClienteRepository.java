package com.appmelinabit.repository;

import com.appmelinabit.model.Cliente;
import com.appmelinabit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> { // MUDADO PARA Integer

	// Essencial para o Controller: mostra apenas os clientes do dono logado
	List<Cliente> findByUsuario(Usuario usuario);
}