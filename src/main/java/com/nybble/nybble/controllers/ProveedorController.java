package com.nybble.nybble.controllers;

import com.nybble.nybble.model.Proveedor.Proveedor;
import com.nybble.nybble.services.ProveedorService;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProveedorController
{

    @Autowired
    ProveedorService proveedorService;

    Logger logger = LoggerFactory.getLogger(ProveedorController.class);

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