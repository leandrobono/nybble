package com.nybble.nybble.services;

import com.jayway.jsonpath.JsonPath;
import com.nybble.nybble.model.Evento.Evento;
import com.nybble.nybble.model.Evento.EventoDao;
import com.nybble.nybble.repositories.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventoService
{
    @Value("${weatherstackkey}")
    String weatherKey;

    @Autowired
    EventoRepository eventoRepository;

    @Autowired
    ProveedorService proveedorService;

    public List<Evento> find() throws IOException {
        List<Evento> eventos = new ArrayList<>();
        
        List<EventoDao> eventoDaos = eventoRepository.findAll();
        if (eventoDaos != null) {
            for (int i = 0; i < eventoDaos.size(); i++) {
                if (eventoDaos.get(i).getLimite() > 0) {
                    Evento evento = daoToEntity(eventoDaos.get(i));
                    eventos.add(evento);
                }
            }
        }

        return eventos;
    }

    public Evento findById(int id) throws IOException {
        return daoToEntity(eventoRepository.findById(id));
    }

    public void reduceLimite(int id) {
        eventoRepository.reduceLimit(id);
    }

    public Evento daoToEntity(EventoDao eventoDao) throws IOException {
        Evento evento = new Evento();
        evento.setId(eventoDao.getId());
        evento.setCiudad(eventoDao.getCiudad());
        evento.setCosto(eventoDao.getCosto());
        evento.setFecha(eventoDao.getFecha());
        evento.setLimite(eventoDao.getLimite());
        evento.setPais(eventoDao.getPais());
        evento.setProveedor(proveedorService.daoToEntity(eventoDao.getProveedor()));

        evento.setTemperature(getTemperature(evento.getCiudad()));

        return evento;
    }

    public Integer getTemperature(String city) throws IOException {
        URL url = new URL("http://api.weatherstack.com/current?access_key=" + this.weatherKey + "&query=" + URLEncoder.encode(city));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        String str = content.toString();

        Integer temp = JsonPath.read(str, "$.current.temperature");

        return temp;
    }

    public EventoDao entityToDao(Evento evento) {
        EventoDao eventoDao = new EventoDao();
        eventoDao.setCiudad(evento.getCiudad());
        eventoDao.setCosto(evento.getCosto());
        eventoDao.setFecha(evento.getFecha());
        eventoDao.setId(evento.getId());
        eventoDao.setLimite(evento.getLimite());
        eventoDao.setPais(evento.getPais());
        eventoDao.setProveedor(proveedorService.entityToDao(proveedorService.findById(evento.getProveedor().getId())));

        return eventoDao;
    }
}