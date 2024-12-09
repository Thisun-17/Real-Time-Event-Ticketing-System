// Path: src/main/java/org/ticketing/thread/VendorThread.java
package org.ticketing.thread;

import org.ticketing.service.TicketPoolService;  // Updated import - matches your package structure
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class VendorThread implements Runnable {
    private final String vendorId;
    private final TicketPoolService ticketPoolService;
    private final int releaseRate;
    private volatile boolean running;
    private int ticketsProduced;
    
    public VendorThread(String vendorId, TicketPoolService ticketPoolService, int releaseRate) {
        this.vendorId = vendorId;
        this.ticketPoolService = ticketPoolService;
        this.releaseRate = releaseRate;
        this.running = false;
        this.ticketsProduced = 0;
    }
    
    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                if (ticketPoolService.addTicket(vendorId)) {
                    ticketsProduced++;
                }
                Thread.sleep(1000 / releaseRate);
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