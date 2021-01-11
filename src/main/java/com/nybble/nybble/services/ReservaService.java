package com.nybble.nybble.services;

import com.nybble.nybble.model.Evento.Evento;
import com.nybble.nybble.model.Reserva.Reserva;
import com.nybble.nybble.model.Reserva.ReservaDao;
import com.nybble.nybble.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class ReservaService
{

    @Autowired
    ReservaRepository reservaRepository;

    @Autowired
    EventoService eventoService;

    @Autowired
    UsuarioService usuarioService;

    public List<Reserva> getReservas(int id) throws IOException {
        List<Reserva> reservas = new ArrayList<>();

        List<ReservaDao> reservaDaos = reservaRepository.findAllByUsuario(usuarioService.entityToDao(usuarioService.findUserById(id)));
        if (reservaDaos != null) {
            for (int i = 0; i < reservaDaos.size(); i++) {
                if (reservaDaos.get(i).getEvento().getFecha().compareTo(new Date()) >= 0) {
                    Reserva reserva = daoToEntity(reservaDaos.get(i));
                    reservas.add(reserva);
                }
            }
        }

        return reservas;
    }

    public List<Reserva> getAllReservas(Integer proveedor, Date desde, Date hasta) throws IOException {
        List<Reserva> reservas = new ArrayList<>();

        List<ReservaDao> reservaDaos = reservaRepository.findAll();
        if (reservaDaos != null) {
            for (int i = 0; i < reservaDaos.size(); i++) {
                if (proveedor == 0 || proveedor == reservaDaos.get(i).getEvento().getProveedor().getId()) {
                    Boolean add = false;
                    if (desde != null && hasta != null) {
                        if (reservaDaos.get(i).getEvento().getFecha().compareTo(desde) >= 0 &&
                            reservaDaos.get(i).getEvento().getFecha().compareTo(hasta) <= 0) {
                            add = true;
                        } else {
                            add = false;
                        }
                    } else {
                        add = true;
                    }

                    if (add) {
                        Reserva reserva = daoToEntity(reservaDaos.get(i));
                        reservas.add(reserva);
                    }
                }
            }
        }

        return reservas;
    }

    public String saveReservas(List<Integer> eventos, Integer usuario) throws IOException {
        String response = "";

        if (eventos != null) {
            for (int i = 0; i < eventos.size(); i++) {
                Evento evento = eventoService.findById(eventos.get(i));
                if (evento.getLimite() > 0) {
                    ReservaDao reservaDao = new ReservaDao();
                    reservaDao.setEvento(eventoService.entityToDao(evento));
                    reservaDao.setUsuario(usuarioService.entityToDao(usuarioService.findUserById(usuario)));
                    ReservaDao reservaDao1 = reservaRepository.save(reservaDao);

                    eventoService.reduceLimite(evento.getId());

                    Locale locale = new Locale("es", "AR");
                    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
                    String date = dateFormat.format(reservaDao.getEvento().getFecha());

                    response += "Su número de ticket para el viaje del " + date + " a " + reservaDao.getEvento().getCiudad() + " es: " + reservaDao1.getId() + "." + "\n";
                }
            }
        }

        return response;
    }

    public Reserva findById(int id) throws IOException {
        return daoToEntity(reservaRepository.findById(id));
    }

    public void deleteReservas(List<Integer> reservas, Integer usuario) throws IOException {
        if (reservas != null) {
            for (int i = 0; i < reservas.size(); i++) {
                Reserva reserva = this.findById(reservas.get(i));

                reservaRepository.deleteReserva(reserva.getId());

                eventoService.updateLimite(reserva.getEvento().getId());
            }
        }
    }

    public Reserva daoToEntity(ReservaDao reservaDao) throws IOException {
        Reserva reserva = new Reserva();
        reserva.setEvento(eventoService.daoToEntity(reservaDao.getEvento()));
        reserva.setId(reservaDao.getId());
        reserva.setUsuario(usuarioService.daoToEntity(reservaDao.getUsuario()));

        return reserva;
    }
}