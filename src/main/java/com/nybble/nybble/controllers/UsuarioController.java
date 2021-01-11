package com.nybble.nybble.controllers;

import com.nybble.nybble.model.JwtUser;
import com.nybble.nybble.model.Usuario.Usuario;
import com.nybble.nybble.model.Usuario.UsuarioDao;
import com.nybble.nybble.model.Usuario.UsuarioForm;
import com.nybble.nybble.model.Usuario.UsuarioLoginForm;
import com.nybble.nybble.model.Validations.ValidationObject;
import com.nybble.nybble.services.JwtService;
import com.nybble.nybble.services.UsuarioService;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class UsuarioController
{

    @Autowired
    private UsuarioService userService;

    @Autowired
    JwtService jwtService;

    Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestBody UsuarioLoginForm loginForm)
    {
        JSONObject entity = new JSONObject();

        String email = loginForm.getEmail();
        String passWord = loginForm.getPassword();
        try {
            UsuarioDao usuario = userService.authenticate(email, passWord);

            if (usuario != null) {
                JwtUser jwtUser = new JwtUser();
                jwtUser.setPassword(passWord);
                jwtUser.setEmail(email);
                jwtUser.setRole(usuario.getRol());
                jwtUser.setId(usuario.getId());

                entity.put("id", jwtUser.getId());
                entity.put("token", jwtService.getToken(jwtUser));
                return new ResponseEntity<Object>(entity, HttpStatus.OK);
            }
            return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);
        } catch (NoSuchAlgorithmException exp) {
            return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);
        } catch (UnsupportedEncodingException exp) {
            return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/usuario")
    public ResponseEntity<Object> postUsuario(@RequestBody UsuarioForm usuarioForm)
    {
        JSONObject entity = new JSONObject();

        try {
            Usuario usuario = new Usuario(usuarioForm);

            Optional<ValidationObject> responseError = usuario.validate();
            if (responseError.isPresent()){
                entity.put("error", responseError.get().getMessage());
                return new ResponseEntity<Object>(entity, responseError.get().getStatus());
            }

            if (userService.findUserByEmail(usuario.getEmail()) != null) {
                entity.put("error", Usuario.EMAIL_EXISTENTE);
                return new ResponseEntity<Object>(entity, HttpStatus.BAD_REQUEST);
            }

            Usuario usuarioSave = userService.saveUser(usuario);

            logger.info("User created on " + new Date());

            entity.put("message", "User saved!");
            entity.put("id", usuarioSave.getId());
            return new ResponseEntity<Object>(entity, HttpStatus.OK);

        } catch (Throwable e) {
            logger.error("ERROR: " + e.getMessage());

            entity.put("error", e.getMessage());
            return new ResponseEntity<Object>(entity, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}