package com.example.ticket_hub.controller;

import com.example.ticket_hub.model.Usuario;
import com.example.ticket_hub.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String usuario,
                        @RequestParam String senha,
                        HttpSession session,
                        HttpServletRequest request,
                        Model model) {

        Usuario encontrado = usuarioRepository.findByUsuario(usuario).orElse(null);
    
        if (encontrado != null && encontrado.getSenha().equals(senha)) {
            request.changeSessionId();
            session.setAttribute("usuarioLogado", encontrado);
            if (encontrado.getUsuario().equals("admin")) {
                return "redirect:/admin/eventos";
            }
            return "redirect:/";
        }

        model.addAttribute("erro", "Usuário ou senha inválidos.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
