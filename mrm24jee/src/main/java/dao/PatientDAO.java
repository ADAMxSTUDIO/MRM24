package dao;

import model.Patient;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.List;

public class PatientDAO implements IClass<Patient> {
    private EntityManager entityManager;

    public PatientDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mrm24jee");
        entityManager = emf.createEntityManager();
    }

    @Override
    public void add(Patient patient) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(patient);  // Corrected 'c' to 'patient'
            entityManager.getTransaction().commit();
            System.out.println("Patient added successfully");
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public Patient get(Long id) {
        try {
            return entityManager.find(Patient.class, id);  // Fetch patient by ID
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;  // Return null if not found
    }

    @Override
    public List<Patient> getAll() {
        try {
            return entityManager.createQuery("SELECT p FROM Patient p", Patient.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); // Return an empty list instead of null for better handling.
        }
    }

    @Override
    public void modify(Patient patient, Long id) {
        entityManager.getTransaction().begin();
        try {
            Patient existingPatient = entityManager.find(Patient.class, id);
            if (existingPatient != null) {
                existingPatient.setFirstName(patient.getFirstName());
                existingPatient.setLastName(patient.getLastName());
                existingPatient.setDateOfBirth(patient.getDateOfBirth());
                existingPatient.setAddress(patient.getAddress());
                existingPatient.setPhoneNumber(patient.getPhoneNumber());
                existingPatient.setMedicalHistory(patient.getMedicalHistory());
                entityManager.merge(existingPatient);  // Merge updated patient
                entityManager.getTransaction().commit();
                System.out.println("Patient updated successfully");
            } else {
                System.out.println("Patient not found for modification");
            }
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public void delete(Long id) {
        entityManager.getTransaction().begin();
        try {
            Patient patient = entityManager.find(Patient.class, id);
            if (patient != null) {
                entityManager.remove(patient);  // Remove patient from DB
                entityManager.getTransaction().commit();
                System.out.println("Patient deleted successfully");
            } else {
                System.out.println("Patient not found for deletion");
            }
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
}
