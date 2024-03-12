package dao;

import entity.CurrencyEntity;
import jakarta.persistence.EntityManager;


import java.sql.SQLException;
import java.util.ArrayList;

public class CurrencyDao {
    public int persist(CurrencyEntity currency) throws SQLException {
        EntityManager em = datasource.MariaDbConnection.getInstance();
        if (em == null) {
            System.out.println("Connection failed");
            return -1;
        }
        em.getTransaction().begin();
        em.persist(currency);
        em.getTransaction().commit();
        return 1;
    }

    public double getRateByAbbreviation(String abbreviation) {
        EntityManager em = datasource.MariaDbConnection.getInstance();
        if (em == null) {
            System.out.println("Connection failed");
            return -1;
        }
        CurrencyEntity currency = em.find(CurrencyEntity.class, abbreviation);
        if (currency != null) {
            return currency.getRateToUSD();
        }
        return 0;
    }

    public CurrencyEntity getCurrencyByAbbreviation(String abbreviation) {
        EntityManager em = datasource.MariaDbConnection.getInstance();
        if (em == null) {
            System.out.println("Connection failed");
            return null;
        }
        CurrencyEntity currency = em.find(CurrencyEntity.class, abbreviation);
        if (currency != null) {
           return currency;
        }
        return null;
    }

    public ArrayList<String> getRates() {
        ArrayList<String> rates = new ArrayList<>();
        EntityManager em = datasource.MariaDbConnection.getInstance();
        if (em == null) {
            System.out.println("Connection failed");
            rates.add("-1");
            return rates;
        }
        em.createQuery("SELECT c FROM CurrencyEntity c", CurrencyEntity.class).getResultList().forEach(currency -> {
            rates.add(currency.getAbbreviation());
        });
        return rates;
    }

    public int checkIfAbbreviationExists(String abbreviation) throws SQLException {
        EntityManager em = datasource.MariaDbConnection.getInstance();
        if (em == null) {
            System.out.println("Connection failed");
            return -1;
        }
        CurrencyEntity currency = em.find(CurrencyEntity.class, abbreviation);
        if (currency != null) {
            System.out.println("Currency already exists");
            return 1;
        }
        return 0;
    }
}
