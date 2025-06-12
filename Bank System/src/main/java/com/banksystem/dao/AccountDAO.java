package com.banksystem.dao;

import com.banksystem.entity.Account;
import com.banksystem.util.HibernateUtil;
import com.banksystem.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;

import java.util.List;

public class AccountDAO {

    public void createAccount(Account account) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(account);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Account getAccountById(int accountId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Account.class, accountId);
        } finally {
            em.close();
        }
    }

    public Account getAccountByNumber(String accountNumber) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Account a WHERE a.accountNumber = :number";
            return session.createQuery(hql, Account.class)
                    .setParameter("number", accountNumber)
                    .getSingleResult();
        } catch (NoResultException e) {
            // No account found with given number
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Account> getAccountsByUserId(int userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Account> query = em.createQuery("SELECT a FROM Account a WHERE a.user.id = :userId", Account.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void updateAccount(Account account) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(account);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void deleteAccount(int accountId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Account account = em.find(Account.class, accountId);
            if (account != null) {
                em.getTransaction().begin();
                em.remove(account);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }
}
