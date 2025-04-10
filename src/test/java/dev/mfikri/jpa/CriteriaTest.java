package dev.mfikri.jpa;

import dev.mfikri.jpa.entity.Brand;
import dev.mfikri.jpa.entity.Product;
import dev.mfikri.jpa.entity.SimpleBrand;
import dev.mfikri.jpa.util.JpaUtil;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

public class CriteriaTest {
    @Test
    void criteriaQuery() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteria = builder.createQuery(Brand.class);
        Root<Brand> b = criteria.from(Brand.class);
        criteria.select(b); // like select b from Brand b

        TypedQuery<Brand> query = entityManager.createQuery(criteria);
        List<Brand> brands = query.getResultList();
        for (Brand brand : brands) {
            System.out.println(brand.getId() + " : " + brand.getName());
        }


        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaQueryNonEntity() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
        Root<Brand> b = criteria.from(Brand.class);
        // select b.id, b.name from Brand b;
        criteria.select(builder.array(b.get("id"), b.get("name")));
        TypedQuery<Object[]> query = entityManager.createQuery(criteria);
        List<Object[]> result = query.getResultList();
        for (Object[] objects : result) {
            System.out.println("Id : " + objects[0]);
            System.out.println("Name : " + objects[1 ]);
        }

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaQueryConstructor() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SimpleBrand> criteria = builder.createQuery(SimpleBrand.class);

        Root<Brand> b = criteria.from(Brand.class);
        criteria.select(builder.construct(SimpleBrand.class, b.get("id"), b.get("name")));

        TypedQuery<SimpleBrand> query = entityManager.createQuery(criteria);
        List<SimpleBrand> simpleBrands = query.getResultList();

        for (SimpleBrand simpleBrand : simpleBrands) {
            System.out.println("Id: " + simpleBrand.getId());
            System.out.println("Name: " + simpleBrand.getName());
        }


        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaWhereClause() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SimpleBrand> criteria = builder.createQuery(SimpleBrand.class);
        Root<Brand> b = criteria.from(Brand.class);
        criteria.select(builder.construct(SimpleBrand.class, b.get("id"), b.get("name")));
//        criteria.where(
//                builder.or(
//                    builder.equal(b.get("name"), "Xiaomi"),
//                    builder.isNotNull(b.get("updatedAt"))
//                )
//        );

//        criteria.where(
//                builder.and(
//                        builder.equal(b.get("name"), "Xiaomi"),
//                        builder.isNotNull(b.get("updatedAt"))
//                )
//        );

        criteria.where(
                builder.equal(b.get("name"), "Xiaomi"),
                builder.isNotNull(b.get("updatedAt"))

        );


        TypedQuery<SimpleBrand> query = entityManager.createQuery(criteria);
        List<SimpleBrand> brands = query.getResultList();

        for (SimpleBrand brand : brands) {
            System.out.println("Id: " + brand.getId());
            System.out.println("Name: " + brand.getName());
        }


        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaJoinClause() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);

        // select p from product p join p.brand b
        Root<Product> p = criteria.from(Product.class);
        Join<Product, Brand> b = p.join("brand");

        // select p from product p join p.brand b where b.name = 'sumsang'
        criteria.select(p);
        criteria.where(builder.equal(b.get("id"), "sumsang"));

        TypedQuery<Product> query = entityManager.createQuery(criteria);
        List<Product> products = query.getResultList();

        for (Product product : products) {
            System.out.println("Name : " + product.getName());
        }

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaNamedParameter() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);

        // select p from product p join p.brand b
        Root<Product> p = criteria.from(Product.class);
        Join<Product, Brand> b = p.join("brand");

        ParameterExpression<String> parameter = builder.parameter(String.class);

        // select p from product p join p.brand b where b.id = 'sumsang'
        criteria.select(p);
        criteria.where(builder.equal(b.get("id"), parameter));

        TypedQuery<Product> query = entityManager.createQuery(criteria);
        query.setParameter(parameter, "xiaomi");

        List<Product> products = query.getResultList();

        for (Product product : products) {
            System.out.println("Name : " + product.getName());
        }

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaAggregate() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);

        // select p from Product p join p.brand b
        Root<Product> p = criteria.from(Product.class);
        Join<Product, Brand> b = p.join("brand");

        criteria.select(builder.array(
                b.get("id"),
                builder.min(p.get("price")),
                builder.max(p.get("price")),
                builder.avg(p.get("price"))
        ));

        criteria.groupBy(b.get("id"));
        criteria.having(builder.greaterThan(builder.min(p.get("price")), 500_000L));

        TypedQuery<Object[]> query = entityManager.createQuery(criteria);
        List<Object[]> result = query.getResultList();

        for (Object[] o : result) {
            System.out.println("Brand id: " + o[0]);
            System.out.println("Min: " + o[1]);
            System.out.println("Max: " + o[2]);
            System.out.println("Average: " + o[3]);
        }


        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void criteriaUpdated() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Brand> criteria = builder.createCriteriaUpdate(Brand.class);
        // select b from Brand b
        Root<Brand> b = criteria.from(Brand.class);

        criteria.set(b.get("name"), "Apple updated Again");
        criteria.set(b.get("description"), "Description for the Apple");
        criteria.set(b.get("updatedAt"), LocalDateTime.now());

        criteria.where(builder.equal(b.get("id"), "apple"));

        Query query = entityManager.createQuery(criteria);
        int impactRecords = query.executeUpdate();

        System.out.println("Success update " + impactRecords + " records");

        entityTransaction.commit();
        entityManager.close();
    }
}
