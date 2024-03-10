package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "currency")
public class CurrencyEntity {
    @Id
    private String abbreviation;
    private String name;
    private double rateToUSD;

    public CurrencyEntity(String abbreviation, String name, double rateToUSD) {
        this.abbreviation = abbreviation;
        this.name = name;
        this.rateToUSD = rateToUSD;
    }

    public CurrencyEntity() {

    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public double getRateToUSD() {
        return rateToUSD;
    }

    public String getName() {
        return name;
    }

    public void setRateToUSD(double rateToUSD) {
        this.rateToUSD = rateToUSD;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}
