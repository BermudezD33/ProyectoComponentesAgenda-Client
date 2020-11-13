package com.personal.agenda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MostrarAgendaController {

    @Autowired
    private SqsService sqsService;

    @GetMapping("/mostrarAgenda")
    public String mostrar(
            @RequestParam(name = "anio", required = false, defaultValue = "2020") String anio,
            @RequestParam(name = "mes", required = false, defaultValue = "1") String mes,
            @RequestParam(name = "dia", required = false, defaultValue = "1") String dia,
            Model model) throws Exception {
        model.addAttribute("anio", anio);
        model.addAttribute("mes", mes);
        model.addAttribute("dia", dia);

        System.out.println(model);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String fechaCompleta = dia + "-" +
                mes + "-" +
                anio + " " +
                 "00:00:00" ;
        Date fecha = formatter.parse(fechaCompleta);

        Mensaje mensaje = new Mensaje();
        mensaje.setTipoMensaje("RetrieveByDay");
        Evento evento = new Evento();
        evento.setFecha(fecha.getTime());
        mensaje.setEvento(evento);
        sqsService.sendMessageToSqs(mensaje);

        Mensaje mensajeRespuesta = sqsService.receiveMessageFromSqs();

        model.addAttribute("eventos", mensajeRespuesta.getEventos() );

        return "mostrarAgenda";
    }

}