package com.example.pruebatecnicaquind.Mapper;

import com.example.pruebatecnicaquind.Dto.RequestAccountCustomerDto;
import com.example.pruebatecnicaquind.Entity.AccountEntity;
import com.example.pruebatecnicaquind.Entity.ProductEntity;

import java.util.List;
import java.util.stream.Collectors;

public class AccountMapper {

    public static AccountEntity dtoToCuentaEntity(RequestAccountCustomerDto requestAccountCustomerDto){
        ProductEntity productEntity = ProductMapper.dtoToProductoEntity(requestAccountCustomerDto.getProductDto());

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setProductEntity(productEntity);
        accountEntity.setTipoCuenta(requestAccountCustomerDto.getTipoCuenta());

     return accountEntity;
    }

    public static List<AccountEntity> listDtoToListEntity(List<RequestAccountCustomerDto> cuentasDto) {
        return cuentasDto.stream()
                .map(AccountMapper::dtoToCuentaEntity)
                .collect(Collectors.toList());
    }

}
