package br.andrevasques.spring_exercises.controller;

import br.andrevasques.spring_exercises.model.entitites.Client;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/client")
public class ClientController {

//    @GetMapping
//    public Client getClient() {
//        return new Client(19, "Andre", "123.456.789-00");
//    }

    @GetMapping
    public Client getClientById(@RequestParam(name = "id") int id) {
        return new Client(id, "Nicole", "123.456.789-00");
    }
}
