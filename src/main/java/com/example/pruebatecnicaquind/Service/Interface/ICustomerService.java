package com.example.pruebatecnicaquind.Service.Interface;

import com.example.pruebatecnicaquind.Dto.CustomerDto;

public interface ICustomerService {

    Object createCliente(CustomerDto customerDTO);

    Object updateCliente(String numeroIdentificacion, CustomerDto customerDTO);

    String deleteCliente(String numeroIdentificacion);

}
