package com.example.medcheckb8.db.repository.custom;

import com.example.medcheckb8.db.dto.response.ApplicationResponse;
import com.example.medcheckb8.db.entities.Application;

public interface ApplicationRepo {
    Application update(Application application);

    ApplicationResponse viewApplication(Application application);
}
