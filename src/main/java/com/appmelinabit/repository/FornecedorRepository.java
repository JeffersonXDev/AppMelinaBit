package com.appmelinabit.repository;

import com.appmelinabit.model.Fornecedor;
import com.appmelinabit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> { // MUDADO para Integer

	// Para listar apenas fornecedores do usuário logado
	List<Fornecedor> findByUsuario(Usuario usuario);
}