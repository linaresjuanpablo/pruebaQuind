package com.example.pruebatecnicaquind.Mapper;

import com.example.pruebatecnicaquind.Dto.CustomerDto;
import com.example.pruebatecnicaquind.Entity.CustomerEntity;

public class CustomerMapper {
    public static CustomerEntity dtoToClienteEntity(CustomerDto customerDTO) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setTipoIdentificacion(customerDTO.getTipoIdentificacion());
        customerEntity.setNumeroIdentificacion(customerDTO.getNumeroIdentificacion());
        customerEntity.setNombre(customerDTO.getNombre());
        customerEntity.setApellido(customerDTO.getApellido());
        customerEntity.setEmail(customerDTO.getEmail());
        customerEntity.setFechaNacimiento(customerDTO.getFechaNacimiento());
        customerEntity.setFechaCreacion(customerDTO.getFechaCreacion());
        customerEntity.setFechaModificacion(customerDTO.getFechaModificacion());

        return customerEntity;
    }
}
