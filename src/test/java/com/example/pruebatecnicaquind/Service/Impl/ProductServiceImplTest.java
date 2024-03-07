package com.example.pruebatecnicaquind.Service.Impl;

import com.example.pruebatecnicaquind.Dto.EditStateAccountDto;
import com.example.pruebatecnicaquind.Dto.ProductDto;
import com.example.pruebatecnicaquind.Dto.RequestAccountCustomerDto;
import com.example.pruebatecnicaquind.Entity.ProductEntity;
import com.example.pruebatecnicaquind.Enum.StateAccount;
import com.example.pruebatecnicaquind.Enum.TypeAccount;
import com.example.pruebatecnicaquind.Repository.AccountRepository;
import com.example.pruebatecnicaquind.Repository.ProductRepository;
import com.example.pruebatecnicaquind.Service.AccountImpl;
import com.example.pruebatecnicaquind.Service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountImpl cuentaServiceMock;

    @InjectMocks
    private ProductServiceImpl productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void CrearDiferente(){
        RequestAccountCustomerDto requestDto = new RequestAccountCustomerDto();
        ProductDto productDto = new ProductDto();
        requestDto.setProductDto(productDto);
        requestDto.setTipoCuenta("abc");

        Object result = productoService.createCuenta(requestDto);
    }

    @Test
    void crearCuentaAhorro() {
        RequestAccountCustomerDto requestDto = new RequestAccountCustomerDto();
        ProductDto productDto = new ProductDto();
        requestDto.setTipoCuenta(TypeAccount.AHORROS.name());
        requestDto.setProductDto(productDto);

        productDto.setSaldo(BigDecimal.valueOf(-100));

        Object result = productoService.createCuenta(requestDto);

    }

    @Test
    void crearCuentaCorriente() {
        RequestAccountCustomerDto requestDto = new RequestAccountCustomerDto();
        ProductDto productDto = new ProductDto();
        requestDto.setTipoCuenta(TypeAccount.CORRIENTE.name());
        requestDto.setProductDto(productDto);

        Object result = productoService.createCuenta(requestDto);

    }


    @Test
    void createCuentaCorriente() {
    }


    @Test
    void updateEstadoCuenta() {
        EditStateAccountDto editStateAccountDto = new EditStateAccountDto();
        editStateAccountDto.setNumeroCuenta("123");
        editStateAccountDto.setEstado(StateAccount.ACTIVA);

        ProductEntity productEntity = new ProductEntity();

        when(productRepository.findProductoEntityByNumeroCuenta(anyString()))
                .thenReturn(Optional.of(productEntity));

        when(productRepository.save(productEntity))
                .thenReturn(productEntity);

        Object result = productoService.updateEstadoCuenta(editStateAccountDto);
        assertNotNull(result);
    }

    @Test
    void updateEstadoCuentaNotFound() {
        EditStateAccountDto editStateAccountDto = new EditStateAccountDto();
        editStateAccountDto.setNumeroCuenta("123");

        when(productRepository.findProductoEntityByNumeroCuenta(anyString()))
                .thenReturn(Optional.empty());

        productoService.updateEstadoCuenta(editStateAccountDto);

    }

    @Test
    void cancelarCuentaSucces() {
        EditStateAccountDto editStateAccountDto = new EditStateAccountDto();
        editStateAccountDto.setNumeroCuenta("123");

        ProductEntity productEntity = new ProductEntity();
        productEntity.setSaldo(BigDecimal.valueOf(0.00));

        when(productRepository.findProductoEntityByNumeroCuenta(anyString()))
                .thenReturn(Optional.of(productEntity));

        when(productRepository.save(productEntity))
                .thenReturn(productEntity);

        productoService.cancelarCuenta(editStateAccountDto);
    }

    @Test
    void cancelarCuentaSaldoMayor() {
        EditStateAccountDto editStateAccountDto = new EditStateAccountDto();
        editStateAccountDto.setNumeroCuenta("123");

        ProductEntity productEntity = new ProductEntity();
        productEntity.setSaldo(BigDecimal.valueOf(1000.00));

        when(productRepository.findProductoEntityByNumeroCuenta(anyString()))
                .thenReturn(Optional.of(productEntity));

        productoService.cancelarCuenta(editStateAccountDto);
    }

    @Test
    void cancelarCuentaNotFound() {
        EditStateAccountDto editStateAccountDto = new EditStateAccountDto();
        editStateAccountDto.setNumeroCuenta("123");

        when(productRepository.findProductoEntityByNumeroCuenta(anyString()))
                .thenReturn(Optional.empty());

        productoService.cancelarCuenta(editStateAccountDto);
    }

    @Test
    void consignarDineroSucces() {
        String numeroCuenta = "123";
        BigDecimal monto = BigDecimal.valueOf(1000.000);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setSaldo(BigDecimal.valueOf(1000.00));

        when(productRepository.findProductoEntityByNumeroCuenta(anyString()))
                .thenReturn(Optional.of(productEntity));

        when(productRepository.save(productEntity))
                .thenReturn(productEntity);

        productoService.consignarDinero(numeroCuenta, monto);
    }

    @Test
    void consignarDineroNotFound() {
        // Arrange
        String numeroCuenta = "NUMERO_DE_PRUEBA";
        BigDecimal monto = BigDecimal.ZERO;

        productoService.consignarDinero(numeroCuenta, monto);
    }

    @Test
    void consignarDineroAccountNotFound() {
        String numeroCuenta = "CUENTA_NO_EXISTENTE";
        BigDecimal monto = BigDecimal.TEN;

        when(productRepository.findProductoEntityByNumeroCuenta(numeroCuenta)).thenReturn(Optional.empty());

        productoService.consignarDinero(numeroCuenta, monto);

    }

    @Test
    void retirarDineroSucces() {
        String numeroCuenta = "123";
        BigDecimal monto = BigDecimal.valueOf(1000.000);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setSaldo(BigDecimal.valueOf(5000.000));

        when(productRepository.findProductoEntityByNumeroCuenta(anyString()))
                .thenReturn(Optional.of(productEntity));

        when(productRepository.save(productEntity))
                .thenReturn(productEntity);

        productoService.retirarDinero(numeroCuenta,monto);
    }

    @Test
    void retirarDineroNotFound() {
        String numeroCuenta = "123";
        BigDecimal monto = BigDecimal.valueOf(1000.000);

        when(productRepository.findProductoEntityByNumeroCuenta(anyString()))
                .thenReturn(Optional.empty());

        productoService.retirarDinero(numeroCuenta,monto);
    }

    @Test
    void retirarDineroInsuficiente() {
        String numeroCuenta = "123";
        BigDecimal monto = BigDecimal.valueOf(1000.000);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setSaldo(BigDecimal.valueOf(100.00));

        when(productRepository.findProductoEntityByNumeroCuenta(anyString()))
                .thenReturn(Optional.of(productEntity));

        productoService.retirarDinero(numeroCuenta,monto);
    }

    @Test
    void tranferirDinero() {
        String origenNumeroCuenta = "123";
        String destinoNumeroCuenta = "1234";
        BigDecimal monto = BigDecimal.valueOf(1000.000);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setSaldo(BigDecimal.valueOf(5000.000));

        when(productRepository.findProductoEntityByNumeroCuenta(anyString()))
                .thenReturn(Optional.of(productEntity));

        when(productRepository.findProductoEntityByNumeroCuenta(anyString()))
                .thenReturn(Optional.of(productEntity));

        when(productRepository.save(productEntity))
                .thenReturn(productEntity);

        productoService.tranferirDinero(origenNumeroCuenta,destinoNumeroCuenta, monto);

    }

    @Test
    void generarNumeroUnico() {
    }

    @Test
    void estadoCuenta() {
    }
}