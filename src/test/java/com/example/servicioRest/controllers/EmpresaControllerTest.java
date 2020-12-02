package com.example.servicioRest.controllers;

import com.example.servicioRest.entities.Empresa;
import com.example.servicioRest.repositories.EmpresaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.test.properties")
public class EmpresaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    Logger logger = LoggerFactory.getLogger(EmpresaControllerTest.class);

    @Test
    public void obtenerEmpresas() throws Exception  {
        mockMvc.perform(
                get("/Empresa/")
                    .contentType("application/json"))
        .andExpect(status().isOk())
        .andDo(print());
    }

    @Test
    public void insertUser400() throws Exception {

        String content = objectMapper.writeValueAsString(null);

        mockMvc.perform(
                post("/Empresa/")
                        .contentType("application/json")
                        .content(content))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void inserEmpresa200() throws Exception {

        int id = empresaRepository.findAll().size() + 1;
        String content = objectMapper.writeValueAsString(new Empresa( null,"Empresa " + id, "0614-140700-120-0",new Date(),"Apopitha"));

        mockMvc.perform(
                post("/Empresa/")
                        .contentType("application/json")
                        .content(content))
                .andExpect(status().isOk());
    }

    @Test
    public void inserEmpresaPartial() throws Exception {

        int id = empresaRepository.findAll().size() + 1;
        String content = objectMapper.writeValueAsString(new Empresa( null,"Empresa " + id, "0614-140700-120-0",null,"Apopitha"));

        mockMvc.perform(
                post("/Empresa/")
                        .contentType("application/json")
                        .content(content))
                .andExpect(status().isOk());
    }


}//Class


// print() content() status()