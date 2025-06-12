package com.banksystem.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory emf;

    // Static block to initialize EntityManagerFactory when the class loads
    static {
        try {
            emf = Persistence.createEntityManagerFactory("bank_system_pu");
        } catch (Throwable ex) {
            System.err.println("Initial EntityManagerFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Provide an EntityManager
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Cleanly shutdown the factory when the app ends
    public static void shutdown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
