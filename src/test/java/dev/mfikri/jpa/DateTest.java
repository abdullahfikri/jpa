package dev.mfikri.jpa;

import dev.mfikri.jpa.entity.Category;
import dev.mfikri.jpa.entity.Customer;
import dev.mfikri.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class DateTest {

    @Test
    void testDateAndTime() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Category category = new Category();
        category.setName("Self Development Book");
        category.setDescription("Category for list of self development book");
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());

        entityManager.persist(category);

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
