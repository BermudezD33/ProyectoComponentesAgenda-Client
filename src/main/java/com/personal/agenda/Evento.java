package com.personal.agenda;

public class Evento {

    private String id;
    private Long fecha;
    private String asunto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getFecha() {
        return fecha;
    }

    public void setFecha(Long fecha) {
        this.fecha = fecha;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    @Override
    public String toString() {
        return "Evento{" +
                 " Id ='" + id  +
                ", Fecha =" + fecha +
                ", Asunto =" + asunto +
                '}';
    }

}
