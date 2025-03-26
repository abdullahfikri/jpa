package dev.mfikri.jpa;

import dev.mfikri.jpa.entity.Customer;
import dev.mfikri.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

public class ColumnTest {
    @Test
    void testColumn() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Customer customer = new Customer();
        customer.setId("15");
        customer.setName("Muhammad Fikri");
        customer.setPrimaryEmail("contoh@example.com");
        customer.setFullName("Muhammad Fikri bin Syaf");

        entityManager.persist(customer);

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
