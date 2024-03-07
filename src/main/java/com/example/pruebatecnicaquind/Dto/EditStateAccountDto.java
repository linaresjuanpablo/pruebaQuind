package com.example.pruebatecnicaquind.Dto;

import com.example.pruebatecnicaquind.Entity.Audit;
import com.example.pruebatecnicaquind.Enum.StateAccount;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditStateAccountDto extends Audit {

    private String numeroCuenta;
    @Enumerated(EnumType.STRING)
    private StateAccount estado;
}
