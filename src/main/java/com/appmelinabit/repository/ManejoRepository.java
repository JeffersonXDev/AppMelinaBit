package com.appmelinabit.repository;

import com.appmelinabit.model.Manejo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManejoRepository extends JpaRepository<Manejo, Long> {

    /**
     * Busca todos os registros de manejo associados a um Usuário específico.
     * O Spring Data JPA traduz automaticamente o nome do método:
     * 'findBy' + 'Usuario' (propriedade em Manejo) + 'IdUsuario' (ID da entidade Usuario).
     * * @param idUsuario ID do usuário logado (obtido via Service).
     * @return Uma lista de objetos Manejo pertencentes àquele usuário.
     */
    List<Manejo> findByUsuarioIdUsuario(Long idUsuario);
}