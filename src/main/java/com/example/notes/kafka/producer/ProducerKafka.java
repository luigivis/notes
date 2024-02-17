package com.example.notes.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ProducerKafka {
  @Autowired private KafkaTemplate<String, String> kafkaTemplate;
  @Autowired private ObjectMapper objectMapper;

  @Async
  public void sendMessage(String topic, Object msg) {
    Thread.startVirtualThread(
        () -> {
          try {
            kafkaTemplate.send(topic, objectMapper.writeValueAsString(msg));
          } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
          }
        });
  }
}
