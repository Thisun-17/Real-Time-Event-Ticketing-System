// Path: src/main/java/org/ticketing/repository/SystemConfigRepository.java
package org.ticketing.repository;

import org.ticketing.entity.SystemConfig;  // Updated import
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, Long> {
    SystemConfig findByIsActive(boolean isActive);
}