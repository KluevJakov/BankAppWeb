package com.kluevja.bankappweb.controllers;

import com.kluevja.bankappweb.models.*;
import com.kluevja.bankappweb.services.AccountService;
import com.kluevja.bankappweb.services.ClientService;
import com.kluevja.bankappweb.services.TransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class RestControllerExample {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransferService transferService;

    @GetMapping("/home")
    public List<Client> home() {
        log.info("home");
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
        log.info("Счета текущего клиента: " + accountService.getAccountList(currentClient.getId()));
        return accountService.getAccountList(currentClient.getId());
    }

    @GetMapping("/getCurrentUserTransactions")
    public List<Transaction> getCurrentUserTransactions(Authentication authentication) {
        log.info("Получить список переводов текущего пользователя");
        Client currentClient = clientService.getClientByEmail(authentication.getPrincipal().toString());
        return transferService.getCurrentUserTransactions(currentClient);
    }

    @PostMapping("/executeTransfer")
    public MessageDTO executeTransfer (Authentication authentication, @RequestBody TransferDTO transferDTO) {
        try {
            Client currentClient = clientService.getClientByEmail(authentication.getPrincipal().toString());
            transferService.execute(transferDTO, currentClient);
            return new MessageDTO("Успешно!", true);
        } catch (Exception e) {
            return new MessageDTO("Ошибка! " + e.getMessage(), false);
        }
    }

}
