// Path: src/main/java/org/ticketing/entity/SystemConfig.java
package org.ticketing.entity;

import jakarta.persistence.*;
import lombok.Data; // Ensure Lombok is configured properly in your build tool, like Maven or Gradle
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@Table(name = "system_config")
@NoArgsConstructor
@AllArgsConstructor
public class SystemConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;
    private boolean isActive;
}