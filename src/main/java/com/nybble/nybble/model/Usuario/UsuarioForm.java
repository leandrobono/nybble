package com.nybble.nybble.model.Usuario;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UsuarioForm
{
    private String nombre;
    private String apellido;
    private String email;
    private String passWord;
    private Integer rol;
    private Integer area;
    private List<Integer> proyectos;
    private Integer nomina;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Integer getRol() {
        return rol;
    }

    public void setRol(Integer role) {
        this.rol = role;
    }

    public List<Integer> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Integer> proyectos) {
        this.proyectos = proyectos;
    }

    public Integer getNomina() {
        return nomina;
    }

    public void setNomina(Integer nomina) {
        this.nomina = nomina;
    }
}