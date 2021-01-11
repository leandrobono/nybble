package com.nybble.nybble.model.Reserva;

import com.nybble.nybble.model.Evento.EventoDao;
import com.nybble.nybble.model.Proveedor.ProveedorDao;
import com.nybble.nybble.model.Usuario.UsuarioDao;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="reservas")
public class ReservaDao {
    @Id
    @GeneratedValue()
    private int id;

    @OneToOne
    @JoinColumn(name = "usuario", referencedColumnName = "id")
    private UsuarioDao usuario;

    @OneToOne
    @JoinColumn(name = "evento", referencedColumnName = "id")
    private EventoDao evento;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UsuarioDao getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDao usuario) {
        this.usuario = usuario;
    }

    public EventoDao getEvento() {
        return evento;
    }

    public void setEvento(EventoDao evento) {
        this.evento = evento;
    }
}
