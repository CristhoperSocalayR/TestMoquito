package pe.edu.vallegrande.SupplierNPH.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.SupplierNPH.model.Product;
import pe.edu.vallegrande.SupplierNPH.service.ProductService;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/NPH/products")
public class ProductRest {

    @Autowired
    private ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping
    public Flux<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @PutMapping("/logic/{id}")
    public Mono<Product> softDeleteProduct(@PathVariable Long id) {
        return productService.softDeleteProduct(id);
    }

    @PutMapping("/restore/{id}")
    public Mono<Product> restoreProduct(@PathVariable Long id) {
        return productService.restoreProduct(id);
    }

    @PutMapping("/{id}")
    public Mono<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return productService.updateProduct(id, productDetails);
    }
}
