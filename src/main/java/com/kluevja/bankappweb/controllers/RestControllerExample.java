package com.kluevja.bankappweb.controllers;

import com.kluevja.bankappweb.models.Client;
import com.kluevja.bankappweb.services.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class RestControllerExample {

    @Autowired
    private ClientService clientService;

    @GetMapping("/home")
    public List<Client> home() {
        return clientService.getAllClients();
    }

    @GetMapping("/getClient")
    public Client getClient(@RequestParam Long id) {
        log.info("Получил id = " + id);
        return clientService.getClient(id);
    }
}
