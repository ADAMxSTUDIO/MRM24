package dao;

import model.Personnel;
import jakarta.persistence.*;

public class PersonnelDAO implements IClass<Personnel> {
    private EntityManager entityManager;

    public PersonnelDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mrm24jee");
        entityManager = emf.createEntityManager();
    }

    @Override
    public void add(Personnel personnel) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(personnel);
            entityManager.getTransaction().commit();
            System.out.println("Personnel added successfully");
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public Personnel get(Long id) {
        try {
            return entityManager.find(Personnel.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void modify(Personnel personnel, Long id) {
        entityManager.getTransaction().begin();
        try {
            Personnel existingPersonnel = entityManager.find(Personnel.class, id);
            if (existingPersonnel != null) {
                existingPersonnel.setFirstName(personnel.getFirstName());
                existingPersonnel.setLastName(personnel.getLastName());
                existingPersonnel.setFunctionTitle(personnel.getFunctionTitle());
                existingPersonnel.setSpeciality(personnel.getSpeciality());
                existingPersonnel.setPhoneNumber(personnel.getPhoneNumber());
                existingPersonnel.setEmail(personnel.getEmail());
                entityManager.merge(existingPersonnel);
                entityManager.getTransaction().commit();
                System.out.println("Personnel updated successfully");
            } else {
                System.out.println("Personnel not found for modification");
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
            Personnel personnel = entityManager.find(Personnel.class, id);
            if (personnel != null) {
                entityManager.remove(personnel);
                entityManager.getTransaction().commit();
                System.out.println("Personnel deleted successfully");
            } else {
                System.out.println("Personnel not found for deletion");
            }
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
}
