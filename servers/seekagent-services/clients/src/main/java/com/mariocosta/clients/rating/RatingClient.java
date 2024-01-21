package com.mariocosta.clients.rating;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "rating",
        path = "api/v1/rating-service")
public interface RatingClient {

}