package com.example.pruebatecnicaquind.Repository;


import com.example.pruebatecnicaquind.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findProductoEntityByNumeroCuenta(String numeroCuenta);
    Boolean findByNumeroCuenta(String numero);
    Boolean existsByNumeroCuenta(String numero);

}
