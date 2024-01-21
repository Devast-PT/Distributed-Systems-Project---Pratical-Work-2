package com.mariocosta.clients.localization;


import com.mariocosta.clients.localization.dto.LocalizationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@FeignClient(
        value = "localization",
        path = "api/v1/localization"
)
public interface LocalizationClient {


    @PostMapping("/register/")
    ResponseEntity<String> registerLocation(@RequestBody LocalizationDto localizationDto);

    @PostMapping("/bylocation/")
    Collection<LocalizationDto> byLocations(@RequestBody LocalizationDto localizationDto);

    @PostMapping("/byPreviousDate")
    Collection<LocalizationDto> byPreviousDate(@RequestBody LocalizationDto localizationDto);

    @PostMapping("/byNextDate")
    Collection<LocalizationDto> byNextDate(@RequestBody LocalizationDto localizationDto);
}
