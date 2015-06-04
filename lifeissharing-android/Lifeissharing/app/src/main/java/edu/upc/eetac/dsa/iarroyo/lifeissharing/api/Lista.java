package edu.upc.eetac.dsa.iarroyo.lifeissharing.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nacho on 30/05/15.
 */
public class Lista {

    private int id;
    private String nombre;

    public long getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(long fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getUltima_modificacion() {
        return ultima_modificacion;
    }

    public void setUltima_modificacion(long ultima_modificacion) {
        this.ultima_modificacion = ultima_modificacion;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    private long fecha_creacion;
    private long ultima_modificacion;
    private String creador;
    private Map<String, Link> links = new HashMap<String, Link>();



}
