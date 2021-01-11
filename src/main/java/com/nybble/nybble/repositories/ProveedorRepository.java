package com.nybble.nybble.repositories;

import com.nybble.nybble.model.Proveedor.ProveedorDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorDao, Long> {
    List<ProveedorDao> findAll();

    ProveedorDao findById(int id);
}