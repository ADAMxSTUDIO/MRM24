package dao;

import model.RendezVous;
import jakarta.persistence.*;

public class RendezVousDAO implements IClass<RendezVous> {
    private EntityManager entityManager;

    public RendezVousDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mrm24jee");
        entityManager = emf.createEntityManager();
    }

    @Override
    public void add(RendezVous rendezVous) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(rendezVous);
            entityManager.getTransaction().commit();
            System.out.println("Rendez-vous added successfully");
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public RendezVous get(Long id) {
        try {
            return entityManager.find(RendezVous.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void modify(RendezVous rendezVous, Long id) {
        entityManager.getTransaction().begin();
        try {
            RendezVous existingRendezVous = entityManager.find(RendezVous.class, id);
            if (existingRendezVous != null) {
                existingRendezVous.setPatient(rendezVous.getPatient());
                existingRendezVous.setPersonnel(rendezVous.getPersonnel());
                entityManager.merge(existingRendezVous);
                entityManager.getTransaction().commit();
                System.out.println("Rendez-vous updated successfully");
            } else {
                System.out.println("Rendez-vous not found for modification");
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
            RendezVous rendezVous = entityManager.find(RendezVous.class, id);
            if (rendezVous != null) {
                entityManager.remove(rendezVous);
                entityManager.getTransaction().commit();
                System.out.println("Rendez-vous deleted successfully");
            } else {
                System.out.println("Rendez-vous not found for deletion");
            }
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
}
