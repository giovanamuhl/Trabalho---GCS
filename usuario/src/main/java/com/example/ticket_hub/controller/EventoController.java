package com.example.ticket_hub.controller;

import com.example.ticket_hub.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("eventos", eventoRepository.findByDataGreaterThanEqual(LocalDateTime.now()));
        return "index";
    }

    @GetMapping("/buscar")
    public String buscar(@RequestParam String termo, Model model) {
        String termoBusca = "%" + termo + "%";
        model.addAttribute("eventos", eventoRepository.buscar(termoBusca, LocalDateTime.now()));
        model.addAttribute("termo", termo);
        return "index";
    }
}
