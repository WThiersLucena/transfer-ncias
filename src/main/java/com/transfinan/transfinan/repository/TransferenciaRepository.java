package com.transfinan.transfinan.repository;

import com.transfinan.transfinan.model.Transferencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {

    
}
