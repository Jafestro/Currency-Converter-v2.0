package dao;

import entity.CurrencyEntity;
import entity.TransactionEntity;
import jakarta.persistence.EntityManager;

import java.sql.SQLException;

public class TransactionDao {
    public int persist(TransactionEntity transaction) throws SQLException {
        EntityManager em = datasource.MariaDbConnection.getInstance();
        if (em == null) {
            System.out.println("Connection failed");
            return -1;
        }
        em.getTransaction().begin();
        em.persist(transaction);
        em.getTransaction().commit();
        return 1;
    }
}
