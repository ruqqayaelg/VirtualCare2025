package service;

import databases.BillingDatabase;
import model.*;
import model.enums.BillStatus;

import java.time.LocalDate;
import java.util.List;

public class BillingService {

    private final BillingDatabase billingDb;

    public BillingService(BillingDatabase billingDb) {
        this.billingDb = billingDb;
    }

    /**
     * Generates a bill for a completed consultation.
     */
    public Bill generateConsultationBill(Appointment appt, double amount) {

        String billId = "B" + System.currentTimeMillis();

        Bill bill = new Bill(
                billId,
                appt.getPatient(),
                appt.getProvider(),
                amount,
                LocalDate.now(),
                BillStatus.UNPAID
        );

        billingDb.add(bill);
        return bill;
    }

    /**
     * Marks a bill as PAID and saves changes.
     */
    public void markPaid(Bill bill) {
        bill.updateStatus(BillStatus.PAID);
        billingDb.save();
    }

    /**
     * Returns all bills belonging to a patient.
     */
    public List<Bill> getBillsFor(Patient p) {
        return billingDb.findAll()
                .stream()
                .filter(b -> b.getPatient().equals(p))
                .toList();
    }
    public boolean hasBillFor(Appointment appt) {
        return billingDb.findAll().stream()
                .anyMatch(b ->
                        b.getPatient().equals(appt.getPatient()) &&
                                b.getProvider().equals(appt.getProvider()) &&
                                b.getDate().equals(LocalDate.now())
                );
    }

}
