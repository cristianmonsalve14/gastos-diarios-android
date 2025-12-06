package cl.duoc.valparaiso.gastosapp_backend.service;

import cl.duoc.valparaiso.gastosapp_backend.model.Gasto;
import cl.duoc.valparaiso.gastosapp_backend.repository.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    // ===== GET ALL =====
    public List<Gasto> obtenerTodosLosGastos() {
        return gastoRepository.findAll();
    }

    // ===== GET BY ID =====
    public Optional<Gasto> obtenerGastoPorId(Long id) {
        return gastoRepository.findById(id);
    }

    // ===== CREATE =====
    public Gasto crearGasto(Gasto gasto) {
        if (gasto.getMonto() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a 0");
        }
        if (gasto.getDescripcion() == null || gasto.getDescripcion().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
        if (gasto.getCategoria() == null || gasto.getCategoria().isEmpty()) {
            throw new IllegalArgumentException("La categoría no puede estar vacía");
        }
        return gastoRepository.save(gasto);
    }

    // ===== UPDATE =====
    public Optional<Gasto> actualizarGasto(Long id, Gasto gastoActualizado) {
        return gastoRepository.findById(id).map(gasto -> {
            if (gastoActualizado.getMonto() != null) {
                gasto.setMonto(gastoActualizado.getMonto());
            }
            if (gastoActualizado.getDescripcion() != null) {
                gasto.setDescripcion(gastoActualizado.getDescripcion());
            }
            if (gastoActualizado.getCategoria() != null) {
                gasto.setCategoria(gastoActualizado.getCategoria());
            }
            if (gastoActualizado.getFecha() != null) {
                gasto.setFecha(gastoActualizado.getFecha());
            }
            if (gastoActualizado.getFotoUrl() != null) {
                gasto.setFotoUrl(gastoActualizado.getFotoUrl());
            }
            return gastoRepository.save(gasto);
        });
    }

    // ===== DELETE =====
    public boolean eliminarGasto(Long id) {
        if (gastoRepository.existsById(id)) {
            gastoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ===== COUNT =====
    public long contarGastos() {
        return gastoRepository.count();
    }
}