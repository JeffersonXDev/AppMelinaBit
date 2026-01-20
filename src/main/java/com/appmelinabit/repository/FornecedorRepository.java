package com.appmelinabit.repository;

import com.appmelinabit.model.Fornecedor;
import com.appmelinabit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> { // MUDADO para Integer

	// Para listar apenas fornecedores do usuário logado
	List<Fornecedor> findByUsuario(Usuario usuario);

	@Query("SELECT f FROM Fornecedor f WHERE f.usuario = :usuario AND " +
			"(LOWER(f.nomeFornecedor) LIKE LOWER(concat('%', :keyword, '%')) OR " +
			"LOWER(f.vendedor) LIKE LOWER(concat('%', :keyword, '%')) OR " +
			"LOWER(f.materialComprado) LIKE LOWER(concat('%', :keyword, '%')) OR " +
			"LOWER(f.cidade) LIKE LOWER(concat('%', :keyword, '%')) OR " +
			"LOWER(f.cnpj) LIKE LOWER(concat('%', :keyword, '%')))")
	List<Fornecedor> buscarFornecedores(@Param("keyword") String keyword, @Param("usuario") Usuario usuario);
}