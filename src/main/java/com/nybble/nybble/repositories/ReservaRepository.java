package com.nybble.nybble.repositories;

import com.nybble.nybble.model.Reserva.ReservaDao;
import com.nybble.nybble.model.Usuario.UsuarioDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaDao, Long> {
    List<ReservaDao> findAll();
    List<ReservaDao> findAllByUsuario(UsuarioDao usuarioDao);
}