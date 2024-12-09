package org.ticketing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ticketing.model.Ticket;
import org.ticketing.repository.TicketRepository;
import java.util.concurrent.locks.ReentrantLock;
import java.time.Instant;

@Service
public class TicketPoolService {
    private final ReentrantLock lock = new ReentrantLock();

    @Autowired
    private TicketRepository ticketRepository;

    @Transactional
    public boolean addTicket(String vendorId) {
        lock.lock();
        try {
            Ticket ticket = new Ticket();
            ticket.setTicketId("TICKET-" + System.nanoTime());
            ticket.setStatus("AVAILABLE");
            ticket.setVendorId(vendorId);
            ticket.setCreatedAt(Instant.now().toEpochMilli());

            ticketRepository.save(ticket);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            lock.unlock();
        }
    }

    @Transactional
    public boolean purchaseTicket(String customerId) {
        lock.lock();
        try {
            Ticket ticket = ticketRepository.findFirstByStatus("AVAILABLE");

            if (ticket == null) {
                return false;
            }

            ticket.setStatus("SOLD");
            ticket.setCustomerId(customerId);
            ticket.setSoldAt(Instant.now().toEpochMilli());

            ticketRepository.save(ticket);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            lock.unlock();
        }
    }
}