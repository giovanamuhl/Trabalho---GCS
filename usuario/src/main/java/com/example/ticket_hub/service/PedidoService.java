package com.example.ticket_hub.service;

import com.example.ticket_hub.enums.FormaPagamento;
import com.example.ticket_hub.model.Evento;
import com.example.ticket_hub.model.Pedido;
import com.example.ticket_hub.model.Usuario;
import com.example.ticket_hub.repository.EventoRepository;
import com.example.ticket_hub.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    public Pedido criar(Usuario usuario, Long eventoId, String nomeComprador, String emailComprador,
                        int quantidade, FormaPagamento formaPagamento) {

        Evento evento = eventoRepository.findById(eventoId).orElse(null);
        if (evento == null) {
            throw new RuntimeException("Evento não encontrado");
        }

        if (evento.getData().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Este evento já aconteceu e não está mais à venda.");
        }
        if (quantidade < 1) {
    throw new RuntimeException("A quantidade deve ser no mínimo 1.");
}

        if (evento.getIngressosDisponiveis() < quantidade) {
            throw new RuntimeException("Ingressos insuficientes. Disponíveis: " + evento.getIngressosDisponiveis());
        }

        Pedido pedido = new Pedido();
        pedido.setEvento(evento);
        pedido.setUsuario(usuario);
        pedido.setNomeComprador(nomeComprador);
        pedido.setEmailComprador(emailComprador);
        pedido.setQuantidade(quantidade);
        pedido.setFormaPagamento(formaPagamento);
        pedido.setValorTotal(evento.getPreco() * quantidade);
        pedido.setDataCriacao(LocalDateTime.now());
        pedidoRepository.save(pedido);

        evento.setIngressosDisponiveis(evento.getIngressosDisponiveis() - quantidade);
        eventoRepository.save(evento);

        return pedido;
    }
}
