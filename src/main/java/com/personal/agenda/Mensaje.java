package com.personal.agenda;

public class Mensaje {
    private String tipoMensaje;
    private Evento evento;

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    private String resultado;

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

    @Override
    public String toString() {
        return "Mensaje{" +
                "tipoMensaje='" + tipoMensaje + '\'' +
                ", evento=" + evento.toString() +
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
