package com.example.pruebatecnicaquind.Service;

import com.example.pruebatecnicaquind.Constans.MessageAplication;
import com.example.pruebatecnicaquind.Dto.RequestAccountCustomerDto;
import com.example.pruebatecnicaquind.Entity.AccountEntity;
import com.example.pruebatecnicaquind.Entity.ProductEntity;
import com.example.pruebatecnicaquind.Enum.StateAccount;
import com.example.pruebatecnicaquind.Mapper.ProductMapper;
import com.example.pruebatecnicaquind.Repository.AccountRepository;
import com.example.pruebatecnicaquind.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AccountImpl {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Object crearCuentaAhorro(RequestAccountCustomerDto requestAccountCustomerDto) {

        if (requestAccountCustomerDto.getProductDto().getSaldo().compareTo(BigDecimal.ZERO) < 0){
            return MessageAplication.BALANCECANNOTLESS0;
        }

        //requestCuentaClienteDto.getProductoDto().setNumeroCuenta(null);

        requestAccountCustomerDto.getProductDto().setNumeroCuenta(generarNumeroCuentaAleatorio("53"));
        requestAccountCustomerDto.getProductDto().setEstado(StateAccount.ACTIVA);
        requestAccountCustomerDto.getProductDto().setFechaCreacion(LocalDateTime.now());

        ProductEntity producto = ProductMapper.dtoToProductoEntity(requestAccountCustomerDto.getProductDto());
        productRepository.save(producto);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setTipoCuenta(requestAccountCustomerDto.getTipoCuenta());
        accountEntity.setProductEntity(producto);
        accountRepository.save(accountEntity);

        return MessageAplication.ACCOUNTCREATED;
    }

    public Object crearCuentaCorriente(RequestAccountCustomerDto requestAccountCustomerDto) {

        //requestCuentaClienteDto.getProductoDto().setNumeroCuenta(null);

        requestAccountCustomerDto.getProductDto().setNumeroCuenta(generarNumeroCuentaAleatorio("33"));
        requestAccountCustomerDto.getProductDto().setEstado(requestAccountCustomerDto.getProductDto().getEstado());
        requestAccountCustomerDto.getProductDto().setFechaCreacion(LocalDateTime.now());

        ProductEntity producto = ProductMapper.dtoToProductoEntity(requestAccountCustomerDto.getProductDto());
        productRepository.save(producto);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setTipoCuenta(requestAccountCustomerDto.getTipoCuenta());
        accountEntity.setProductEntity(producto);
        accountRepository.save(accountEntity);

        return MessageAplication.ACCOUNTCREATED;
    }


    public String generarNumeroCuentaAleatorio(String prefijo) {
        Random random = new Random();
        int numeroAleatorio = 10000000 + random.nextInt(90000000);

        while(productRepository.existsByNumeroCuenta(prefijo+numeroAleatorio)){
            numeroAleatorio = 10000000 + random.nextInt(90000000);
        }
        return prefijo + numeroAleatorio;
    }
}
