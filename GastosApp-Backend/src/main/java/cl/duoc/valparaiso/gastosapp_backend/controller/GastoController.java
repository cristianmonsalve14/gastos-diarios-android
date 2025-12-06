package cl.duoc.valparaiso.gastosapp_backend.controller;

import cl.duoc.valparaiso.gastosapp_backend.model.Gasto;
import cl.duoc.valparaiso.gastosapp_backend.service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gastos")
@CrossOrigin(origins = "*")
public class GastoController {

    @Autowired
    private GastoService gastoService;

    // ===== GET ALL - Obtener todos los gastos =====
    @GetMapping
    public ResponseEntity<List<Gasto>> obtenerTodosLosGastos() {
        List<Gasto> gastos = gastoService.obtenerTodosLosGastos();
        return ResponseEntity.ok(gastos);
    }

    // ===== GET BY ID - Obtener un gasto por ID =====
    @GetMapping("/{id}")
    public ResponseEntity<Gasto> obtenerGastoPorId(@PathVariable Long id) {
        Optional<Gasto> gasto = gastoService.obtenerGastoPorId(id);
        if (gasto.isPresent()) {
            return ResponseEntity.ok(gasto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ===== POST - Crear un nuevo gasto =====
    @PostMapping
    public ResponseEntity<Gasto> crearGasto(@RequestBody Gasto gasto) {
        try {
            Gasto gastoCreado = gastoService.crearGasto(gasto);
            return ResponseEntity.status(HttpStatus.CREATED).body(gastoCreado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ===== PUT - Actualizar un gasto =====
    @PutMapping("/{id}")
    public ResponseEntity<Gasto> actualizarGasto(
            @PathVariable Long id,
            @RequestBody Gasto gasto) {
        Optional<Gasto> gastoActualizado = gastoService.actualizarGasto(id, gasto);
        if (gastoActualizado.isPresent()) {
            return ResponseEntity.ok(gastoActualizado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ===== DELETE - Eliminar un gasto =====
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGasto(@PathVariable Long id) {
        boolean eliminado = gastoService.eliminarGasto(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ===== GET COUNT - Contar gastos =====
    @GetMapping("/count")
    public ResponseEntity<Long> contarGastos() {
        long count = gastoService.contarGastos();
        return ResponseEntity.ok(count);
    }
}