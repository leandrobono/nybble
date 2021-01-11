package com.nybble.nybble.model.Proveedor;

import com.nybble.nybble.model.Usuario.UsuarioForm;
import com.nybble.nybble.model.Validations.Validation;
import com.nybble.nybble.model.Validations.ValidationObject;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public class Proveedor
{
    private int id;
    private String nombre;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}