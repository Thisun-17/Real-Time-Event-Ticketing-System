package com.service;

import com.model.Configuration;
import com.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

    @Autowired
    private ConfigurationRepository configurationRepository;

    public Configuration getActiveConfiguration() {
        return configurationRepository.findByActive(true);
    }

    public Configuration updateConfiguration(Configuration configuration) {
        Configuration existingConfig = configurationRepository.findByActive(true);
        if (existingConfig != null) {
            existingConfig.setActive(false);
            configurationRepository.save(existingConfig);
        }

        configuration.setActive(true);
        return configurationRepository.save(configuration);
    }
}