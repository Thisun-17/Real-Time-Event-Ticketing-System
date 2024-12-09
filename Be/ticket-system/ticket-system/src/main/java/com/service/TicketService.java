package com.service;

import com.dto.TicketRequest;
import com.model.Ticket;
import com.model.Vendor;
import com.repository.TicketRepository;
import com.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private VendorRepository vendorRepository;

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Ticket createTicket(TicketRequest request) {
        log.info("Creating ticket with request: {}", request);

        // Find vendor
        Vendor vendor = vendorRepository.findById(request.getVendorId())
                .orElseThrow(() -> new RuntimeException("Vendor not found with ID: " + request.getVendorId()));

        // Create ticket
        Ticket ticket = new Ticket();
        ticket.setStatus(request.getStatus());
        ticket.setVendor(vendor);

        // Save ticket
        Ticket savedTicket = ticketRepository.save(ticket);

        // Update vendor's ticket count - using Integer
        int currentCount = (vendor.getTicketsProduced() != null) ? vendor.getTicketsProduced() : 0;
        vendor.setTicketsProduced(currentCount + 1);
        vendorRepository.save(vendor);

        return savedTicket;
    }

    public Ticket findById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));
    }

    public Ticket updateTicket(Long id, TicketRequest request) {
        Ticket ticket = findById(id);

        if (request.getStatus() != null) {
            ticket.setStatus(request.getStatus());
        }

        if (request.getVendorId() != null) {
            Vendor vendor = vendorRepository.findById(request.getVendorId())
                    .orElseThrow(() -> new RuntimeException("Vendor not found with ID: " + request.getVendorId()));
            ticket.setVendor(vendor);
        }

        return ticketRepository.save(ticket);
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
}