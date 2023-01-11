package com.kluevja.bankappweb.controllers;

import com.kluevja.bankappweb.models.Client;
import com.kluevja.bankappweb.services.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/client")
@Slf4j
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute Client client, RedirectAttributes model) {
        log.info("Попытка создать клиента " + client);
        try {
            clientService.createClient(client);
            model.addFlashAttribute("msg", "Пользователь успешно создан");
        } catch (Exception e) {
            model.addFlashAttribute("msg", e.getMessage());
            log.warn("Неудачная попытка создать клиента : " + e.getMessage());
        }
        return new ModelAndView("redirect:/client");
    }

    @GetMapping("/get")
    public ModelAndView get(@RequestParam Long id, RedirectAttributes model) {
        try {
            Client client = clientService.getClient(id);
            model.addFlashAttribute("client", client);
        } catch (Exception e) {
            model.addFlashAttribute("msg", e.getMessage());
            log.warn("Неудачная попытка найти клиента : " + e.getMessage());
        }
        return new ModelAndView("redirect:/client");
    }

    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute Client client, RedirectAttributes model) {
        try {
            clientService.updateClient(client);
            model.addFlashAttribute("msg", "Пользователь успешно обновлен");
        } catch (Exception e) {
            model.addFlashAttribute("msg", "Не удалось найти пользователя");
            log.error("Ошибка при изменении клиента : " + e.getMessage());
        }
        return new ModelAndView("redirect:/client");
    }

    @PostMapping("/delete")
    public ModelAndView delete(@RequestParam Long id, RedirectAttributes model) {
        if (clientService.deleteClient(id)) {
            model.addFlashAttribute("msg", "Пользователь успешно удален");
        } else {
            model.addFlashAttribute("msg", "Не удалось удалить пользователя");
        }
        return new ModelAndView("redirect:/client");
    }
}
