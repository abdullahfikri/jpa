package dev.mfikri.jpa;

import dev.mfikri.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EntityManagerTest {
    @Test
    void create() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Assertions.assertNotNull(entityManager);
        // operasi db (CRUD)

        entityManager.close();
        entityManagerFactory.close();
    }
}
