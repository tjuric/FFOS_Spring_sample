package com.infobip.sample.ffosspring_sample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // ready for use by Spring MVC to handle web requests
public class FfosSpringSampleController {

    @GetMapping("/")
    public String index() {
        return "Pozdrav FFOS-u iz Spring Boot aplikacije!";
    }

}
