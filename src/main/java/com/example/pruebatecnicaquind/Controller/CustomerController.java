package com.example.pruebatecnicaquind.Controller;

import com.example.pruebatecnicaquind.Constans.MessageAplication;
import com.example.pruebatecnicaquind.Dto.CustomerDto;
import com.example.pruebatecnicaquind.Service.Interface.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/cliente")
public class CustomerController {

    @Autowired
    private ICustomerService iCustomerService;

    @PostMapping("/create")
    public String createCliente(@RequestBody CustomerDto customerDTO){
        try {
            iCustomerService.createCliente(customerDTO);
            return MessageAplication.ACCOUNTCREATED;
        } catch (Exception e) {
            return MessageAplication.ACCOUNTCANNOTCANCELLED;
        }
    }

    @PatchMapping("/update/{numeroIdentificacion}")
    public String updateCliente(@PathVariable String numeroIdentificacion, @RequestBody CustomerDto customerDTO){
        try {
            iCustomerService.updateCliente(numeroIdentificacion, customerDTO);
            return MessageAplication.UPDATEACCOUNTSTATUSCORRECTLY;
        } catch (IllegalArgumentException e) {
            return MessageAplication.ACCOUNTNOTFOUND;
        } catch (Exception e) {
            return MessageAplication.ACCOUNTCANCELLED;
        }
    }

    @DeleteMapping("/delete/{numeroIdentificacion}")
    public String deleteCliente(@PathVariable String numeroIdentificacion){
        try {
            iCustomerService.deleteCliente(numeroIdentificacion);
            return MessageAplication.ACCOUNTCANCELLED;
        } catch (IllegalArgumentException e) {
            return MessageAplication.ACCOUNTNOTFOUND;
        } catch (Exception e) {
            return MessageAplication.ACCOUNTCANCELLED;
        }
    }
}
