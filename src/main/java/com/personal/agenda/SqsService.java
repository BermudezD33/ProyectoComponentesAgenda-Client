package com.personal.agenda;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SqsService {
    public static final Logger LOGGER = LoggerFactory.getLogger(SqsService.class);

    private static final String QUEUE_REQUEST = "ColaRequest.fifo";
    private static final String QUEUE_RESPONSE = "ColaResponse.fifo";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    public void sendMessageToSqs(Mensaje mensaje) throws Exception {
        System.out.println("Sending the message to the Amazon sqs.");
        String mensajeJson = objectMapper.writeValueAsString(mensaje);
        Map<String, Object> headers = new HashMap<>();
        headers.put("message-group-id", "G1");
        headers.put("message-deduplication-id", String.valueOf(new Date().getTime()));
        //boolean estado;
        queueMessagingTemplate.convertAndSend(QUEUE_REQUEST, mensaje, headers);
        System.out.println("Message sent successfully to the Amazon sqs.");
    }

    public Mensaje receiveMessageFromSqs() throws Exception {
        String mensajeJson;
        int intentos = 0;
        do {
            Thread.sleep(500);
                mensajeJson = queueMessagingTemplate.receiveAndConvert(QUEUE_RESPONSE, String.class);
            intentos++;
        } while (mensajeJson == null && intentos <= 10);

        Mensaje mensaje;
        if (mensajeJson == null) {
            mensaje = new Mensaje();
            mensaje.setEventos(new ArrayList<>());
        } else {
            mensaje = objectMapper.readValue(mensajeJson, Mensaje.class);
        }
        return mensaje;
    }

}


//
//{
//        "tipoMensaje":"Create",
//        "evento":{
//        "id":"sdfsdfsg",
//        "fecha":1,
//        "asunto":"pruebaWeb1"
//        }
//        "resultado":"OK"
//        }
//
//        {
//        "tipoMensaje":"Retrieve",
//        "evento":{
//        "id":"sdfsdfsg",
//        "fecha":1,
//        "asunto":"pruebaWeb1"
//        }
//        "resultado":"OK"
//        }
