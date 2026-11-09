package com.example.ticket_hub.controller;

import com.example.ticket_hub.enums.FormaPagamento;
import com.example.ticket_hub.model.Pedido;
import com.example.ticket_hub.model.Usuario;
import com.example.ticket_hub.repository.EventoRepository;
import com.example.ticket_hub.repository.PedidoRepository;
import com.example.ticket_hub.service.PedidoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    private Usuario usuarioLogado(HttpSession session) {
        return (Usuario) session.getAttribute("usuarioLogado");
    }

    @GetMapping("/pagamento/{eventoId}")
    public String pagamento(@PathVariable Long eventoId, Model model) {
        model.addAttribute("evento", eventoRepository.findById(eventoId).orElseThrow());
        return "pagamento";
    }

    @PostMapping("/pedidos")
    public String criar(@RequestParam Long eventoId,
                        @RequestParam String nomeComprador,
                        @RequestParam String emailComprador,
                        @RequestParam int quantidade,
                        @RequestParam FormaPagamento formaPagamento,
                        HttpSession session,
                        Model model) {
        try {
            Pedido pedido = pedidoService.criar(usuarioLogado(session), eventoId, nomeComprador, emailComprador, quantidade, formaPagamento);
            return "redirect:/confirmacao/" + pedido.getId();
        } catch (RuntimeException e) {
            model.addAttribute("evento", eventoRepository.findById(eventoId).orElseThrow());
            model.addAttribute("erro", e.getMessage());
            return "pagamento";
        }
    }

    @GetMapping("/confirmacao/{pedidoId}")
    public String confirmacao(@PathVariable Long pedidoId, HttpSession session, Model model) {
        Usuario usuario = usuarioLogado(session);
        Pedido pedido = pedidoRepository.findById(pedidoId).orElse(null);
        if (pedido == null || !pedido.getUsuario().getId().equals(usuario.getId())) {
            return "redirect:/meus-ingressos";
        }
        model.addAttribute("pedido", pedido);
        return "confirmacao";
    }

    @GetMapping("/meus-ingressos")
    public String meusIngressos(HttpSession session, Model model) {
        model.addAttribute("pedidos", pedidoRepository.findByUsuarioId(usuarioLogado(session).getId()));
        return "meus-ingressos";
    }
}
