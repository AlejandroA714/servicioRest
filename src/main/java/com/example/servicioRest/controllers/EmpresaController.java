package com.example.servicioRest.controllers;

import com.example.servicioRest.entities.Empresa;
import com.example.servicioRest.utils.Result;
import com.example.servicioRest.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/Empresa")
public class EmpresaController {

    @Autowired
    EmpresaRepository empresaRepository;

    @GetMapping("/")
    public ResponseEntity<Result> obtenerEmpresas(){
        try{
           List<Empresa> empresas = empresaRepository.findAll();
           return ResponseEntity.ok(new Result(true,empresas));
        }catch (Exception e){
            return ResponseEntity.ok(new Result(false,String.format("¡Error Fallo debido a: %s!", e.getMessage())));
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<Result> obtenerEmpresa(@PathVariable Integer id){
        try{
            Optional<Empresa> empresa = Optional.of(empresaRepository.getOne(id));
            if (!empresa.isPresent()){ throw  new Exception("No existe empresa con ese id");}
            return ResponseEntity.ok(new Result(true,empresa));
        }catch (Exception e){
            return ResponseEntity.ok(new Result(false,String.format("¡Error Fallo debido a: %s!", e.getMessage())));
        }
    }

    @PostMapping("/")
    public ResponseEntity<Result> insertarEmpresa(@Valid @RequestBody Empresa empresa){
        try{
            empresaRepository.save(empresa);
            return ResponseEntity.ok(new Result(true,empresa));
        }catch (Exception e){
            return ResponseEntity.ok(new Result(false,String.format("¡Error Fallo debido a: %s!", e.getMessage())));
        }
    }

    @PutMapping("/")
    private ResponseEntity<Result> actualizarEmpresa(@Valid @RequestBody Empresa empresa){
        try{
            empresaRepository.save(empresa);
            return ResponseEntity.ok(new Result(true,empresa));
        }catch (Exception e){
            return ResponseEntity.ok(new Result(false,String.format("¡Error Fallo debido a: %s!", e.getMessage())));
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Result> eliminarEmpresa(@PathVariable Integer id){
        try{
            empresaRepository.deleteById(id);
            return ResponseEntity.ok(new Result(true,"¡Exito! Empresa eliminada"));
        }catch (Exception e){
            return ResponseEntity.ok(new Result(false,String.format("¡Error Fallo debido a: %s!", e.getMessage())));
        }

    }

    @DeleteMapping("/")
    private ResponseEntity<Result> eliminarEmpresa(@Valid @RequestBody Empresa empresa){
        try{
            empresaRepository.delete(empresa);
            return ResponseEntity.ok(new Result(true,"¡Exito! Empresa eliminada"));
        }catch (Exception e){
            return ResponseEntity.ok(new Result(false,String.format("¡Error Fallo debido a: %s!", e.getMessage())));
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<Result> devolerErrores(MethodArgumentNotValidException ex){
        List<ObjectError> erros = ex.getBindingResult().getAllErrors();
        String ErrorStr = "";
        for(ObjectError error:erros){
            ErrorStr += error.getDefaultMessage() + ",";
        }
        return ResponseEntity.ok(new Result(false,ErrorStr));
    }

}
