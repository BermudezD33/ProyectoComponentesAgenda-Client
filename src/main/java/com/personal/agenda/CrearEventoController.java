package com.personal.agenda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class CrearEventoController {

    @Autowired
    private SqsService sqsService;

    @GetMapping("/crearEvento")
    public String mostrar(Model model) {
        model.addAttribute("mensajeForm", new MensajeForm());
        model.addAttribute("actionUrl", "/crearEvento");
        return "crearEvento";
    }


    @PostMapping("/crearEvento")
    public String crearEvento(@ModelAttribute MensajeForm mensajeForm, Model model) throws Exception {
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
        evento.setFecha(fecha.getTime());
        mensaje.setTipoMensaje("Create");
        mensaje.setEvento(evento);

        sqsService.sendMessageToSqs(mensaje);

        return "redirect:/mostrarAgenda";

    }

}

