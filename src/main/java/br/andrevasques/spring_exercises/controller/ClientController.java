package br.andrevasques.spring_exercises.controller;

import br.andrevasques.spring_exercises.model.entitites.Client;
import br.andrevasques.spring_exercises.model.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/client")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

//    @GetMapping
//    public Client getClientById(@RequestParam(name = "id") int id) {
//        return new Client(id, "Nicole", "123.456.789-00");
//    }
}
