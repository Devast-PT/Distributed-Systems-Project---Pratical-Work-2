package com.mariocosta.clients.kafka;

import com.mariocosta.clients.kafka.dto.MessageRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        value = "kafka",
        path = "api/v1/publisher"
)
public interface KakfaClient {

   @PostMapping
   void publicar(@RequestBody MessageRequest messageRequest);
}
