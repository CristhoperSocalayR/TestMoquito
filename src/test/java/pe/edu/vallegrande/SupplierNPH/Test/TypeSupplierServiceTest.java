package pe.edu.vallegrande.SupplierNPH.service;

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
import pe.edu.vallegrande.SupplierNPH.model.TypeSupplier;
import pe.edu.vallegrande.SupplierNPH.repository.TypeSupplierRepository;
import pe.edu.vallegrande.SupplierNPH.service.TypeSupplierService;

@ExtendWith(MockitoExtension.class)
class TypeSupplierServiceTest {

    @Mock
    private TypeSupplierRepository repository;

    @InjectMocks
    private TypeSupplierService service;

    private TypeSupplier typeSupplier;

    @BeforeEach
    void setUp() {
        typeSupplier = new TypeSupplier();
        typeSupplier.setId(1L);
        typeSupplier.setName("Proveedor A");
        typeSupplier.setAddress("Calle 123");
        typeSupplier.setUbigeoId(100L);
        typeSupplier.setStatus("A");
    }

    @Test
    void testGetAll() {
        when(repository.findAllByOrderByIdAsc()).thenReturn(Flux.just(typeSupplier));
        
        Flux<TypeSupplier> result = service.getAll();
        
        assertEquals(1, result.collectList().block().size());
        verify(repository, times(1)).findAllByOrderByIdAsc();
    }

    @Test
    void testGetById() {
        when(repository.findById(1L)).thenReturn(Mono.just(typeSupplier));
        
        Mono<TypeSupplier> result = service.getById(1L);
        TypeSupplier found = result.block();
        
        assertNotNull(found);
        assertEquals(1L, found.getId());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testSoftDelete() {
        when(repository.findById(1L)).thenReturn(Mono.just(typeSupplier));
        when(repository.save(any(TypeSupplier.class))).thenReturn(Mono.just(typeSupplier));
        
        Mono<TypeSupplier> result = service.softDelete(1L);
        TypeSupplier updated = result.block();
        
        assertNotNull(updated);
        assertEquals("I", updated.getStatus());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(typeSupplier);
    }

    @Test
    void testRestore() {
        typeSupplier.setStatus("I");
        when(repository.findById(1L)).thenReturn(Mono.just(typeSupplier));
        when(repository.save(any(TypeSupplier.class))).thenReturn(Mono.just(typeSupplier));
        
        Mono<TypeSupplier> result = service.restore(1L);
        TypeSupplier updated = result.block();
        
        assertNotNull(updated);
        assertEquals("A", updated.getStatus());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(typeSupplier);
    }
}
