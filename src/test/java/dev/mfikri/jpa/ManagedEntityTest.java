package dev.mfikri.jpa;

import dev.mfikri.jpa.entity.Brand;
import dev.mfikri.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class ManagedEntityTest {
    // Manage entity just life in the transaction lifecycle, after transaction is commited or rollback, the managed entity will be unmanaged entity

    @Test
    void managedEntity() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        // unmanaged entity
        Brand brand = new Brand();
        brand.setId("apple");
        brand.setName("Apple");
        entityManager.persist(brand); // change unmanaged entity to managed entity

        brand.setName("Apple Mango"); // will be update even if we don't call merge method to update because the brand is managed entity

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void findManagedEntity() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Brand brand = entityManager.find(Brand.class, "apple");  // managed entity
        brand.setName("Apple Indonesia"); // will be update even if we don't call merge method to update because the brand is managed entity

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void detachManagedEntity() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Brand brand = entityManager.find(Brand.class, "apple");  // managed entity
        entityManager.detach(brand); // change managed entity to be unmanaged entity
        brand.setName("Apple Indonesia Update"); // will not update if we don't call merge method to update because the brand is unmanaged entity

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void findManagedEntityAfterCommit() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Brand brand = entityManager.find(Brand.class, "apple");  // managed entity

        entityTransaction.commit();

        brand.setName("Apple Indonesia"); // will not update if we don't call the managed entity in the transaction lifecycle

        entityManager.close();
        entityManagerFactory.close();
    }
}
