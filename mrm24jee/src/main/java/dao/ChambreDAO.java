package dao;

import model.Chambre;
import jakarta.persistence.*;
import java.util.Collections;
import java.util.List;

public class ChambreDAO implements IClass<Chambre> {
    private EntityManager entityManager;

    public ChambreDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mrm24jee");
        entityManager = emf.createEntityManager();
    }

    @Override
    public void add(Chambre chambre) {
        try {
            entityManager.getTransaction().begin();
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
    public List<Chambre> getAll() {
        try {
            return entityManager.createQuery("SELECT c FROM Chambre c", Chambre.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public void modify(Chambre chambre, Long id) {
        try {
            entityManager.getTransaction().begin();
            Chambre existingChambre = entityManager.find(Chambre.class, id);
            if (existingChambre != null) {
                existingChambre.setRoomNumber(chambre.getRoomNumber());
                existingChambre.setRoomType(chambre.getRoomType());
                existingChambre.setDisponible(chambre.isDisponible());
                entityManager.merge(existingChambre);
                entityManager.getTransaction().commit();
                System.out.println("Chambre updated successfully");
            } else {
                System.out.println("Chambre not found for modification");
            }
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public void delete(Long id) {
        try {
            entityManager.getTransaction().begin();
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
