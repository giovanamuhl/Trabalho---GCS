package com.example.ticket_hub.service;

import com.example.ticket_hub.enums.FormaPagamento;
import com.example.ticket_hub.model.Evento;
import com.example.ticket_hub.model.Pedido;
import com.example.ticket_hub.repository.EventoRepository;
import com.example.ticket_hub.repository.PedidoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private EventoRepository eventoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    private Evento evento;

    @BeforeEach
    void configurar() {
        evento = new Evento();
        evento.setId(1L);
        evento.setTitulo("Show de Teste");
        evento.setPreco(50.0);
        evento.setIngressosDisponiveis(10);

        when(eventoRepository.findById(1L))
                .thenReturn(Optional.of(evento));
    }
    @Test
    void deveCriarPedidoComCompraValida() {

        Pedido pedido = pedidoService.criar(
                1L,
                "Maria",
                "maria@email.com",
                2,
                FormaPagamento.PIX
        );

        assertNotNull(pedido);
        assertEquals("Maria", pedido.getNomeComprador());
        assertEquals(2, pedido.getQuantidade());

        assertEquals(100.0, pedido.getValorTotal(), 0.001);
        assertEquals(8, evento.getIngressosDisponiveis());
        verify(eventoRepository).save(evento);

        verify(pedidoRepository).save(pedido);
    }
}