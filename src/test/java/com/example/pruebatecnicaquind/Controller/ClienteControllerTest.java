package com.example.pruebatecnicaquind.Controller;

import com.example.pruebatecnicaquind.Constans.MessageAplication;
import com.example.pruebatecnicaquind.Dto.CustomerDto;
import com.example.pruebatecnicaquind.Enum.TypeDocument;
import com.example.pruebatecnicaquind.Service.Interface.ICustomerService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ClienteControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private ICustomerService iCustomerService;

    @Test
    void createCliente_Success() {
        CustomerDto customerDto = CustomerDto.builder()
                .nombre("string")
                .apellido("string")
                .email("string")
                .fechaNacimiento("2001-18-06")
                .tipoIdentificacion(TypeDocument.CC)
                .build();

        when(iCustomerService.createCliente(any(CustomerDto.class))).thenReturn(true);

        customerController.createCliente(customerDto);
    }

    @Test
    void createCliente_Failure() {
        CustomerDto customerDto = CustomerDto.builder().build();

        when(iCustomerService.createCliente(any(CustomerDto.class))).thenThrow(new RuntimeException(MessageAplication.ACCOUNTCANNOTCANCELLED));

        customerController.createCliente(customerDto);
    }

    @Test
    void updateCliente_Success() {
        CustomerDto customerDto = CustomerDto.builder()
                .tipoIdentificacion(TypeDocument.CC)
                .build();

        String numeroIdentificacion = "123456789";

        when(iCustomerService.updateCliente(eq(numeroIdentificacion), any(CustomerDto.class))).thenReturn(true);

        customerController.updateCliente(numeroIdentificacion, customerDto);
    }

    @Test
    void updateCliente_NotFound() {
        CustomerDto customerDto = CustomerDto.builder()
                .tipoIdentificacion(TypeDocument.CC)
                .build();

        String numeroIdentificacion = "123456789";

        when(iCustomerService.updateCliente(eq(numeroIdentificacion), any(CustomerDto.class))).thenThrow(new IllegalArgumentException(MessageAplication.ACCOUNTNOTFOUND));

        customerController.updateCliente(numeroIdentificacion, customerDto);
    }

    @Test
    void updateCliente_Exception() {
        CustomerDto customerDto = CustomerDto.builder()
                .tipoIdentificacion(TypeDocument.CC)
                .build();

        String numeroIdentificacion = "123456789";

        when(iCustomerService.updateCliente(eq(numeroIdentificacion), any(CustomerDto.class))).thenThrow(new RuntimeException(MessageAplication.ACCOUNTCANCELLED));

        customerController.updateCliente(numeroIdentificacion, customerDto);
    }

    @Test
    void deleteCliente_Success() {
        String numeroIdentificacion = "123456789";

        when(iCustomerService.deleteCliente(eq(numeroIdentificacion))).thenReturn(MessageAplication.ACCOUNTDELETED);

        customerController.deleteCliente(numeroIdentificacion);
    }

    @Test
    void deleteCliente_NotFound() {
        String numeroIdentificacion = "123456789";

        when(iCustomerService.deleteCliente(eq(numeroIdentificacion))).thenThrow(new IllegalArgumentException(MessageAplication.ACCOUNTNOTFOUND));

        customerController.deleteCliente(numeroIdentificacion);
    }

    @Test
    void deleteCliente_Exception() {
        String numeroIdentificacion = "123456789";

        when(iCustomerService.deleteCliente(eq(numeroIdentificacion))).thenThrow(new RuntimeException(MessageAplication.ACCOUNTCANCELLED));

        customerController.deleteCliente(numeroIdentificacion);
    }
}
