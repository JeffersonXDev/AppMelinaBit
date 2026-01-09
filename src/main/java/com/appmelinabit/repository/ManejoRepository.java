package com.appmelinabit.repository;

import com.appmelinabit.model.Manejo;
import com.appmelinabit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ManejoRepository extends JpaRepository<Manejo, Integer> {

    // 1. Busca passando o objeto Usuario completo
    List<Manejo> findByUsuario(Usuario usuario);

    // 2. Busca passando apenas o ID (Integer) do usuário
    // O Spring JPA entende que deve entrar em Usuario e buscar pelo idUsuario
    List<Manejo> findByUsuario_IdUsuario(Integer idUsuario);
}