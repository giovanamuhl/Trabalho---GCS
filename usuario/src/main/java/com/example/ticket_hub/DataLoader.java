// DataLoader é uma classe que implementa CommandLineRunner, o que significa que ela será executada automaticamente quando a aplicação for iniciada. Ela é responsável por carregar dados iniciais no banco de dados, como eventos e um usuário admin, para facilitar os testes e o desenvolvimento da aplicação.

package com.example.ticket_hub;

import com.example.ticket_hub.model.Evento;
import com.example.ticket_hub.model.Usuario;
import com.example.ticket_hub.repository.EventoRepository;
import com.example.ticket_hub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) {

        Usuario admin = new Usuario();
        admin.setUsuario("admin");
        admin.setSenha("1234");
        usuarioRepository.save(admin);

        Usuario cliente = new Usuario();
        cliente.setUsuario("cliente");
        cliente.setSenha("1234");
        usuarioRepository.save(cliente);

        eventoRepository.save(criarEvento(
                "Festival de Música Eletrônica",
                "Uma noite incrível com os melhores DJs do Brasil.",
                LocalDateTime.of(2026, 8, 15, 22, 0),
                "Shopping Bosque dos Ipês",
                180.0,
                200,
                "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?w=800"
        ));

        eventoRepository.save(criarEvento(
                "Show da Banda X",
                "O maior show de rock do ano, não perca!",
                LocalDateTime.of(2026, 9, 20, 21, 0),
                "Arena MS",
                220.0,
                500,
                "https://images.unsplash.com/photo-1501386761578-eac5c94b800a?w=800"
        ));

        eventoRepository.save(criarEvento(
                "Exposição de Arte Moderna",
                "Uma experiência única com obras de artistas contemporâneos.",
                LocalDateTime.of(2026, 10, 5, 10, 0),
                "Museu de Arte Contemporânea",
                60.0,
                150,
                "https://images.unsplash.com/photo-1460661419201-fd4cecdf8a8b?w=800"
        ));

        eventoRepository.save(criarEvento(
                "Festival de Jazz e Blues",
                "Duas noites de muita música ao vivo com grandes nomes do jazz.",
                LocalDateTime.of(2026, 10, 22, 20, 0),
                "Clube do Jazz",
                120.0,
                300,
                "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?w=800"
        ));

        eventoRepository.save(criarEvento(
                "Concerto de Música Clássica",
                "Uma noite inesquecível com a Orquestra Filarmônica.",
                LocalDateTime.of(2026, 11, 5, 19, 30),
                "Teatro Municipal",
                90.0,
                400,
                "https://images.unsplash.com/photo-1504805572947-34fad45aed93?w=800"
        ));

        eventoRepository.save(criarEvento(
                "Feira de Gastronomia Internacional",
                "Sabores do mundo inteiro em um só lugar.",
                LocalDateTime.of(2026, 11, 12, 11, 0),
                "Centro de Convenções",
                40.0,
                1000,
                "https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=800"
        ));
    }

    private Evento criarEvento(String titulo, String descricao, LocalDateTime data,
                                String local, double preco, int capacidade, String imagem) {
        Evento e = new Evento();
        e.setTitulo(titulo);
        e.setDescricao(descricao);
        e.setData(data);
        e.setLocal(local);
        e.setPreco(preco);
        e.setCapacidade(capacidade);
        e.setIngressosDisponiveis(capacidade);
        e.setImagem(imagem);
        return e;
    }
}
