package org.ticketing.model;
// rest of Ticket.java code

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@Table(name = "tickets")
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticketId;
    private String status; // AVAILABLE, SOLD
    private String vendorId;
    private String customerId;
    private Long createdAt;
    private Long soldAt;
}