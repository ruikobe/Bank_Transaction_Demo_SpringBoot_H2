package rui.org.advaitademo.model;

import javax.persistence.*;

/**
 * Bank transactions entity
 *
 * @author Rui Zhu
 *
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "time", columnNames = "time") })
public class Transaction {

    @Id
    @Column(name = "time")
    private String time;

    @Column(name = "amount")
    private double amount;

    // ----------------
    // - CONSTRUCTORS -
    // ----------------

    public Transaction() {
    }

    public Transaction(String time, double amount) {
        this.time = time;
        this.amount = amount;
    }

    // -------------------
    // - SETTERS/GETTERS -
    // -------------------


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    // -------------
    // - TO STRING -
    // -------------


    @Override
    public String toString() {
        return "Transaction{" +
                "time='" + time + '\'' +
                ", amount=" + amount +
                '}';
    }
}
