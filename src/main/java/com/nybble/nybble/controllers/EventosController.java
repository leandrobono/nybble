package com.nybble.nybble.controllers;

import com.nybble.nybble.model.Evento.Evento;
import com.nybble.nybble.model.JwtUser;
import com.nybble.nybble.model.Proveedor.Proveedor;
import com.nybble.nybble.model.Reserva.Reserva;
import com.nybble.nybble.model.Usuario.UsuarioLoginForm;
import com.nybble.nybble.services.EventoService;
import com.nybble.nybble.services.JwtService;
import com.nybble.nybble.services.ProveedorService;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventosController
{
    @Autowired
    JwtService jwtService;

    @Autowired
    EventoService eventoService;

    @Autowired
    ProveedorService proveedorService;

    Logger logger = LoggerFactory.getLogger(EventosController.class);

    @GetMapping(value = "/eventos")
    public ResponseEntity<Object> getEventos()
    {
        JSONObject entity = new JSONObject();
        try {
            List<Evento> eventos = eventoService.find();

            entity.put("eventos", eventos);
            return new ResponseEntity<Object>(entity, HttpStatus.OK);
        } catch (Throwable e) {
            logger.error("ERROR: " + e.getMessage());

            entity.put("error", e.getMessage());
            return new ResponseEntity<Object>(entity, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/proveedor")
    public ResponseEntity<Object> getProveedor(
            @RequestHeader(name = "x-auth-token") String token
    )
    {
        JSONObject entity = new JSONObject();
        try {
            List<Proveedor> proveedors = proveedorService.find();

            entity.put("proveedores", proveedors);
            return new ResponseEntity<Object>(entity, HttpStatus.OK);
        } catch (Throwable e) {
            logger.error("ERROR: " + e.getMessage());

            entity.put("error", e.getMessage());
            return new ResponseEntity<Object>(entity, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}