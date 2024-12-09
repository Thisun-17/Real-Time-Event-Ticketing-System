// Path: src/main/java/org/ticketing/thread/CustomerThread.java
package org.ticketing.thread;

import org.ticketing.service.TicketPoolService;  // Updated import
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class CustomerThread implements Runnable {
    private final String customerId;
    private final TicketPoolService ticketPoolService;
    private final int retrievalRate;
    private volatile boolean running;
    private int ticketsPurchased;
    
    public CustomerThread(String customerId, TicketPoolService ticketPoolService, int retrievalRate) {
        this.customerId = customerId;
        this.ticketPoolService = ticketPoolService;
        this.retrievalRate = retrievalRate;
        this.running = false;
        this.ticketsPurchased = 0;
    }
    
    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                if (ticketPoolService.purchaseTicket(customerId)) {
                    ticketsPurchased++;
                }
                Thread.sleep(1000 / retrievalRate);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    public void stop() {
        running = false;
    }
}