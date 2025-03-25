package pe.edu.vallegrande.SupplierNPH.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import pe.edu.vallegrande.SupplierNPH.model.Ubigeo;
import pe.edu.vallegrande.SupplierNPH.service.UbigeoService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UbigeoRestTest {

    private WebTestClient webTestClient;

    @Mock
    private UbigeoService ubigeoService;

    @InjectMocks
    private UbigeoRest ubigeoRest;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(ubigeoRest).build();
    }

    @Test
    void testFindAll() {
        Ubigeo ubigeo = new Ubigeo();
        ubigeo.setId(1L);
        ubigeo.setCountry("Peru");

        when(ubigeoService.listarTodos()).thenReturn(Flux.just(ubigeo));

        webTestClient.get()
                .uri("/NPH/ubigeo")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Ubigeo.class)
                .hasSize(1);
    }

    @Test
    void testLogicalDelete() {
        Ubigeo ubigeo = new Ubigeo();
        ubigeo.setId(1L);
        ubigeo.setStatus("I");

        when(ubigeoService.eliminarLogico(1L)).thenReturn(Mono.just(ubigeo));

        webTestClient.delete()
                .uri("/NPH/ubigeo/1/logico")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Ubigeo.class)
                .value(u -> u.getStatus().equals("I"));
    }

    @Test
    void testRestore() {
        Ubigeo ubigeo = new Ubigeo();
        ubigeo.setId(1L);
        ubigeo.setStatus("A");

        when(ubigeoService.restaurar(1L)).thenReturn(Mono.just(ubigeo));

        webTestClient.put()
                .uri("/NPH/ubigeo/1/restaurar")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Ubigeo.class)
                .value(u -> u.getStatus().equals("A"));
    }
}
