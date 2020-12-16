package com.personal.agenda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Controller
public class UpdateEventoController {

    @Autowired
    private SqsService sqsService;

    @GetMapping("/crearEvento/{id}")
    public String mostrar(@PathVariable(value = "id") String id, Model model) throws Exception {
        Mensaje mensaje = new Mensaje();
        Evento evento = new Evento();
        evento.setId(id);
        mensaje.setTipoMensaje("Retrieve");
        mensaje.setEvento(evento);
        sqsService.sendMessageToSqs(mensaje);

        Mensaje mensajeRespuesta = sqsService.receiveMessageFromSqs();
        long fechaSegundos = mensajeRespuesta.getEvento().getFecha();
        Date date = new Date(fechaSegundos);
        LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());

        MensajeForm mensajeForm = new MensajeForm();
        mensajeForm.setAsunto(mensajeRespuesta.getEvento().getAsunto());
        mensajeForm.setDropdownDia(String.valueOf(dateTime.getDayOfMonth()));
        mensajeForm.setDropdownMes(String.valueOf(dateTime.getMonth()));
        mensajeForm.setDropdownAnio(String.valueOf(dateTime.getYear()));
        mensajeForm.setDropdownHora(String.valueOf(dateTime.getHour()));
        mensajeForm.setDropdownMinuto(String.valueOf(dateTime.getMinute()));

        model.addAttribute("mensajeForm", new MensajeForm());
        model.addAttribute("asunto", mensajeRespuesta.getEvento().getAsunto());
        model.addAttribute("hora", dateTime.getHour());
        model.addAttribute("minuto", dateTime.getMinute());
        model.addAttribute("anio", dateTime.getYear());
        model.addAttribute("mes", dateTime.getMonth());
        model.addAttribute("dia", dateTime.getDayOfMonth());
        model.addAttribute("actionUrl", "/crearEvento/" + id);
        return "crearEvento";
    }


    @PostMapping("/crearEvento/{id}")
    public String crearEvento(@PathVariable(value = "id") String id, @ModelAttribute MensajeForm mensajeForm, Model model) throws Exception {
        String asunto = mensajeForm.getAsunto();
        model.addAttribute("mensajeForm", mensajeForm);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String fechaCompleta = mensajeForm.getDropdownDia() + "-" +
                mensajeForm.getDropdownMes() + "-" +
                mensajeForm.getDropdownAnio() + " " +
                mensajeForm.getDropdownHora() + ":" + mensajeForm.getDropdownMinuto();
        Date fecha = formatter.parse(fechaCompleta);


        Mensaje mensaje = new Mensaje();
        Evento evento = new Evento();
        evento.setAsunto(asunto);
        evento.setId(id);
        evento.setFecha(fecha.getTime());
        mensaje.setTipoMensaje("Update");
        mensaje.setEvento(evento);

        sqsService.sendMessageToSqs(mensaje);

        return "redirect:/mostrarAgenda";
    }
}
