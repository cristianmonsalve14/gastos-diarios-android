package cl.duoc.valparaiso.gastosapp_backend.repository;

import cl.duoc.valparaiso.gastosapp_backend.model.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {
    // JpaRepository proporciona automáticamente:
    // - findAll()
    // - findById(Long id)
    // - save(Gasto gasto)
    // - delete(Gasto gasto)
    // - deleteById(Long id)
    // - count()
    // - etc.

    // Puedes agregar métodos personalizados aquí si los necesitas
}