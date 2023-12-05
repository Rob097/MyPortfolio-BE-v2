package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.FeedbackDao;
import com.myprojects.myportfolio.core.repositories.FeedbackRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service(value = "FeedbackService")
public class FeedbackService implements FeedbackServiceI {

    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public void save(FeedbackDao feedback) {
        // If feedback ip is not null, search it and if it exists we will do some custom logic:
        if (feedback.getIp() != null) {
            feedbackRepository.findByIp(feedback.getIp()).ifPresentOrElse(
                    // If feedback ip exists, we will update it:
                    existingFeedback -> {

                        // Concatenate newFeatures if it is not null:
                        String existingNewFeatures = existingFeedback.getNewFeatures();
                        String newFeatures = feedback.getNewFeatures();
                        if (existingNewFeatures != null && newFeatures != null) {
                            existingFeedback.setNewFeatures(existingNewFeatures + ", " + newFeatures);
                        } else if (existingNewFeatures == null && newFeatures != null) {
                            existingFeedback.setNewFeatures(newFeatures);
                        }

                        // replace register form if it is not null:
                        if (feedback.getRegisterForm() != null) {
                            existingFeedback.setRegisterForm(feedback.getRegisterForm());
                        }

                        feedbackRepository.save(existingFeedback);
                    },
                    // If feedback ip does not exist, we will save it:
                    () -> feedbackRepository.save(feedback)
            );
        } else {
            // If feedback ip is null, we will save it:
            feedbackRepository.save(feedback);
        }
    }
}
