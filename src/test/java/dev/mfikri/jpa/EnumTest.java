package dev.mfikri.jpa;

import dev.mfikri.jpa.entity.Customer;
import dev.mfikri.jpa.entity.CustomerType;
import dev.mfikri.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

public class EnumTest {
    @Test
    void testEnum() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Customer customer = new Customer();
        customer.setId("3");
        customer.setName("Fikri");
        customer.setPrimaryEmail("contoh@example.com");
        customer.setAge((byte) 24);
        customer.setMarried(false);
        customer.setType(CustomerType.VIP);

        entityManager.persist(customer
        );

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
