package com.example.pruebatecnicaquind.Entity;

import com.example.pruebatecnicaquind.Enum.StateAccount;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountEntity> cuenta;

    @Column(name = "numero_cuenta",unique = true, nullable = false)
    private String numeroCuenta;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_cuenta")
    private StateAccount estado;

    @Column(name = "saldo_cuenta")
    private BigDecimal saldo;

    @Column(name = "exenta_gmf")
    private boolean exentaGMF;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private CustomerEntity customerEntity;


}
