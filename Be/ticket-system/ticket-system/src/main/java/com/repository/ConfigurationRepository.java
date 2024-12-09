// ConfigurationRepository.java
package com.repository;

import com.model.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
    Configuration findByActive(boolean active);
}