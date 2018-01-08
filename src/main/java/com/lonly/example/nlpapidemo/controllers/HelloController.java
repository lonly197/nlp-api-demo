package com.lonly.example.nlpapidemo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping
public class HelloController {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String jiebaSegWord() {
        return "Hello, world!";
    }

    @ExceptionHandler
    void handleIllegaArgumentException(IllegalAccessException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}
