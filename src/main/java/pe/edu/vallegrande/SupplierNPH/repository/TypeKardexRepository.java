package pe.edu.vallegrande.SupplierNPH.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.SupplierNPH.model.TypeKardex;
import reactor.core.publisher.Flux;

public interface TypeKardexRepository extends ReactiveCrudRepository<TypeKardex, Long> {

    Flux<TypeKardex> findByStatus(String status);
    Flux<TypeKardex> findAllByOrderByIdAsc();
}
