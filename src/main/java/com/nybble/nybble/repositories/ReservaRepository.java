package com.nybble.nybble.repositories;

import com.nybble.nybble.model.Reserva.ReservaDao;
import com.nybble.nybble.model.Usuario.UsuarioDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaDao, Long> {
    List<ReservaDao> findAll();
    List<ReservaDao> findAllByUsuario(UsuarioDao usuarioDao);
    ReservaDao findById(int id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM reservas WHERE id = :id",
            nativeQuery = true)
    void deleteReserva(@Param("id") int id);
}