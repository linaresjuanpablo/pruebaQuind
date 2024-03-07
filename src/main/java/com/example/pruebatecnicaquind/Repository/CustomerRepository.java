package com.example.pruebatecnicaquind.Repository;

import com.example.pruebatecnicaquind.Entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {

    Optional<CustomerEntity> findClienteEntityByNumeroIdentificacion(String numeroIdentificacion);
}
