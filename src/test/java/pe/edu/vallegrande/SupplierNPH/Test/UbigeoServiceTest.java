import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import pe.edu.vallegrande.SupplierNPH.model.Ubigeo;
import pe.edu.vallegrande.SupplierNPH.repository.UbigeoRepository;
import pe.edu.vallegrande.SupplierNPH.service.UbigeoService;

@ExtendWith(MockitoExtension.class)
class UbigeoServiceTest {

    @Mock
    private UbigeoRepository ubigeoRepository;

    @InjectMocks
    private UbigeoService ubigeoService;

    private Ubigeo ubigeo;

    @BeforeEach
    void setUp() {
        ubigeo = new Ubigeo();
        ubigeo.setId(1L);
        ubigeo.setCountry("Peru");
        ubigeo.setDepartment("Lima");
        ubigeo.setProvince("Lima");
        ubigeo.setDistrict("Miraflores");
        ubigeo.setStatus("A");
    }

    @Test
    void testListarTodos() {
        when(ubigeoRepository.findAllByOrderByIdAsc()).thenReturn(Flux.just(ubigeo));

        Flux<Ubigeo> result = ubigeoService.listarTodos();

        assertEquals(1, result.collectList().block().size());
        verify(ubigeoRepository, times(1)).findAllByOrderByIdAsc();
    }

    @Test
    void testEliminarLogico() {
        when(ubigeoRepository.findById(1L)).thenReturn(Mono.just(ubigeo));
        when(ubigeoRepository.save(any(Ubigeo.class))).thenReturn(Mono.just(ubigeo));

        Mono<Ubigeo> result = ubigeoService.eliminarLogico(1L);
        Ubigeo updatedUbigeo = result.block();

        assertNotNull(updatedUbigeo);
        assertEquals("I", updatedUbigeo.getStatus());
        verify(ubigeoRepository, times(1)).findById(1L);
        verify(ubigeoRepository, times(1)).save(ubigeo);
    }

    @Test
    void testRestaurar() {
        ubigeo.setStatus("I");
        when(ubigeoRepository.findById(1L)).thenReturn(Mono.just(ubigeo));
        when(ubigeoRepository.save(any(Ubigeo.class))).thenReturn(Mono.just(ubigeo));

        Mono<Ubigeo> result = ubigeoService.restaurar(1L);
        Ubigeo updatedUbigeo = result.block();

        assertNotNull(updatedUbigeo);
        assertEquals("A", updatedUbigeo.getStatus());
        verify(ubigeoRepository, times(1)).findById(1L);
        verify(ubigeoRepository, times(1)).save(ubigeo);
    }
}
