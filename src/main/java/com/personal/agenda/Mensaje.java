package com.personal.agenda;

import java.util.List;

public class Mensaje {
    private String tipoMensaje;
    private Evento evento;
    private List<Evento> eventos;
    private String resultado;

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "tipoMensaje='" + tipoMensaje + '\'' +
                ", evento=" + evento +
                ", resultado='" + resultado + '\'' +
                '}';
    }

//    {
//        "operation": "SELECT",
//            "data": {
//        "id": "12345",
//                "subject": "lsjfsljglksjgljglgkls",
//                "datetime": 1221324232355
//    }
//    }
}
