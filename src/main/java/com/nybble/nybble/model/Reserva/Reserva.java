package com.nybble.nybble.model.Reserva;

import com.nybble.nybble.model.Evento.Evento;
import com.nybble.nybble.model.Proveedor.Proveedor;
import com.nybble.nybble.model.Usuario.UsuarioResponse;

import java.util.Date;

public class Reserva
{
    private int id;
    private UsuarioResponse usuario;
    private Evento evento;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UsuarioResponse getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResponse usuario) {
        this.usuario = usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
}