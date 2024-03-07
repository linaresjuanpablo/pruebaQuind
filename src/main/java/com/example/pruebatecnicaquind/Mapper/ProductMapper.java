package com.example.pruebatecnicaquind.Mapper;

import com.example.pruebatecnicaquind.Dto.ProductDto;
import com.example.pruebatecnicaquind.Entity.CustomerEntity;
import com.example.pruebatecnicaquind.Entity.ProductEntity;

public class ProductMapper {

    public static ProductEntity dtoToProductoEntity(ProductDto productDto) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setEstado(productDto.getEstado());
        productEntity.setSaldo(productDto.getSaldo());
        productEntity.setNumeroCuenta(productDto.getNumeroCuenta());
        productEntity.setCustomerEntity(CustomerEntity
                .builder()
                .id(productDto.getClienteId())
                .build());
        productEntity.setFechaCreacion(productDto.getFechaCreacion());
        productEntity.setExentaGMF(productDto.isExentaGMF());

        return productEntity;

    }
}
