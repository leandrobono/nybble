package com.nybble.nybble.model.Evento;

import com.nybble.nybble.model.Proveedor.ProveedorDao;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="eventos")
public class EventoDao {
    @Id
    @GeneratedValue()
    private int id;

    @OneToOne
    @JoinColumn(name = "proveedor", referencedColumnName = "id")
    private ProveedorDao proveedor;

    private Integer limite;
    private Date fecha;
    private String ciudad;
    private String pais;
    private float costo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProveedorDao getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorDao proveedor) {
        this.proveedor = proveedor;
    }

    public Integer getLimite() {
        return limite;
    }

    public void setLimite(Integer limite) {
        this.limite = limite;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }
}
