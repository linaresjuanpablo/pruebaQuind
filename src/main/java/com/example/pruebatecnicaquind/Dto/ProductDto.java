package com.example.pruebatecnicaquind.Dto;

import com.example.pruebatecnicaquind.Entity.Audit;
import com.example.pruebatecnicaquind.Enum.StateAccount;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto extends Audit {

    private String tipoCuenta;
    private String numeroCuenta;
    private StateAccount estado;
    private BigDecimal saldo;
    private boolean exentaGMF;
    private Long clienteId;
}
