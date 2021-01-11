package com.nybble.nybble.controllers;

import com.nybble.nybble.model.JwtUser;
import com.nybble.nybble.model.Reserva.Reserva;
import com.nybble.nybble.model.Reserva.ReservaForm;
import com.nybble.nybble.services.JwtService;
import com.nybble.nybble.services.ReservaService;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservasController
{
    @Autowired
    JwtService jwtService;

    @Autowired
    ReservaService reservaService;

    @Autowired
    JwtService jwtTokenService;

    Logger logger = LoggerFactory.getLogger(ReservasController.class);

    @PostMapping(value = "/reserva")
    public ResponseEntity<Object> postReserva(
            @RequestHeader(name = "x-auth-token") String token,
            @RequestBody ReservaForm reservaForm
            )
    {
        JSONObject entity = new JSONObject();
        try {
            JwtUser jwtUser = jwtTokenService.getUser(token);

            reservaService.saveReservas(reservaForm.getEventos(), jwtUser.getId());

            entity.put("message", "Reservas guardadas");
            return new ResponseEntity<Object>(entity, HttpStatus.OK);
        } catch (Throwable e) {
            logger.error("ERROR: " + e.getMessage());

            entity.put("error", e.getMessage());
            return new ResponseEntity<Object>(entity, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/reserva")
    public ResponseEntity<Object> getReservas(
            @RequestHeader(name = "x-auth-token") String token
    )
    {
        JSONObject entity = new JSONObject();
        try {
            JwtUser jwtUser = jwtTokenService.getUser(token);

            List<Reserva> reservas = reservaService.getReservas(jwtUser.getId());

            entity.put("reservas", reservas);
            return new ResponseEntity<Object>(entity, HttpStatus.OK);
        } catch (Throwable e) {
            logger.error("ERROR: " + e.getMessage());

            entity.put("error", e.getMessage());
            return new ResponseEntity<Object>(entity, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}