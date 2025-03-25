package pe.edu.vallegrande.SupplierNPH.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.SupplierNPH.model.Shed;

@Repository
public interface ShedRepository extends ReactiveCrudRepository<Shed, Long> {

}
