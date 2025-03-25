package pe.edu.vallegrande.SupplierNPH.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.SupplierNPH.model.MovementKardex;
import pe.edu.vallegrande.SupplierNPH.service.MovementKardexService;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/NPH/movement-kardex")
public class MovementKardexRest {

    private final MovementKardexService movementKardexService;

    @Autowired
    public MovementKardexRest(MovementKardexService movementKardexService) {
        this.movementKardexService = movementKardexService;
    }

    // Listar todos los movimientos de Kardex
    @GetMapping
    public Flux<MovementKardex> listarTodos() {
        return movementKardexService.listarTodos();
    }

    // Crear un nuevo movimiento de Kardex
    @PostMapping
    public Mono<ResponseEntity<MovementKardex>> crear(@RequestBody MovementKardex movementKardex) {
        return movementKardexService.crear(movementKardex)
                .map(savedMovement -> ResponseEntity.status(HttpStatus.CREATED).body(savedMovement))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    // Editar un movimiento de Kardex existente
    @PutMapping("/{kardexId}")
    public Mono<ResponseEntity<MovementKardex>> editar(@PathVariable Long kardexId, @RequestBody MovementKardex movementKardex) {
        return movementKardexService.editar(kardexId, movementKardex)
                .map(updatedMovement -> ResponseEntity.status(HttpStatus.OK).body(updatedMovement))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Eliminar físicamente un movimiento de Kardex
    @DeleteMapping("/{kardexId}")
    public Mono<ResponseEntity<Object>> eliminar(@PathVariable Long kardexId) {
        return movementKardexService.eliminar(kardexId)
                .then(Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).build()))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}