package com.appmelinabit.repository;

import com.appmelinabit.model.Fornecedor; // CONFIRME ESTE CAMINHO DA SUA CLASSE DE ENTIDADE
import com.appmelinabit.model.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface que representa o repositório de dados para a entidade Fornecedor.
 * Estender JpaRepository fornece métodos CRUD básicos (salvar, buscar, deletar).
 */
@Repository // Opcional, mas boa prática para clareza
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

	List<Fornecedor> findByUsuario(Usuario usuarioLogado);
    
    // Você não precisa escrever código aqui para métodos como save(), findById(), findAll(), etc.
    // Eles são herdados automaticamente do JpaRepository.

    // Se precisar de buscas específicas, você pode declará-las aqui. Exemplo:
    // List<Fornecedor> findByNomeContainingIgnoreCase(String nome);
}