package dev.mfikri.jpa;

import dev.mfikri.jpa.entity.Customer;
import dev.mfikri.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

public class BasicDataTypeTest {
    @Test
    void testInsert() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Customer customer = new Customer();
        customer.setId("2");
        customer.setName("Budi");
        customer.setAge((byte) 30);
        customer.setMarried(true);

        entityManager.persist(customer);

        entityTransaction.commit();
        entityManager.close();
    }
}
