package service;

import databases.FeedbackDatabase;
import model.*;
import model.enums.RatingLevel;

import java.util.List;

public class FeedbackProcessor {

    private final FeedbackDatabase feedbackDb;

    public FeedbackProcessor(FeedbackDatabase feedbackDb) {
        this.feedbackDb = feedbackDb;
    }

    public Feedback submitFeedback(String feedbackId, Patient patient,
                                   HealthcareProvider provider, RatingLevel rating,
                                   String comments) {

        Feedback fb = new Feedback(feedbackId, patient, provider, rating, comments);
        feedbackDb.add(fb);
        return fb;
    }

    public List<Feedback> getAllFeedback() {
        return feedbackDb.findAll();
    }
}
