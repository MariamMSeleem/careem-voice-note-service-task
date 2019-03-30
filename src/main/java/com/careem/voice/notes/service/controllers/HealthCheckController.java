package com.careem.voice.notes.service.controllers;


import com.careem.voice.notes.service.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
public class HealthCheckController {

    @GetMapping
    public ResponseEntity<ApiResponse<String>> healthCheck(){
        return new ResponseEntity(new ApiResponse(HttpStatus.OK, true,"Voice Note Service 1.0.", (Object)null), HttpStatus.OK);
    }
}
