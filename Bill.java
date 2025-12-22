package model;

import model.enums.BillStatus;
import java.time.LocalDate;

public class Bill {

    private String billId;
    private Patient patient;
    private HealthcareProvider provider;
    private double amount;
    private BillStatus status;
    private LocalDate date;

    public Bill(String billId, Patient patient, HealthcareProvider provider,
                double amount, LocalDate date, BillStatus status) {

        this.billId = billId;
        this.patient = patient;
        this.provider = provider;
        this.amount = amount;
        this.date = date;
        this.status = status;
    }

    // --------- Getters ----------
    public String getBillId() { return billId; }
    public Patient getPatient() { return patient; }
    public HealthcareProvider getProvider() { return provider; }
    public double getAmount() { return amount; }
    public LocalDate getDate() { return date; }
    public BillStatus getStatus() { return status; }

    // --------- Logic Methods ----------
    public boolean isPaid() {
        return status == BillStatus.PAID;
    }

    public void markPaid() {
        this.status = BillStatus.PAID;
    }

    public void updateStatus(BillStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return billId + " | " + provider.getName() + " | " + amount + " | " + status;
    }
    public String getPatientId() {
        return patient != null ? patient.getId() : null;
    }




}
