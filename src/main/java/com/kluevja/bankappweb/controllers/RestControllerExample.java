package com.kluevja.bankappweb.controllers;

import com.kluevja.bankappweb.models.Account;
import com.kluevja.bankappweb.models.Client;
import com.kluevja.bankappweb.services.AccountService;
import com.kluevja.bankappweb.services.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class RestControllerExample {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/home")
    public List<Client> home() {
        return clientService.getAllClients();
    }

    @GetMapping("/getClient")
    public Client getClient(@RequestParam Long id) {
        log.info("Получил id = " + id);
        return clientService.getClient(id);
    }

    @GetMapping("/getCurrentUser")
    public Client getCurrentUser(Authentication authentication) {
        log.info("Получить текущего пользователя");
        return clientService.getClientByEmail(authentication.getPrincipal().toString());
    }

    @GetMapping("/getCurrentUserAccounts")
    public List<Account> getCurrentUserAccounts(Authentication authentication) {
        log.info("Получить список счетов текущего пользователя");
        Client currentClient = clientService.getClientByEmail(authentication.getPrincipal().toString());
        return accountService.getAccountList(currentClient.getId());
    }

    @PostMapping("/executeTransfer")
    public void executeTransfer () { //возвращать bool
        return;
    }

}
