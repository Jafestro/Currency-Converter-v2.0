package dao;

import entity.CurrencyEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CurrencyDao {
    public int persist(CurrencyEntity currency) throws SQLException {
        Connection conn = datasource.MariaDbConnection.getConnection();
        if (conn == null) {
            System.out.println("Connection failed");
            return -1;
        }
        String sql = "insert into currency (abbreviation, name, rateToUSD) values (?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, currency.getAbbreviation());
            ps.setString(2, currency.getName());
            ps.setDouble(3, currency.getRateToUSD());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in persist");
        }
        return 1;
    }

    public double getRateByAbbreviation(String abbreviation) {
        Connection conn = datasource.MariaDbConnection.getConnection();
        if (conn == null) {
            System.out.println("Connection failed");
            return -1;
        }
        String sql = "select rateToUSD from currency where abbreviation = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, abbreviation);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in getRateByAbbreviation");
        }
        return 0;
    }

    public ArrayList<String> getRates() {
        ArrayList<String> rates = new ArrayList<>();
        Connection conn = datasource.MariaDbConnection.getConnection();
        if (conn == null) {
            System.out.println("Connection failed");
            rates.add("-1");
            return rates;
        }
        String sql = "select abbreviation from currency";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rates.add(rs.getString("abbreviation"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in getRates");
        }
        return rates;
    }
}
