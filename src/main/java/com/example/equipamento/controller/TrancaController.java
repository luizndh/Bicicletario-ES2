package com.example.equipamento.controller;

import com.example.equipamento.model.Tranca;
import com.example.equipamento.service.TrancaService;
import com.example.equipamento.util.Erro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tranca")
public class TrancaController {

    @Autowired
    private TrancaService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> getTrancaPorId(@PathVariable("id_tranca") int idTranca) {
        Tranca t = this.service.getTrancaPorId(idTranca);

        if(t != null) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(t);
        }
        return ResponseEntity.status(404).body(new Erro("404", "Nao foi possivel encontrar a tranca com id " + idTranca));
    }
}
