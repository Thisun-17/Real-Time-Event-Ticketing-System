// TicketRepository.java
package com.repository;

import com.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    long countByStatus(String status);
}





