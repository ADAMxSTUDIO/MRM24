package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Entity
@Table(name = "chambres")
@Data
@NoArgsConstructor
public class Chambre implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomNumber;
    private String typeChambre;
    private int isDisponible;

    public Chambre(String roomNumber, String typeChambre, int isDisponible) {
        this.roomNumber = roomNumber;
        this.typeChambre = typeChambre;
        this.isDisponible = isDisponible;
    }

}
