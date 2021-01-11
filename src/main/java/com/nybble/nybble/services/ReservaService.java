package com.nybble.nybble.services;

import com.nybble.nybble.model.Evento.Evento;
import com.nybble.nybble.model.Reserva.Reserva;
import com.nybble.nybble.model.Reserva.ReservaDao;
import com.nybble.nybble.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                Reserva reserva = daoToEntity(reservaDaos.get(i));
                reservas.add(reserva);
            }
        }

        return reservas;
    }

    public void saveReservas(List<Integer> eventos, Integer usuario) throws IOException {
        if (eventos != null) {
            for (int i = 0; i < eventos.size(); i++) {
                Evento evento = eventoService.findById(eventos.get(i));
                if (evento.getLimite() > 0) {
                    ReservaDao reservaDao = new ReservaDao();
                    reservaDao.setEvento(eventoService.entityToDao(evento));
                    reservaDao.setUsuario(usuarioService.entityToDao(usuarioService.findUserById(usuario)));
                    reservaRepository.save(reservaDao);

                    eventoService.reduceLimite(evento.getId());
                }
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