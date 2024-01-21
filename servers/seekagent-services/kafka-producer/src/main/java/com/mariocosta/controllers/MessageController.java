package com.mariocosta.controllers;


import com.mariocosta.clients.kafka.dto.MessageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/publisher")
public class MessageController {


    private KafkaTemplate<String, String> kafkaTemplate;

    public MessageController(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public void publicar(@RequestBody MessageRequest messageRequest)
    {
        if (!(messageRequest.message() == null || messageRequest.message().isEmpty()))
           kafkaTemplate.send("NewArtist", messageRequest.message());
    }
}
