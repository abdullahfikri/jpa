package dev.mfikri.jpa;

import dev.mfikri.jpa.entity.Category;
import dev.mfikri.jpa.entity.Image;
import dev.mfikri.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class LargeObjectTest {
    @Test
    void largeObject() throws IOException {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Image image = new Image();
        image.setName("Contoh Image");
        image.setDescription("Contoh descripsi");

        byte[] bytes = Files.readAllBytes(Path.of(getClass().getResource("/images/example.png").getPath()));

        image.setImage(bytes);

        entityManager.persist(image);

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
