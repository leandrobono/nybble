package com.nybble.nybble.model.Evento;

import com.nybble.nybble.model.Proveedor.Proveedor;
import com.nybble.nybble.model.Usuario.UsuarioForm;
import com.nybble.nybble.model.Validations.Validation;
import com.nybble.nybble.model.Validations.ValidationObject;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.Optional;

public class Evento
{
    private int id;
    private Proveedor proveedor;
    private Integer limite;
    private Date fecha;
    private String ciudad;
    private String pais;
    private Float costo;
    private Integer temperature;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Integer getLimite() {
        return limite;
    }

    public void setLimite(Integer limite) {
        this.limite = limite;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Float getCosto() {
        return costo;
    }

    public void setCosto(Float costo) {
        this.costo = costo;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }
}