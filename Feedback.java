package model;

import model.enums.RatingLevel;

public class Feedback {

    private String feedbackId;
    private Patient patient;
    private HealthcareProvider provider;
    private RatingLevel rating;
    private String comments;

    public Feedback(String feedbackId, Patient patient, HealthcareProvider provider,
                    RatingLevel rating, String comments) {
        this.feedbackId = feedbackId;
        this.patient = patient;
        this.provider = provider;
        this.rating = rating;
        this.comments = comments;
    }

    public String getFeedbackSummary() {
        return rating + ": " + comments;
    }

    // Getters
    public String getFeedbackId() { return feedbackId; }
    public Patient getPatient() { return patient; }
    public HealthcareProvider getProvider() { return provider; }
    public RatingLevel getRating() { return rating; }
    public String getComments() { return comments; }

    @Override
    public String toString() {
        return getFeedbackSummary();
    }
}
