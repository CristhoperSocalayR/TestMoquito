package pe.edu.vallegrande.SupplierNPH.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.SupplierNPH.model.MovementKardex;
import reactor.core.publisher.Flux;

@Repository
public interface MovementKardexRepository extends ReactiveCrudRepository<MovementKardex, Long> {
     Flux<MovementKardex> findAllByOrderByKardexIdAsc();
}
