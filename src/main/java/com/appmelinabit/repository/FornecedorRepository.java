package com.appmelinabit.repository;

import com.appmelinabit.model.Fornecedor; // CONFIRME ESTE CAMINHO DA SUA CLASSE DE ENTIDADE
import com.appmelinabit.model.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository // Opcional, mas boa prática para clareza
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

	List<Fornecedor> findByUsuario(Usuario usuarioLogado);

}