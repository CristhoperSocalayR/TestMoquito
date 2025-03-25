package pe.edu.vallegrande.SupplierNPH.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.SupplierNPH.model.Ubigeo;
import reactor.core.publisher.Flux;

public interface UbigeoRepository extends ReactiveCrudRepository<Ubigeo, Long> {
    Flux<Ubigeo> findAllByOrderByIdAsc();
}
