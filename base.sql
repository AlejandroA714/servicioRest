CREATE DATABASE empresaTest;
USE empresaTest;

CREATE TABLE empresa(
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255),
    nit VARCHAR(32),
    fecha_Fundacion DATE,
    direccion VARCHAR(255)
)
