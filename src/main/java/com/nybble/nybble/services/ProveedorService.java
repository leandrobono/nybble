package com.nybble.nybble.services;

import com.nybble.nybble.model.Proveedor.Proveedor;
import com.nybble.nybble.model.Proveedor.ProveedorDao;
import com.nybble.nybble.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProveedorService
{

    @Autowired
    ProveedorRepository proveedorRepository;

    public List<Proveedor> find()
    {
        List<Proveedor> proveedors = new ArrayList<>();

        List<ProveedorDao> proveedorDaos = proveedorRepository.findAll();
        if (proveedorDaos != null) {
            for (int i = 0; i < proveedorDaos.size(); i++) {
                Proveedor proveedor = daoToEntity(proveedorDaos.get(i));
                proveedors.add(proveedor);
            }
        }

        return proveedors;
    }

    public Proveedor findById(int id) {
        return daoToEntity(proveedorRepository.findById(id));
    }

    public ProveedorDao entityToDao(Proveedor proveedor) {
        ProveedorDao proveedorDao = new ProveedorDao();
        proveedorDao.setId(proveedor.getId());
        proveedorDao.setNombre(proveedor.getNombre());

        return proveedorDao;
    }

    public Proveedor daoToEntity(ProveedorDao proveedorDao)
    {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(proveedorDao.getId());
        proveedor.setNombre(proveedorDao.getNombre());

        return proveedor;
    }
}