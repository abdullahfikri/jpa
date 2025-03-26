package dev.mfikri.jpa;

import dev.mfikri.jpa.entity.Department;
import dev.mfikri.jpa.entity.DepartmentId;
import dev.mfikri.jpa.entity.Member;
import dev.mfikri.jpa.entity.Name;
import dev.mfikri.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

public class EmbeddedIdTest {
    @Test
    void testEmbeddedId() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        DepartmentId departmentId = new DepartmentId();
        departmentId.setCompanyId("Widuri");
        departmentId.setDepartmentId("dev");

        Department department = new Department();
        department.setId(departmentId);
        department.setName("Development Department");

        entityManager.persist(department);

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void testEmbeddedIdFind() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        DepartmentId departmentId = new DepartmentId();
        departmentId.setCompanyId("Widuri");
        departmentId.setDepartmentId("dev");

        Department department = entityManager.find(Department.class, departmentId);

        System.out.println(department.getId());
        System.out.println(department.getName());

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
