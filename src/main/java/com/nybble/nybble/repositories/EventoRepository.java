package com.nybble.nybble.repositories;

import com.nybble.nybble.model.Evento.EventoDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<EventoDao, Long> {
    List<EventoDao> findAll();

    EventoDao findById(int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE eventos SET limite = limite -1 WHERE id = :evento",
            nativeQuery = true)
    void reduceLimit(@Param("evento") int evento);

    @Modifying
    @Transactional
    @Query(value = "UPDATE eventos SET limite = limite +1 WHERE id = :evento",
            nativeQuery = true)
    void updateLimit(@Param("evento") int evento);
}