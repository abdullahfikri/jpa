package dev.mfikri.jpa;

import dev.mfikri.jpa.entity.*;
import dev.mfikri.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

public class EntityRelationshipTest {
    @Test
    void oneToOnePersist() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        User user = new User();
        user.setId("fikri");
        user.setName("Muhammad Fikri");
        entityManager.persist(user);

        Credential credential = new Credential();
        credential.setId("fikri");
        credential.setEmail("example@test.com");
        credential.setPassword("secretpasswrod");
        entityManager.persist(credential);

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void oneToOneFind() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        User user = entityManager.find(User.class, "fikri");
        Assertions.assertNotNull(user.getWallet());
        Assertions.assertNotNull(user.getCredential());

        Assertions.assertEquals(user.getId(), user.getCredential().getId());
        Assertions.assertEquals(user.getId(), user.getWallet().getUser().getId());

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void oneToOneJoinColumn() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        User user = entityManager.find(User.class, "fikri");

        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(1_000_000L);

        entityManager.persist(wallet);

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void oneToManyInsert() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Brand brand = new Brand();
        brand.setId("sumsang");
        brand.setName("Sumsang");
        brand.setDescription("Description of sumsang phone");
        entityManager.persist(brand);

        Product product1 = new Product();
        product1.setId("p1");
        product1.setName("Sumsang Galaxy 100");
        product1.setPrice(15_000_000L);
        product1.setDescription("The next gen smartphone by sumsang");
        product1.setBrand(brand);
        entityManager.persist(product1);

        Product product2 = new Product();
        product2.setId("p2");
        product2.setName("Sumsang Galaxy 200");
        product2.setPrice(25_000_000L);
        product2.setDescription("The next next gen smartphone by sumsang");
        product2.setBrand(brand);
        entityManager.persist(product2);

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void oneToManyFind() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Brand brand = entityManager.find(Brand.class, "sumsang");
        Assertions.assertNotNull(brand.getProducts());
        Assertions.assertEquals(2, brand.getProducts().size());

        brand.getProducts().forEach(System.out::println);

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void manyToManyInsert() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        User user = entityManager.find(User.class, "fikri");

//        user.setLikes(new HashSet<>()); // use this for first time we created a likes set;

        Product product1 = entityManager.find(Product.class, "p1");
        Product product2 = entityManager.find(Product.class, "p2");

        user.getLikes().add(product1);
        user.getLikes().add(product2);

        entityManager.merge(user);

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void manyToManyFindAndUpdate() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        User user = entityManager.find(User.class, "fikri");

        user.getLikes().forEach(product -> System.out.println(product.getName()));

        Optional<Product> first = user.getLikes().stream().findFirst();

        first.ifPresent(product -> user.getLikes().remove(product));

        entityManager.merge(user);

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void fetch() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Product product = entityManager.find(Product.class, "p1");
        Brand brand = product.getBrand();
        System.out.println(brand.getName());
        //Brand brand = entityManager.find(Brand.class, "sumsang");
        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }


}
