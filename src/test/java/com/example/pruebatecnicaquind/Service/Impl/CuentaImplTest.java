package com.example.pruebatecnicaquind.Service.Impl;

import com.example.pruebatecnicaquind.Dto.ProductDto;
import com.example.pruebatecnicaquind.Dto.RequestAccountCustomerDto;
import com.example.pruebatecnicaquind.Entity.AccountEntity;
import com.example.pruebatecnicaquind.Entity.ProductEntity;
import com.example.pruebatecnicaquind.Enum.StateAccount;
import com.example.pruebatecnicaquind.Repository.AccountRepository;
import com.example.pruebatecnicaquind.Repository.ProductRepository;
import com.example.pruebatecnicaquind.Service.AccountImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CuentaImplTest {

    @InjectMocks
    private AccountImpl accountImpl;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
    }


    @Test
    void crearCuentaAhorroConSaldoNegativo() {
        RequestAccountCustomerDto requestDto = new RequestAccountCustomerDto();
        ProductDto productDto = new ProductDto();
        productDto.setSaldo(new BigDecimal("-100"));
        requestDto.setProductDto(productDto);

        accountImpl.crearCuentaAhorro(requestDto);
    }

    @Test
    void crearCuentaAhorros() {
        RequestAccountCustomerDto requestDto = new RequestAccountCustomerDto();
        requestDto.setTipoCuenta("AHORRO");
        requestDto.setProductDto(new ProductDto());
        requestDto.getProductDto().setSaldo(BigDecimal.valueOf(1000));

        when(productRepository.save(any(ProductEntity.class))).thenReturn(new ProductEntity());
        when(accountRepository.save(any(AccountEntity.class))).thenReturn(new AccountEntity());

        accountImpl.crearCuentaAhorro(requestDto);

    }

    @Test
    void crearCuentaCorriente() {
        RequestAccountCustomerDto requestDto = new RequestAccountCustomerDto();
        requestDto.setTipoCuenta("CORRIENTE");
        requestDto.setProductDto(new ProductDto());
        requestDto.getProductDto().setEstado(StateAccount.ACTIVA);

        when(productRepository.save(any(ProductEntity.class))).thenReturn(new ProductEntity());

        when(accountRepository.save(any(AccountEntity.class))).thenReturn(new AccountEntity());
        accountImpl.crearCuentaCorriente(requestDto);

    }

    @Test
    void generarNumeroCuentaAleatorio_NumeroNoExistente() {
        when(productRepository.existsByNumeroCuenta(any())).thenReturn(false);
        accountImpl.generarNumeroCuentaAleatorio("53");
    }

}
