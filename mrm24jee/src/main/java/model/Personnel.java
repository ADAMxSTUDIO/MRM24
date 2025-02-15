package model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "personnels")
@Data
@NoArgsConstructor
public class Personnel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String jobTitle; // Changed from functionTitle to jobTitle
    private String speciality;
    private String phoneNumber;
    private String email;

    // Custom constructor to initialize Personnel
    public Personnel(String firstName, String lastName, String jobTitle, String speciality, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle; // Changed from functionTitle to jobTitle
        this.speciality = speciality;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
