package com.example.pruebatecnicaquind.Controller;

import com.example.pruebatecnicaquind.Constans.MessageAplication;
import com.example.pruebatecnicaquind.Dto.EditStateAccountDto;
import com.example.pruebatecnicaquind.Dto.RequestAccountCustomerDto;
import com.example.pruebatecnicaquind.Service.Interface.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/producto")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @PostMapping("/createCuenta")
    public String createCuenta(@RequestBody RequestAccountCustomerDto requestAccountCustomerDto){
        try {
            iProductService.createCuenta(requestAccountCustomerDto);
            return MessageAplication.ACCOUNTCREATED;
        } catch (Exception e) {
            return MessageAplication.ACCOUNTCANCELLED;
        }
    }

    @PatchMapping("/updateEstadoCuenta")
    public String updateEstadoCuenta(@RequestBody EditStateAccountDto editStateAccountDto) {
        try {
            iProductService.updateEstadoCuenta(editStateAccountDto);
            return MessageAplication.UPDATEACCOUNTSTATUSCORRECTLY;
        } catch (Exception e) {
            return MessageAplication.ACCOUNTNOTFOUND;
        }
    }

    @PatchMapping("/cancelarCuenta")
    public String cancelarCuenta(@RequestBody EditStateAccountDto editStateAccountDto) {
        try {
            iProductService.cancelarCuenta(editStateAccountDto);
            return MessageAplication.ACCOUNTCANCELLED;
        } catch (Exception e) {
            return MessageAplication.ACCOUNTCANCELLED;
        }
    }

    @PostMapping("/consignar/{numeroCuenta}")
    public String consignar(@PathVariable String numeroCuenta, @RequestParam BigDecimal monto) {
        try {
            iProductService.consignarDinero(numeroCuenta, monto);
            return MessageAplication.ACCOUNTCREATED;
        } catch (IllegalArgumentException e) {
            return MessageAplication.ACCOUNTNOTFOUND;
        } catch (Exception e) {
            return MessageAplication.ACCOUNTCANCELLED;
        }
    }

    @PostMapping("/retirar/{numeroCuenta}")
    public String retirar(@PathVariable String numeroCuenta, @RequestParam BigDecimal monto) {
        try {
            iProductService.retirarDinero(numeroCuenta, monto);
            return MessageAplication.ACCOUNTCANCELLED;
        } catch (IllegalArgumentException e) {
            return MessageAplication.ACCOUNTNOTFOUND;
        } catch (Exception e) {
            return MessageAplication.ACCOUNTCANCELLED;
        }
    }

    @PostMapping("/transferir")
    public String transferir(@RequestParam String origenCuenta, @RequestParam String destinoCuenta, @RequestParam BigDecimal monto) {
        try {
            iProductService.tranferirDinero(origenCuenta, destinoCuenta, monto);
            return MessageAplication.ACCOUNTCANCELLED;
        } catch (IllegalArgumentException e) {
            return MessageAplication.ACCOUNTNOTFOUND;
        } catch (Exception e) {
            return MessageAplication.ACCOUNTCANCELLED;
        }
    }
}
