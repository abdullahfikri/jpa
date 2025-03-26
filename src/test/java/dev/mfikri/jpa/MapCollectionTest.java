package dev.mfikri.jpa;

import dev.mfikri.jpa.entity.Member;
import dev.mfikri.jpa.entity.Name;
import dev.mfikri.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class MapCollectionTest {
    @Test
    void testCreate() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Member member = new Member();
        Name name = new Name();
        name.setFirstName("Johny");



        member.setName(name);
        member.setEmail("example1@mail.com");
        member.setSkills(new HashMap<>());
        member.getSkills().put("JAVA", 90);
        member.getSkills().put("REACTJS", 85);
        member.getSkills().put("NodeJS", 80);

        entityManager.persist(member);

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
