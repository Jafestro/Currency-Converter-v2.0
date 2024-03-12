package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "transaction")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private CurrencyEntity source;
    @ManyToOne
    private CurrencyEntity destination;

    public TransactionEntity(CurrencyEntity source, CurrencyEntity destination) {
        this.source = source;
        this.destination = destination;
    }

    public TransactionEntity() {

    }
}
