package model;

import databases.PharmacyDatabase;

import java.time.LocalDate;
import java.time.YearMonth;

public class PharmacySystem {

    private PharmacyDatabase pharmacyDb = new PharmacyDatabase();

    public void receivePrescription(Prescription p) {

        pharmacyDb.addRecord(
                p.getPrescriptionId(),
                p.getPatientId(),         // <-- FIXED
                p.getProviderId(),        // <-- FIXED
                p.getMedication(),
                p.getDosage(),
                p.getIssueDate().toString(),
                "RECEIVED"
        );

    }
    public boolean canDispense(Prescription p) {

        YearMonth currentMonth = YearMonth.now();

        int dispensedThisMonth =
                pharmacyDb.countMonthlyDispenses(
                        p.getPrescriptionId(),
                        currentMonth
                );

        return dispensedThisMonth < p.getDispensingLimit();
    }
    public void recordDispense(Prescription p) {
        pharmacyDb.addRecord(
                p.getPrescriptionId(),
                p.getPatientId(),
                p.getProviderId(),
                p.getMedication(),
                p.getDosage(),
                LocalDate.now().toString(),
                "DISPENSED"
        );
    }


    public String accessPrescription(String prescriptionId) {
        return pharmacyDb.findByPrescriptionId(prescriptionId);
    }
}
