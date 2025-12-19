package com.appmelinabit.repository;

import com.appmelinabit.model.HistoricoCompraFornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoCompraFornecedorRepository extends JpaRepository<HistoricoCompraFornecedor, Long> {
    
}