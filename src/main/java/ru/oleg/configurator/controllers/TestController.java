package ru.oleg.configurator.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
@AllArgsConstructor
public class TestController {

    @GetMapping
    public String getHelloWorld() {
        return "Hello World";
    }
}
