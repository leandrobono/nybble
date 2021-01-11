package com.nybble.nybble.repositories;

import com.nybble.nybble.model.Usuario.UsuarioDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioDao, Long> {
    UsuarioDao findByEmail(String email);

    UsuarioDao findById(int id);
}