package com.nybble.nybble.model.Reserva;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DeleteReservaForm
{
    private List<Integer> reservas;

    public List<Integer> getReservas() {
        return reservas;
    }

    public void setReservas(List<Integer> reservas) {
        this.reservas = reservas;
    }
}