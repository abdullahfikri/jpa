package dev.mfikri.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions_credit")
public class TransactionCredit extends Transaction{
    @Column(name = "credit_amount")
    private Long CreditAmount;

    public Long getCreditAmount() {
        return CreditAmount;
    }

    public void setCreditAmount(Long creditAmount) {
        CreditAmount = creditAmount;
    }
}
