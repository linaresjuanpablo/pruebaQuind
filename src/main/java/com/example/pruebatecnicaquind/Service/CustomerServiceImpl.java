package com.example.pruebatecnicaquind.Service;

import com.example.pruebatecnicaquind.Constans.MessageAplication;
import com.example.pruebatecnicaquind.Dto.CustomerDto;
import com.example.pruebatecnicaquind.Entity.CustomerEntity;
import com.example.pruebatecnicaquind.Mapper.CustomerMapper;
import com.example.pruebatecnicaquind.Repository.CustomerRepository;
import com.example.pruebatecnicaquind.Service.Interface.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Object createCliente(CustomerDto customerDTO) {
        Boolean edadValida = validateEdadCliente(customerDTO.getFechaNacimiento());
        if (!edadValida){
            return MessageAplication.CANNOTMINOR;
        }

        customerDTO.setFechaCreacion(LocalDateTime.now());
        customerDTO.setFechaModificacion(null);
        CustomerEntity saveInformation = CustomerMapper.dtoToClienteEntity(customerDTO);
        return customerRepository.save(saveInformation);
    }

    @Override
    public Object updateCliente(String numeroIdentificacion, CustomerDto customerDTO) {
        Optional<CustomerEntity> existingClienteEntity = customerRepository.findClienteEntityByNumeroIdentificacion(numeroIdentificacion);

        Boolean edadValida = validateEdadCliente(customerDTO.getFechaNacimiento());
        if (!edadValida){
            return MessageAplication.CANNOTMINOR;
        }

        if (existingClienteEntity.isPresent()){
            existingClienteEntity.get().setTipoIdentificacion(customerDTO.getTipoIdentificacion());
            existingClienteEntity.get().setNumeroIdentificacion(customerDTO.getNumeroIdentificacion());
            existingClienteEntity.get().setNombre(customerDTO.getNombre());
            existingClienteEntity.get().setApellido(customerDTO.getApellido());
            existingClienteEntity.get().setEmail(customerDTO.getEmail());
            existingClienteEntity.get().setFechaNacimiento(customerDTO.getFechaNacimiento());
            existingClienteEntity.get().setFechaModificacion(LocalDateTime.now());
            return customerRepository.save(existingClienteEntity.get());
        }

        return null;
    }

    @Override
    public String deleteCliente(String numeroIdentificacion) {
        Optional<CustomerEntity> clienteEntity = customerRepository.findClienteEntityByNumeroIdentificacion(numeroIdentificacion);
        if (clienteEntity.isPresent()){

            if (clienteEntity.get().getProductEntity().isEmpty()) {
                customerRepository.deleteById(clienteEntity.get().getId());
                return MessageAplication.DELETECLIENT;
            }
            return MessageAplication.DELETECLIENTERROR;
        }

        return MessageAplication.CLIENTNOTFOUND;
    }

    private Boolean validateEdadCliente(String fechaNacimiento) {
            LocalDate fechaNac = LocalDate.parse(fechaNacimiento);
            LocalDate ahora = LocalDate.now();
            Period periodo = Period.between(fechaNac, ahora);
            int edad = periodo.getYears();
            if (edad < 18) {
                return false;
            }
            return true;
        }

}
