package dev.mfikri.jpa;

import dev.mfikri.jpa.entity.Customer;
import dev.mfikri.jpa.entity.Member;
import dev.mfikri.jpa.entity.Name;
import dev.mfikri.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

public class EmbeddedTest {

    @Test
    void createEmbedded() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Member member = new Member();
        Name name = new Name();
        name.setTitle("Member Example");
        name.setFirstName("John");
        name.setLastName("Doe");

        member.setEmail("example@email.com");
        member.setName(name);

        entityManager.persist(member);

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
