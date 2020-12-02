package com.example.servicioRest.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Campo nombre no puede estar vacio")
    @Size(min=5,max = 255, message = "Nombre minimo 5, maximo 255 caracteres")
    private String nombre;

    @NotBlank(message = "Campo Nit no puede estar vacio")
    @Size(min=5,max = 255, message = "Nit minimo 5 maximo 255 caracteres")
    private  String nit;

    @Temporal(TemporalType.DATE)
    @PastOrPresent
    @NotNull(message = "Fecha no puede estar vacio")
    private Date fechaFundacion;

    @Size(max = 255, message = "Nombre maximo 255 caracteres")
    private String direccion;

}
