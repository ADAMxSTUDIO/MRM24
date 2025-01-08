package model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.io.Serializable;

@Entity
@Table(name = "rendezvous")
@Data
@NoArgsConstructor
public class RendezVous implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "personnel_id", referencedColumnName = "id")
    private Personnel personnel;

    private LocalDateTime dateTime;

    private String reason;

    public RendezVous(Patient patient, Personnel personnel, LocalDateTime dateTime, String reason) {
        this.patient = patient;
        this.personnel = personnel;
        this.dateTime = dateTime;
        this.reason = reason;
    }
}
