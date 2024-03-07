package com.example.pruebatecnicaquind.Controller;

import com.example.pruebatecnicaquind.Constans.MessageAplication;
import com.example.pruebatecnicaquind.Dto.EditStateAccountDto;
import com.example.pruebatecnicaquind.Dto.RequestAccountCustomerDto;
import com.example.pruebatecnicaquind.Service.Interface.IProductService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductoControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private IProductService iProductService;

    @Test
    void createCuenta_Success() {
        RequestAccountCustomerDto requestAccountCustomerDto = new RequestAccountCustomerDto();

        when(iProductService.createCuenta(any(RequestAccountCustomerDto.class))).thenReturn(true);

        productController.createCuenta(requestAccountCustomerDto);
    }

    @Test
    void createCuenta_Failure() {
        RequestAccountCustomerDto requestAccountCustomerDto = new RequestAccountCustomerDto();

        when(iProductService.createCuenta(any(RequestAccountCustomerDto.class))).thenThrow(new RuntimeException(MessageAplication.SIMULATEDERROR));

        productController.createCuenta(requestAccountCustomerDto);
    }

    @Test
    void updateEstadoCuenta_Success() {
        EditStateAccountDto editStateAccountDto = new EditStateAccountDto();

        when(iProductService.updateEstadoCuenta(any())).thenReturn(null);

        productController.updateEstadoCuenta(editStateAccountDto);
    }

    @Test
    void updateEstadoCuenta_AccountNotFound() {
        EditStateAccountDto editStateAccountDto = new EditStateAccountDto();

        when(iProductService.updateEstadoCuenta(any())).thenThrow(new IllegalArgumentException(MessageAplication.ACCOUNTNOTFOUND));

        productController.updateEstadoCuenta(editStateAccountDto);
    }

    @Test
    void cancelarCuenta_Success() {
        EditStateAccountDto editStateAccountDto = new EditStateAccountDto();

        when(iProductService.cancelarCuenta(any())).thenReturn(null);

        productController.cancelarCuenta(editStateAccountDto);
    }

    @Test
    void cancelarCuenta_Exception() {
        EditStateAccountDto editStateAccountDto = new EditStateAccountDto();

        when(iProductService.cancelarCuenta(any())).thenThrow(new RuntimeException(MessageAplication.ACCOUNTCANNOTCANCELLED));

        productController.cancelarCuenta(editStateAccountDto);
    }

    @Test
    void consignar_Success() {
        String numeroCuenta = "123456789";
        BigDecimal monto = BigDecimal.valueOf(100);

        when(iProductService.consignarDinero(any(), any())).thenReturn(null);

        productController.consignar(numeroCuenta, monto);
    }

    @Test
    void consignar_AccountNotFound() {
        String numeroCuenta = "123456789";
        BigDecimal monto = BigDecimal.valueOf(100);

        when(iProductService.consignarDinero(any(), any())).thenThrow(new IllegalArgumentException(MessageAplication.ACCOUNTNOTFOUND));

        productController.consignar(numeroCuenta, monto);

    }

    @Test
    void consignar_Exception() {
        String numeroCuenta = "123456789";
        BigDecimal monto = BigDecimal.valueOf(100);

        when(iProductService.consignarDinero(any(), any())).thenThrow(new RuntimeException(MessageAplication.CANNOTOBTAINED));

        productController.consignar(numeroCuenta, monto);

    }

    @Test
    void retirar_Success() {
        String numeroCuenta = "123456789";
        BigDecimal monto = BigDecimal.valueOf(100);

        when(iProductService.retirarDinero(any(), any())).thenReturn(null);

        productController.retirar(numeroCuenta, monto);

    }

    @Test
    void retirar_AccountNotFound() {
        String numeroCuenta = "123456789";
        BigDecimal monto = BigDecimal.valueOf(100);

        when(iProductService.retirarDinero(any(), any())).thenThrow(new IllegalArgumentException(MessageAplication.ACCOUNTNOTFOUND));

        productController.retirar(numeroCuenta, monto);

    }

    @Test
    void retirar_Exception() {
        String numeroCuenta = "123456789";
        BigDecimal monto = BigDecimal.valueOf(100);

        when(iProductService.retirarDinero(any(), any())).thenThrow(new RuntimeException(MessageAplication.CANNOTWITHDRAWN));

        productController.retirar(numeroCuenta, monto);

    }


    @Test
    void transferir_Success() {
        String origenCuenta = "123456789";
        String destinoCuenta = "987654321";
        BigDecimal monto = BigDecimal.valueOf(100);

        doAnswer(invocation -> {
            return "Transferencia exitosa";
        }).when(iProductService).tranferirDinero(any(), any(), any());

        productController.transferir(origenCuenta, destinoCuenta, monto);
    }




    @Test
    void transferir_AccountNotFound() {
        String origenCuenta = "123456789";
        String destinoCuenta = "987654321";
        BigDecimal monto = BigDecimal.valueOf(100);

        doThrow(new IllegalArgumentException("Cuenta no encontrada")).when(iProductService).tranferirDinero(any(), any(), any());

        String result = productController.transferir(origenCuenta, destinoCuenta, monto);

        assertEquals(MessageAplication.ACCOUNTNOTFOUND, result);
    }

    @Test
    void transferir_Exception() {
        String origenCuenta = "123456789";
        String destinoCuenta = "987654321";
        BigDecimal monto = BigDecimal.valueOf(100);

        doThrow(new RuntimeException("Error al transferir en la cuenta")).when(iProductService).tranferirDinero(any(), any(), any());

        String result = productController.transferir(origenCuenta, destinoCuenta, monto);

        assertEquals(MessageAplication.ACCOUNTCANCELLED, result);
    }
}
