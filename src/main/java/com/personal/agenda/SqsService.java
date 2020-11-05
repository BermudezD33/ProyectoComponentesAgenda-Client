package com.personal.agenda;

import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SqsService {
    private static final String QUEUE_REQUEST = "ColaRequest.fifo";
    private static final String QUEUE_RESPONSE = "ColaResponse.fifo";

    public static final Logger LOGGER = LoggerFactory.getLogger(SqsService.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    public void sendMessageToSqs(final Message message) {
        System.out.println("Sending the message to the Amazon sqs.");
        queueMessagingTemplate.convertAndSend(QUEUE_REQUEST, message);
        System.out.println("Message sent successfully to the Amazon sqs.");
    }

    @SqsListener(value = QUEUE_RESPONSE, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void getMessageFromSqs(String message, @Header("MessageId") String messageId) throws Exception {
        System.out.println("Mensaje recibido: " + message);

        Mensaje mensaje = objectMapper.readValue(message, Mensaje.class);
        String retornable;
        System.out.println(mensaje.toString());
        //try {
        switch (mensaje.getTipoMensaje()) {
            case "Create":
                if (mensaje.getResultado().equals("Success")) {
                    System.out.println("Estado Create: " + mensaje.getResultado());
                }
                break;
            case "Retrieve":
                if (mensaje.getResultado().equals("Success")) {
//
//                        Evento event = dynamoDBMapper.load(Evento.class, mensaje.getEvento().getId());
                    System.out.println("Estado Retrieve: " + mensaje.getResultado());
                }
                break;
            case "Update":
                if (mensaje.getResultado().equals("Success")) {
//                        dynamoDBMapper.save(mensaje.getEvento());
                    System.out.println("Estado Update: " + mensaje.getResultado());
                }
                break;
            case "Delete":
                if (mensaje.getResultado().equals("Success")) {
//                        dynamoDBMapper.delete(mensaje.getEvento());
                    System.out.println("Estado Delete: " + mensaje.getResultado());
                }
                break;
            default:
                throw new Exception("No se pude realizar ninguna accion con ese mensaje");
        }
        //mensaje.setResultado("Success");


        // } catch (Exception exception) {
        // mensaje.setResultado("Fail");
        // }
        retornable = objectMapper.writeValueAsString(mensaje);
        System.out.println(retornable);

       // Map<String, Object> headers = new HashMap<>();
        //headers.put("message-group-id", "G2");
       // queueMessagingTemplate.convertAndSend(QUEUE_RESPONSE, retornable, headers);
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
