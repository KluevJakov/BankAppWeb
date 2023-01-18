package com.kluevja.bankappweb.services;

import com.kluevja.bankappweb.models.Account;
import com.kluevja.bankappweb.models.Client;
import com.kluevja.bankappweb.repositories.AccountRepository;
import com.kluevja.bankappweb.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;

    public boolean createAccount(Long clientId) {
        Account account = new Account();
        account.setBalance(0);

        Optional<Client> clientForEdit = clientRepository.findById(clientId); // проверяем на null
        if (clientForEdit.isPresent()) {
            account.setName("Счет " + clientForEdit.get().getName());
            accountRepository.save(account); //сохранит в БД счет с нулевым балансом
            clientForEdit.get().getAccounts().add(accountRepository.findTopByOrderByIdDesc());
            clientRepository.save(clientForEdit.get());
            return true;
        }

        return false;
    }

    public Optional<Account> getAccount(Long id) {
        return accountRepository.findById(id);
    }

    public List<Account> getAccountList(Long id) {
        Client client = clientRepository.findById(id).get();
        return client.getAccounts();
    }

    public boolean updateAccount(Account account) {
        /*
        Client clientForChange = clientRepository.findById(client.getId()).get();
        clientForChange.setName(client.getName());
        clientForChange.setSurname(client.getSurname());
        clientForChange.setPatronymic(client.getPatronymic());
        clientForChange.setPassword(client.getPassword());
        clientRepository.save(clientForChange);

         */
        return true;
    }

    public boolean deleteAccount(Long id) {
        accountRepository.delete(accountRepository.findById(id).get());
        return true;
    }
}
