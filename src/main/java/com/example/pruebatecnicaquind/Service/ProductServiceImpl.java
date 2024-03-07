package com.example.pruebatecnicaquind.Service;

import com.example.pruebatecnicaquind.Constans.MessageAplication;
import com.example.pruebatecnicaquind.Dto.EditStateAccountDto;
import com.example.pruebatecnicaquind.Dto.RequestAccountCustomerDto;
import com.example.pruebatecnicaquind.Entity.ProductEntity;
import com.example.pruebatecnicaquind.Enum.StateAccount;
import com.example.pruebatecnicaquind.Enum.TypeAccount;
import com.example.pruebatecnicaquind.Repository.ProductRepository;
import com.example.pruebatecnicaquind.Service.Interface.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AccountImpl crearCuenta;

    @Override
    public Object createCuenta(RequestAccountCustomerDto requestAccountCustomerDto) {
        requestAccountCustomerDto.getProductDto().setNumeroCuenta(null);
        String numeroCuenta;

        if (requestAccountCustomerDto.getTipoCuenta().equals(TypeAccount.AHORROS.name())){
            numeroCuenta = crearCuenta.generarNumeroCuentaAleatorio("53");
            requestAccountCustomerDto.getProductDto().setNumeroCuenta(numeroCuenta);
            return  crearCuenta.crearCuentaAhorro(requestAccountCustomerDto);

        } else if (requestAccountCustomerDto.getTipoCuenta().equals(TypeAccount.CORRIENTE.name())) {
            numeroCuenta = crearCuenta.generarNumeroCuentaAleatorio("33");
            requestAccountCustomerDto.getProductDto().setNumeroCuenta(numeroCuenta);
            return crearCuenta.crearCuentaCorriente(requestAccountCustomerDto);

        }
        return MessageAplication.CANNOTCREATEDIFFERENTACCOUNT;
    }

    @Override
    public Object updateEstadoCuenta(EditStateAccountDto editStateAccountDto) {
        Optional<ProductEntity> productoEntity = productRepository.findProductoEntityByNumeroCuenta(editStateAccountDto.getNumeroCuenta());
        if (productoEntity.isPresent()) {
            productoEntity.get().setEstado(editStateAccountDto.getEstado());
            productoEntity.get().setFechaModificacion(LocalDateTime.now());
            productRepository.save(productoEntity.get());
            return MessageAplication.UPDATEACCOUNTSTATUSCORRECTLY;
        }
        return MessageAplication.ACCOUNTNOTFOUND;
    }

    @Override
    public Object cancelarCuenta(EditStateAccountDto editStateAccountDto) {
        Optional<ProductEntity> productoEntity = productRepository.findProductoEntityByNumeroCuenta(editStateAccountDto.getNumeroCuenta());
        if (!productoEntity.isPresent()){
            return MessageAplication.ACCOUNTNOTFOUND;
        }
        if (productoEntity.get().getSaldo().compareTo(BigDecimal.ZERO) != 0) {
            return MessageAplication.ACCOUNTCANNOTCANCELLED;
        }
        productoEntity.get().setEstado(StateAccount.CANCELADA);
        productoEntity.get().setFechaModificacion(LocalDateTime.now());
        productRepository.save(productoEntity.get());
        return MessageAplication.ACCOUNTCANCELLED;
    }

    @Override
    public Object consignarDinero(String numeroCuenta, BigDecimal monto) {
        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
           return MessageAplication.AMOUNTAPPROPRIATIONMUSTBEPOSITIVE;
        }

        Optional<ProductEntity> productoEntity = productRepository.findProductoEntityByNumeroCuenta(numeroCuenta);
        if (productoEntity.isEmpty()){
            return MessageAplication.ACCOUNTNOTFOUND;
        }

        productoEntity.get().setSaldo(productoEntity.get().getSaldo().add(monto));
        productoEntity.get().setFechaModificacion(LocalDateTime.now());

        return productRepository.save(productoEntity.get());
    }

    @Override
    public Object retirarDinero(String numeroCuenta, BigDecimal monto) {
        Optional<ProductEntity> productoEntity = productRepository.findProductoEntityByNumeroCuenta(numeroCuenta);

        if (productoEntity.isEmpty()){
            return MessageAplication.ACCOUNTNOTFOUND;
        }
        if (productoEntity.get().getSaldo().compareTo(monto) >= 0) {
            productoEntity.get().setSaldo(productoEntity.get().getSaldo().subtract(monto));
            productoEntity.get().setFechaModificacion(LocalDateTime.now());

            return productRepository.save(productoEntity.get());
        } else {
            return MessageAplication.INSUFFICIENTBALANCE;
        }
    }

    @Override
    public void tranferirDinero(String origenNumeroCuenta,  String destinoNumeroCuenta, BigDecimal monto) {
        retirarDinero(origenNumeroCuenta,monto);
        consignarDinero(destinoNumeroCuenta,monto);
    }

}
