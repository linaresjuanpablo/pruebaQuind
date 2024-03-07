package com.example.pruebatecnicaquind.Service.Impl;

import com.example.pruebatecnicaquind.Dto.CustomerDto;
import com.example.pruebatecnicaquind.Entity.CustomerEntity;
import com.example.pruebatecnicaquind.Entity.ProductEntity;
import com.example.pruebatecnicaquind.Enum.TypeDocument;
import com.example.pruebatecnicaquind.Repository.CustomerRepository;
import com.example.pruebatecnicaquind.Service.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCliente() {
        CustomerDto customerDto = CustomerDto.builder()
                .fechaNacimiento("2000-11-11")
                .build();

        CustomerEntity customerEntity = new CustomerEntity();
        when(customerRepository.save(customerEntity)).thenReturn(customerEntity);
        clienteService.createCliente(customerDto);
    }

    @Test
    void createClienteMenor() {
        CustomerDto customerDto = CustomerDto.builder()
                .fechaNacimiento("2023-11-11")
                .build();

        clienteService.createCliente(customerDto);
    }

    @Test
    void updateCliente() {
        CustomerDto customerDto = CustomerDto.builder()
                .numeroIdentificacion("123456789")
                .fechaNacimiento("1990-01-01")
                .build();
        CustomerEntity customerEntity = new CustomerEntity();
        Optional<CustomerEntity> existingClienteEntityOptional = Optional.of(customerEntity);

        CustomerRepository customerRepositoryMock = mock(CustomerRepository.class);
        when(customerRepositoryMock.findClienteEntityByNumeroIdentificacion(customerDto.getNumeroIdentificacion())).thenReturn(existingClienteEntityOptional);
        when(customerRepositoryMock.save(any(CustomerEntity.class))).thenReturn(customerEntity);

        clienteService.updateCliente(customerDto.getNumeroIdentificacion(), customerDto);
    }

    @Test
    void updateClienteMenor() {
        CustomerDto customerDto = CustomerDto.builder()
                .numeroIdentificacion("123456789")
                .fechaNacimiento("2010-01-01")
                .build();
        CustomerEntity existingCustomerEntity = new CustomerEntity();
        Optional<CustomerEntity> existingClienteEntityOptional = Optional.of(existingCustomerEntity);

        customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findClienteEntityByNumeroIdentificacion(customerDto.getNumeroIdentificacion())).thenReturn(existingClienteEntityOptional);
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(existingCustomerEntity);

        clienteService.updateCliente(customerDto.getNumeroIdentificacion(), customerDto);
    }

    @Test
    void updateClienteNoExistente() {
        String numeroIdentificacion = "clienteExistente";
        CustomerDto customerDto = CustomerDto.builder()
                .tipoIdentificacion(TypeDocument.CC)
                .numeroIdentificacion(numeroIdentificacion)
                .nombre("Nombre")
                .apellido("Apellido")
                .email("correo@ejemplo.com")
                .fechaNacimiento("1990-01-01")
                .build();

        CustomerEntity customerEntityExistente = new CustomerEntity();
        customerEntityExistente.setId(1L);
        Optional<CustomerEntity> existingClienteEntityOptional = Optional.of(customerEntityExistente);

        when(customerRepository.findClienteEntityByNumeroIdentificacion(numeroIdentificacion)).thenReturn(existingClienteEntityOptional);
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntityExistente);

        clienteService.updateCliente(numeroIdentificacion, customerDto);

    }

    @Test
    void deleteCliente() {
        CustomerDto customerDto = CustomerDto.builder()
                .numeroIdentificacion("123456789")
                .build();

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1L);
        customerEntity.setProductEntity(new ArrayList<>());
        Optional<CustomerEntity> clienteEntityOptional = Optional.of(customerEntity);

        when(customerRepository.findClienteEntityByNumeroIdentificacion(customerDto.getNumeroIdentificacion())).thenReturn(clienteEntityOptional);
        when(customerRepository.existsById(customerEntity.getId())).thenReturn(true);

        clienteService.deleteCliente(customerDto.getNumeroIdentificacion());
    }

    @Test
    void deleteClienteConProductosAsociados() {
        CustomerDto customerDto = CustomerDto.builder()
                .numeroIdentificacion("123456789")
                .build();

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1L);
        List<ProductEntity> productos = new ArrayList<>();
        productos.add(new ProductEntity());
        customerEntity.setProductEntity(productos);
        Optional<CustomerEntity> clienteEntityOptional = Optional.of(customerEntity);

        when(customerRepository.findClienteEntityByNumeroIdentificacion(customerDto.getNumeroIdentificacion())).thenReturn(clienteEntityOptional);
        when(customerRepository.existsById(customerEntity.getId())).thenReturn(true);

        clienteService.deleteCliente(customerDto.getNumeroIdentificacion());
    }

    @Test
    void deleteClienteNoEncontrado() {
        CustomerDto customerDto = CustomerDto.builder()
                .numeroIdentificacion("123456789")
                .build();

        when(customerRepository.findClienteEntityByNumeroIdentificacion(customerDto.getNumeroIdentificacion())).thenReturn(Optional.empty());

        clienteService.deleteCliente(customerDto.getNumeroIdentificacion());
    }
}