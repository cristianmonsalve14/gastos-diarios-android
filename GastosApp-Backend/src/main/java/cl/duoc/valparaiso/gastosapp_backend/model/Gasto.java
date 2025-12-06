package cl.duoc.valparaiso.gastosapp_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gastos")
public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column
    private String fotoUrl;

    // ===== CONSTRUCTORES =====
    public Gasto() {
    }

    public Gasto(Double monto, String descripcion, String categoria, LocalDateTime fecha) {
        this.monto = monto;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.fecha = fecha;
    }

    // ===== GETTERS Y SETTERS =====
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    @Override
    public String toString() {
        return "Gasto{" +
                "id=" + id +
                ", monto=" + monto +
                ", descripcion='" + descripcion + '\'' +
                ", categoria='" + categoria + '\'' +
                ", fecha=" + fecha +
                ", fotoUrl='" + fotoUrl + '\'' +
                '}';
    }
}