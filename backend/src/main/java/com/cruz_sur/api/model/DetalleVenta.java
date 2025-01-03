package com.cruz_sur.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "detalle_venta")
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Time horaInicio;
    private Time horaFinal;
    private BigDecimal precio;
    private LocalDate fecha;
    private String usuarioCreacion;
    private LocalDateTime fechaCreacion;
    private String usuarioModificacion;
    private LocalDateTime fechaModificacion;

    @Column(name = "estado", nullable = false)
    private Character estado;

    @ManyToOne
    @JoinColumn(name = "venta_id", nullable = false)
    private Reserva venta;

    @ManyToOne
    @JoinColumn(name = "campo_id", nullable = false)
    private Campo campo;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;

}
