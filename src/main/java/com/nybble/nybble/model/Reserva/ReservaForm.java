package com.nybble.nybble.model.Reserva;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReservaForm
{
    private List<Integer> eventos;

    public List<Integer> getEventos() {
        return eventos;
    }

    public void setEventos(List<Integer> eventos) {
        this.eventos = eventos;
    }
}