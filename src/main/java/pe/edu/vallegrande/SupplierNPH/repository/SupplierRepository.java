package pe.edu.vallegrande.SupplierNPH.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.SupplierNPH.model.Supplier;
import reactor.core.publisher.Flux;

@Repository
public interface SupplierRepository extends ReactiveCrudRepository<Supplier, Long> {
    Flux<Supplier> findAllByOrderByIdAsc();
}
