package pe.edu.vallegrande.SupplierNPH.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.edu.vallegrande.SupplierNPH.model.Product;
import pe.edu.vallegrande.SupplierNPH.repository.ProductRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        Product product1 = new Product();
        Product product2 = new Product();
        when(productRepository.findAll()).thenReturn(Flux.just(product1, product2));

        StepVerifier.create(productService.getAllProducts())
                .expectNext(product1, product2)
                .verifyComplete();
    }

    @Test
    void testDeleteProduct() {
        Long productId = 1L;
        when(productRepository.deleteById(productId)).thenReturn(Mono.empty());

        StepVerifier.create(productService.deleteProduct(productId))
                .verifyComplete();
    }

    @Test
    void testSoftDeleteProduct() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setStatus("A");

        when(productRepository.findById(productId)).thenReturn(Mono.just(product));
        when(productRepository.save(any(Product.class))).thenReturn(Mono.just(product));

        StepVerifier.create(productService.softDeleteProduct(productId))
                .expectNextMatches(p -> "I".equals(p.getStatus()))
                .verifyComplete();
    }

    @Test
    void testRestoreProduct() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setStatus("I");

        when(productRepository.findByIdAndStatus(productId, "I")).thenReturn(Mono.just(product));
        when(productRepository.save(any(Product.class))).thenReturn(Mono.just(product));

        StepVerifier.create(productService.restoreProduct(productId))
                .expectNextMatches(p -> "A".equals(p.getStatus()))
                .verifyComplete();
    }
}
