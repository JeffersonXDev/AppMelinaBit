package com.appmelinabit.repository;

import com.appmelinabit.model.Cliente;
import com.appmelinabit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // IMPORTANTE: Faltava este import
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	List<Cliente> findByUsuario(Usuario usuario);

	@Query("SELECT c FROM Cliente c WHERE c.usuario = :usuario AND (" +
			"LOWER(c.nomeCliente) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
			"LOWER(c.cidade) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
			"LOWER(c.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
			"LOWER(c.uf) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
			"LOWER(c.obs) LIKE LOWER(CONCAT('%', :keyword, '%')))")
	List<Cliente> buscarGeral(@Param("keyword") String keyword, @Param("usuario") Usuario usuario);
}