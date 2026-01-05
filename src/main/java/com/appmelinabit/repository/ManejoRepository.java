package com.appmelinabit.repository;

import com.appmelinabit.model.Manejo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManejoRepository extends JpaRepository<Manejo, Long> {

    List<Manejo> findByUsuarioIdUsuario(Long idUsuario);
}