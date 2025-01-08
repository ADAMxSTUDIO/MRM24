package model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.io.Serializable;

@Entity
@Table(name = "patients")
@Data  // Lombok annotation that includes Getter, Setter, ToString, EqualsAndHashCode, and RequiredArgsConstructor
@NoArgsConstructor  // No-arg constructor is included automatically by Lombok's @Data, but it's explicitly stated here

public class Patient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
    private String phoneNumber;
    private String medicalHistory;

    // This constructor will be used for creating a Patient with specific details
    public Patient(String firstName, String lastName, LocalDate dateOfBirth, String address, String phoneNumber, String medicalHistory) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.medicalHistory = medicalHistory;
    }
}
