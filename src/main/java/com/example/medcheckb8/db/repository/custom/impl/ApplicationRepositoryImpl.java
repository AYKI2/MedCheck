package com.example.medcheckb8.db.repository.custom.impl;

import com.example.medcheckb8.db.dto.response.ApplicationResponse;
import com.example.medcheckb8.db.entities.Application;
import com.example.medcheckb8.db.repository.custom.ApplicationRepo;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRepositoryImpl implements ApplicationRepo {
    @Override
    public Application update(Application application) {
        if (!application.getProcessed()) {
            application.setProcessed(true);
        } else if (application.getProcessed()) {
            application.setProcessed(false);
        }
        return application;
    }

    @Override
    public ApplicationResponse viewApplication(Application application) {
        if (application == null) {
            return null;
        }
        return ApplicationResponse.builder().
                id(application.getId())
                .name(application.getName())
                .date(application.getDate())
                .phoneNumber(application.getPhoneNumber())
                .processed(application.getProcessed())
                .build();
    }
}
