package dev.mfikri.jpa;

import dev.mfikri.jpa.entity.Category;
import dev.mfikri.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GeneratedValueTest {
    @Test
    void testCreateCategory() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Category category = new Category();
        category.setName("Iphonge 17");
        category.setDescription("Getget termansyur abad pertengahan");

        entityManager.persist(category);

        Assertions.assertNotNull(category.getId());

        System.out.println(category.getId());

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
