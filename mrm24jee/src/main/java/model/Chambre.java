package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Entity
@Table(name = "chambres")
@Data
@NoArgsConstructor
public class Chambre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomNumber;

    private String roomType;

    private boolean disponible;

    public Chambre(String roomNumber, String roomType, boolean disponible) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.disponible = disponible;
    }
}