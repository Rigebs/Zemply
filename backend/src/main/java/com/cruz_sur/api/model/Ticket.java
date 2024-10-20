package com.cruz_sur.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serie;

    private String numero;

    private String usuarioCreacion;

    @Column(name = "fecha_creacion", columnDefinition = "DATETIME DEFAULT GETDATE()")
    private LocalDateTime fechaCreacion;

    private String usuarioModificacion;

    private LocalDateTime fechaModificacion;

    @Column(name = "estado", nullable = false)
    private Character estado;

    @ManyToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;
}
