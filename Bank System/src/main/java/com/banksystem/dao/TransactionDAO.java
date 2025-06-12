package com.banksystem.dao;
import com.banksystem.entity.Transaction;


import com.banksystem.util.HibernateUtil;
import org.hibernate.Session;
import java.util.List;

public class TransactionDAO {

    public void saveTransaction(Transaction transaction) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            org.hibernate.Transaction tx = session.beginTransaction();
            session.saveOrUpdate(transaction);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Transaction getTransactionById(int transactionId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Transaction.class, transactionId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Transaction> getAllTransactions() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Transaction", Transaction.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }


    public List<Transaction> getTransactionsByAccountId(int accountId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Transaction t WHERE t.account.accountId = :accountId";
            return session.createQuery(hql, Transaction.class)
                    .setParameter("accountId", accountId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }




}
