package com.example.servicioRest.repositories;

import com.example.servicioRest.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

}
