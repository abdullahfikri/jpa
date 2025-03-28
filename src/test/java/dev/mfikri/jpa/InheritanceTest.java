package dev.mfikri.jpa;

import dev.mfikri.jpa.entity.*;
import dev.mfikri.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class InheritanceTest {
    @Test
    void singleTableInsert() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Employee employee = new Employee();
        employee.setId("rina");
        employee.setName("Rina Wati");
        entityManager.persist(employee);

        Manager manager = new Manager();
        manager.setId("joko");
        manager.setName("Joko Moro");
        manager.setTotalEmployee(20);
        entityManager.persist(manager);

        VicePresident vp = new VicePresident();
        vp.setId("budi");
        vp.setName("Budi Andi");
        vp.setTotalManager(5);
        entityManager.persist(vp);

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void singleTableFind() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Manager manager = entityManager.find(Manager.class, "joko");
        Assertions.assertEquals(20, manager.getTotalEmployee());

        // call vp from parent class employee (polymorphism)
        Employee employee = entityManager.find(Employee.class, "budi");
        VicePresident vp = (VicePresident) employee;

        Assertions.assertEquals(5, vp.getTotalManager());

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void joinedTableInset() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        PaymentGopay paymentGopay = new PaymentGopay();
        paymentGopay.setId("gopay1");
        paymentGopay.setAmount(100_000L);
        paymentGopay.setGopayId("09821212GP");
        entityManager.persist(paymentGopay);

        PaymentCreditCard paymentCreditCard = new PaymentCreditCard();
        paymentCreditCard.setId("cc1");
        paymentCreditCard.setAmount(1_000_000L);
        paymentCreditCard.setMaskedCard("4555-22233");
        paymentCreditCard.setBank("BSI");
        entityManager.persist(paymentCreditCard);

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void joinedTableFind() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        PaymentGopay paymentGopay = entityManager.find(PaymentGopay.class, "gopay1");
        Assertions.assertEquals(1_00_000L, paymentGopay.getAmount());

        PaymentCreditCard creditCard = entityManager.find(PaymentCreditCard.class, "cc1");
        Assertions.assertEquals(1_000_000L, creditCard.getAmount());

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void joinedTableFindParent() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        // warning: the JPA will query join to the all the child entity to find the gopay1 Id, so it is not recommended to find from the parent class if we use joined table
        Payment gopay = entityManager.find(Payment.class, "gopay1");

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void testPerClassInsert() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Transaction transaction = new Transaction();
        transaction.setId("t1");
        transaction.setBalance(1000_000L);
        transaction.setCreatedAt(LocalDateTime.now());
        entityManager.persist(transaction);

        TransactionCredit credit = new TransactionCredit();
        credit.setId("c1");
        credit.setBalance(1000_000L);
        credit.setCreatedAt(LocalDateTime.now());
        credit.setCreditAmount(500_000L);
        entityManager.persist(credit);

        TransactionDebit debit = new TransactionDebit();
        debit.setId("d1");
        debit.setBalance(1000_000L);
        debit.setCreatedAt(LocalDateTime.now());
        debit.setDebitAmount(300_000L);
        entityManager.persist(debit);


        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void testPerClassFind() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        // NOTE: doing select from child class will fast because not need join table with parent
        TransactionCredit credit = entityManager.find(TransactionCredit.class, "c1");
        TransactionDebit debit = entityManager.find(TransactionDebit.class, "d1");


        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void testPerClassFindParent() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        // NOTE: doing select from parent class is not recommended since the query is slow, and if the child contain same id with the parent, the find method will return error;
        Transaction transaction = entityManager.find(Transaction.class, "t1");


        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void mappedSuperClass() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Brand brand = new Brand();

        brand.setId("mi");
        brand.setName("Xiaomi");
        brand.setDescription("Xiaomi Global");
        brand.setCreatedAt(LocalDateTime.now());
        brand.setUpdatedAt(LocalDateTime.now());

        entityManager.persist(brand);

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
