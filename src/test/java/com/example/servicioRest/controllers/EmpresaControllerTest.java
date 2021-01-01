package com.example.servicioRest.controllers;

import com.example.servicioRest.entities.Empresa;
import com.example.servicioRest.repositories.EmpresaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MimeTypeUtils;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest //Crea un contexto para nuestra applicacion
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.test.properties")
public class EmpresaControllerTest {

    //BeforeEach - Se ejecuta antes de cada @Test
    //AfterEach - Se ejecuta despues de cada @Tes
    //BeforeALl - Se ejecuta al inicio de la clase
    //AfterAll - Se ejecuta despues de la clase

    /*
            @ParameterizedTest(name)
            @DisplayName
        - Sources para ParameterizedTest
            @ValueSource(array{})
            @CsvSource(delimiter=";",value=data{"data;123;123;123"})
            @EnumSource(value=TimeUnit.class,names={})
            @MethodSource("methodName") ; method must return Stream<Arguments>

     */

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpresaRepository empresaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    Logger logger = LoggerFactory.getLogger(EmpresaControllerTest.class);

    /* @ParameterizedTest(name="Test con parametrizaciones")
    @ValueSource(strings = {"hola","mundo","esta","es","una","prueba","parametrizada"})
    public void Prueba(String string){
       logger.error(() -> string);
    }
    */


    @Test
    public void obtenerEmpresasShouldSuccess() throws Exception  {
        mockMvc.perform(
                get("/Empresa/")
                    .contentType("application/json"))
        .andExpect(status().isOk())
        .andDo(print());
    }

    @Test
    public void obtenerEmpresasShouldFail() throws Exception {

        mockMvc.perform(
                get("/Empresa/")
                    .accept(MimeTypeUtils.APPLICATION_XML_VALUE)) //Invalid Accept Type, only works with application/json
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void getByIdShouldReturnOptional() throws  Exception {

        doReturn(Optional.empty()).when(empresaRepository).findById(-1);

        mockMvc.perform(
                get("/Empresa/-1")
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[?(@.success == false)]").exists())
           .andDo(print());

    }

    @Test
    public void getByIdShouldReturnObject() throws Exception {

        when(empresaRepository.findById(1)).thenReturn(Optional.of(new Empresa(1, "Empresa 1", "0614-140700-120-0", new Date(), "Apopitha")));

        mockMvc.perform(
                get("/Empresa/1")
                    .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.data.length()").value(empresaRepository.findAll().size()))
                .andExpect(jsonPath("$.data[?(@.nombre == 'Empresa 1')]").exists())
                .andDo(print());
    }

    @Test
    public void insertUser400() throws Exception {

        String content = objectMapper.writeValueAsString(null);

        mockMvc.perform(
                post("/Empresa/")
                        .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void inserEmpresaSholudReturnSuccess() throws Exception {

        int id = empresaRepository.findAll().size() + 1;
        String content = objectMapper.writeValueAsString(new Empresa( null,"Empresa " + id, "0614-140700-120-0",new Date(),"Apopitha"));

        mockMvc.perform(
                post("/Empresa/")
                        .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.success == true)]").exists());
    }

    public void inserEmpresaSholudReturnFailure() throws Exception {

        int id = empresaRepository.findAll().size() + 1;
        String content = objectMapper.writeValueAsString(new Empresa( null,"Empresa " + id, "0614-140700-120-0",null,"Apopitha"));

        mockMvc.perform(
                post("/Empresa/")
                        .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.success == false)]").exists());

    }

}//Class


// print() content() status()