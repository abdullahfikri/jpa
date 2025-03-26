package dev.mfikri.jpa;

import dev.mfikri.jpa.entity.Customer;
import dev.mfikri.jpa.entity.Member;
import dev.mfikri.jpa.entity.Name;
import dev.mfikri.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CollectionTest {
    @Test
    void testCreateCollection() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Member member = new Member();
        Name name = new Name();
        name.setFirstName("Muhammad Fikri");



        member.setName(name);
        member.setEmail("example@mail.com");
        member.setHobbies(new ArrayList<>());
        member.getHobbies().add("Read A book");
        member.getHobbies().add("Gaming");
        member.getHobbies().add("Coding");

        entityManager.persist(member);

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void testUpdateCollection() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Member member = entityManager.find(Member.class, 2);

        member.getHobbies().add("Fishing");

        entityManager.merge(member);

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
