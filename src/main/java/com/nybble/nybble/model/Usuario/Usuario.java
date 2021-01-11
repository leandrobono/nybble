package com.nybble.nybble.model.Usuario;

import com.nybble.nybble.model.Validations.Validation;
import com.nybble.nybble.model.Validations.ValidationObject;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

public class Usuario implements Validation
{
    public static final String NOMBRE_OBLIGATORIO = "El nombre debe ser obligatorio y debe tener menos de 100 caracteres";
    public static final String APELLIDO_OBLIGATORIO = "El apellido debe ser obligatorio y debe tener menos de 100 caracteres";
    public static final String EMAIL_OBLIGATORIO = "El email es obligatorio";
    public static final String EMAIL_INVALIDO = "El email no es tiene el formato correcto";
    public static final String EMAIL_EXISTENTE = "El email ya se encuentra registrado";

    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String passWord;
    private int rol;

    public Usuario(UsuarioForm usuarioForm){
        this.nombre = usuarioForm.getNombre();
        this.apellido = usuarioForm.getApellido();
        this.email = usuarioForm.getEmail();
        this.passWord = usuarioForm.getPassWord();
        this.rol = 1;
    }

    public Usuario() {}

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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    @Override
    public Optional<ValidationObject> validate() {
        if (nombre == null || nombre.length() > 100 || nombre.length() == 0) {
            return Optional.of(new ValidationObject(NOMBRE_OBLIGATORIO, HttpStatus.BAD_REQUEST));
        }

        if (apellido == null || apellido.length() == 0 || apellido.length() > 100){
            return Optional.of(new ValidationObject(APELLIDO_OBLIGATORIO, HttpStatus.BAD_REQUEST));
        }

        if (email == null  || email.length() == 0 || email.length() > 80) {
            return Optional.of(new ValidationObject(EMAIL_OBLIGATORIO, HttpStatus.BAD_REQUEST));
        }

        if (!isValidEmail(email)){
            return Optional.of(new ValidationObject(EMAIL_INVALIDO, HttpStatus.BAD_REQUEST));
        }

        return Optional.empty();
    }

    private boolean isValidEmail(String email) {
        String regex = "^[\\w-\\.+]*[\\w-\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}