package dao;

import model.*;
import jakarta.persistence.*;

public class ChambreDAO implements IClass<Chambre> {
    private EntityManager entityManager;

    public ChambreDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mrm24jee");
        entityManager = emf.createEntityManager();
    }

    @Override
    public void add(Chambre chambre) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(chambre);
            entityManager.getTransaction().commit();
            System.out.println("Chambre added successfully");
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public Chambre get(Long id) {
        try {
            return entityManager.find(Chambre.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void modify(Chambre chambre, Long id) {
        entityManager.getTransaction().begin();
        try {
            Chambre existingChambre = entityManager.find(Chambre.class, id);
            if (existingChambre != null) {
                existingChambre.setRoomNumber(chambre.getRoomNumber());
                existingChambre.setTypeChambre(chambre.getTypeChambre());
                existingChambre.setIsDisponible(chambre.getIsDisponible());
                entityManager.merge(existingChambre);
                entityManager.getTransaction().commit();
                System.out.println("Chambre updated successfully");
            } else {
                System.out.println("Chambre not found for modification");
            }
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(Long id) {
        entityManager.getTransaction().begin();
        try {
            Chambre chambre = entityManager.find(Chambre.class, id);
            if (chambre != null) {
                entityManager.remove(chambre);
                entityManager.getTransaction().commit();
                System.out.println("Chambre deleted successfully");
            } else {
                System.out.println("Chambre not found for deletion");
            }
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
}
