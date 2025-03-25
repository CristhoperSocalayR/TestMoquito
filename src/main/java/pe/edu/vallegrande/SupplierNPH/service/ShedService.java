package pe.edu.vallegrande.SupplierNPH.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import pe.edu.vallegrande.SupplierNPH.model.Shed;
import pe.edu.vallegrande.SupplierNPH.repository.ShedRepository;

@Service
public class ShedService {

    @Autowired
    private ShedRepository shedRepository;

    public Mono<Shed> createShed(Shed shed) {
        return shedRepository.save(shed);
    }

    public Mono<Shed> updateShed(Long id, Shed shed) {
        return shedRepository.findById(id)
                .flatMap(existingShed -> {
                    existingShed.setName(shed.getName());
                    existingShed.setLocation(shed.getLocation());
                    existingShed.setCapacity(shed.getCapacity());
                    existingShed.setChickenType(shed.getChickenType());
                    existingShed.setInspectionDate(shed.getInspectionDate());
                    existingShed.setNote(shed.getNote());
                    return shedRepository.save(existingShed);
                });
    }

    public Flux<Shed> getAllSheds() {
        return shedRepository.findAll();
    }

    public Mono<Shed> deleteShed(Long id) {
        return shedRepository.findById(id)
                .flatMap(existingShed -> {
                    existingShed.setStatus("I");  // Marcar como "Eliminado" lógicamente
                    return shedRepository.save(existingShed);
                });
    }

    public Mono<Shed> restoreShed(Long id) {
        return shedRepository.findById(id)
                .flatMap(existingShed -> {
                    existingShed.setStatus("A");  // Restaurar el Shed
                    return shedRepository.save(existingShed);
                });
    }

    public Mono<Void> permanentDeleteShed(Long id) {
        return shedRepository.deleteById(id);
    }
}
