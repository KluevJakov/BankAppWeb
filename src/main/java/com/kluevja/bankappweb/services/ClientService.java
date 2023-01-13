package com.kluevja.bankappweb.services;

import com.kluevja.bankappweb.models.Client;
import com.kluevja.bankappweb.models.Role;
import com.kluevja.bankappweb.repositories.ClientRepository;
import com.kluevja.bankappweb.repositories.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createClient(Client client) {
        if (StringUtils.isEmptyOrWhitespace(client.getEmail())) {
            throw new IllegalArgumentException("Email не должен быть пустым");
        }
        if (StringUtils.isEmptyOrWhitespace(client.getName())) {
            throw new IllegalArgumentException("Поле имени не должен быть пустым");
        }
        if (StringUtils.isEmptyOrWhitespace(client.getSurname())) {
            throw new IllegalArgumentException("Поле фамилии не должен быть пустым");
        }
        if (StringUtils.isEmptyOrWhitespace(client.getPatronymic())) {
            throw new IllegalArgumentException("Поле отчества не должен быть пустым");
        }
        if (StringUtils.isEmptyOrWhitespace(client.getPassword())) {
            throw new IllegalArgumentException("Поле пароля не должен быть пустым");
        }

        Role roleForNewClient = roleRepository.findBySystemName("USER");
        client.setRole(roleForNewClient);
        client.setAccounts(new ArrayList<>());
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setActive(true);
        clientRepository.save(client);
    }

    public Client getClient(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            client.get().setPassword("");
            return client.get();
        } else {
            throw new IllegalArgumentException("Клиент с id = " + id + " не найдет в БД");
        }
    }

    public void updateClient(Client client) throws NoSuchElementException {
        if (client.getId() == null) {
            throw new IllegalArgumentException("Id не может быть пусты");
        }

        Client clientForChange = clientRepository.findById(client.getId()).get();

        if (!StringUtils.isEmptyOrWhitespace(client.getName())) {
            clientForChange.setName(client.getName());
        }

        if (!StringUtils.isEmptyOrWhitespace(client.getSurname())) {
            clientForChange.setSurname(client.getSurname());
        }

        if (!StringUtils.isEmptyOrWhitespace(client.getPatronymic())) {
            clientForChange.setPatronymic(client.getPatronymic());
        }

        clientRepository.save(clientForChange);
    }

    public boolean deleteClient(Long id) {
        clientRepository.delete(clientRepository.findById(id).get());
        return true;
    }

    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public List<Client> getAllClients () {
        return clientRepository.findAll();
    }
}
