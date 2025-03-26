package pe.edu.vallegrande.SupplierNPH.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import pe.edu.vallegrande.SupplierNPH.model.Supplier;
import pe.edu.vallegrande.SupplierNPH.service.SupplierService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SupplierRestTest {

    private WebTestClient webTestClient;

    @Mock
    private SupplierService supplierService;

    @InjectMocks
    private SupplierRest supplierRest;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(supplierRest).build();
    }

    @Test
    void testFindAll() {
        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("Empresa ABC");

        when(supplierService.findAll()).thenReturn(Flux.just(supplier));

        webTestClient.get()
                .uri("/NPH/suppliers")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Supplier.class)
                .hasSize(1);
    }

    @Test
    void testLogicalDelete() {
        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setStatus("I"); // Inactivo después del borrado lógico

        when(supplierService.logicalDelete(1L)).thenReturn(Mono.just(supplier));

        webTestClient.delete()
                .uri("/NPH/suppliers/1/logico")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Supplier.class)
                .value(s -> s.getStatus().equals("I"));
    }

    @Test
    void testRestore() {
        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setStatus("A"); // Activo después de la restauración

        when(supplierService.restore(1L)).thenReturn(Mono.just(supplier));

        webTestClient.put()
                .uri("/NPH/suppliers/1/restaurar")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Supplier.class)
                .value(s -> s.getStatus().equals("A"));
    }
}
