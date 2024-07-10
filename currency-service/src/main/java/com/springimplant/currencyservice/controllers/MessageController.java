package com.springimplant.currencyservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springimplant.currencyservice.contract.User;
import com.springimplant.currencyservice.producer.KafkaJsonProducer;
import com.springimplant.currencyservice.producer.KafkaProducer;

@RestController
@RequestMapping("/api/v1/kafka")
public class MessageController {
	
	private KafkaProducer kafkaProducer;
    private KafkaJsonProducer kafkaJsonProducer;

    public MessageController(KafkaProducer kafkaProducer, KafkaJsonProducer kafkaJsonProducer) {
        this.kafkaProducer = kafkaProducer;
        this.kafkaJsonProducer = kafkaJsonProducer;
    }

    @GetMapping("/publish")
    public ResponseEntity<String> publishMessage(@RequestParam("message") String message) {
        kafkaProducer.sendMessage(message);
        return ResponseEntity.ok("Message published to the topic");
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publishJson(@RequestBody User user) {
        kafkaJsonProducer.publishMessage(user);
        return ResponseEntity.ok("JSON Message published successfully!");
    }
}
