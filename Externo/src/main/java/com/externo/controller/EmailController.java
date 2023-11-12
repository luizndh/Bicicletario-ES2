package com.externo.controller;

import com.externo.DTO.EmailDTO;
import com.externo.model.Email;
import com.externo.servico.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enviarEmail")
@Api(value="API para envio de email")
public class EmailController {

    @Autowired
    private EmailService service;

    @PostMapping("/")
    @ApiOperation(value="Envia um email")
    public ResponseEntity<Boolean> enviarEmail(@RequestBody EmailDTO dadosEmail) {
        return ResponseEntity.ok().body(this.service.enviarEmail(dadosEmail));
    }
}
