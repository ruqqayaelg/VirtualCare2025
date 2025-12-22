package model;

import model.enums.PaymentStatus;

public class PaymentSystem {

    public PaymentStatus processPayment(Bill bill) {
        // stub
        return bill.getAmount() > 0 ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;
    }
}
