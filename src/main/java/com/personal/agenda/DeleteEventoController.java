package com.personal.agenda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class DeleteEventoController {

    @Autowired
    private SqsService sqsService;

    @GetMapping("/deleteEvento/{id}")
    public String mostrar(@PathVariable(value = "id") String id,@ModelAttribute MensajeForm mensajeForm,
                          Model model) throws Exception{
        model.addAttribute("mensajeForm", new MensajeForm());
        Mensaje mensaje = new Mensaje();
        Evento evento = new Evento();
        evento.setId(id);
        mensaje.setTipoMensaje("Delete");
        mensaje.setEvento(evento);

        sqsService.sendMessageToSqs(mensaje);

        Mensaje mensajeRespuesta = sqsService.receiveMessageFromSqs();

        return "redirect:/mostrarAgenda";

    }


}
