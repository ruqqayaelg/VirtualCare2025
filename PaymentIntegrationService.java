package service;

import databases.PaymentTransactionDatabase;
import model.Bill;
import model.PaymentSystem;
import model.enums.PaymentStatus;

public class PaymentIntegrationService {

    private final PaymentSystem paymentSystem = new PaymentSystem();
    private final PaymentTransactionDatabase paymentDb;

    public PaymentIntegrationService(PaymentTransactionDatabase paymentDb) {
        this.paymentDb = paymentDb;
    }

    public PaymentStatus processPayment(Bill bill) {

        PaymentStatus status = paymentSystem.processPayment(bill);

        String record = bill.getBillId() + ";" +
                bill.getPatient().getId() + ";" +
                bill.getAmount() + ";" +
                status;

        paymentDb.add(record);

        return status;
    }
}
