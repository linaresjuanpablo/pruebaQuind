package com.example.pruebatecnicaquind.Service.Interface;

import com.example.pruebatecnicaquind.Dto.EditStateAccountDto;
import com.example.pruebatecnicaquind.Dto.RequestAccountCustomerDto;

import java.math.BigDecimal;

public interface IProductService {

    Object createCuenta(RequestAccountCustomerDto requestAccountCustomerDto);
    Object updateEstadoCuenta(EditStateAccountDto requestCuentaClienteDto);
    Object cancelarCuenta(EditStateAccountDto requestCuentaClienteDto);
    Object consignarDinero(String numeroCuenta, BigDecimal monto);
    Object retirarDinero(String numeroCuenta, BigDecimal monto);
    void tranferirDinero(String origenCuenta, String destinoCuenta, BigDecimal monto);
}
