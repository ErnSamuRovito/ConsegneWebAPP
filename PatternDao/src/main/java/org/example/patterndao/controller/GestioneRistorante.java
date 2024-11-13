package org.example.patterndao.controller;

import org.example.patterndao.model.Ristorante;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GestioneRistorante {
    @GetMapping("/addRistorante")
    public String aggiungiRistorante(@RequestBody Ristorante ristorante) {
        System.out.println("ristorante: " + ristorante.getNome());
        return "OK";
    }
}
