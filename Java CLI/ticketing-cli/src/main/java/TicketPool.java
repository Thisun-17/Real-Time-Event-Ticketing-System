import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;
import java.util.logging.Level;

public class TicketPool {
    private final Queue<Integer> tickets = new LinkedList<>();
    private final int maxCapacity;
    private final int totalTickets;
    private int ticketsProduced = 0;
    private int ticketsSold = 0;
    private final Object lock = new Object();
    private static final String LOG_FILE = "system_log.txt";
    private final Logger logger;

    public TicketPool(int maxCapacity, int totalTickets) {
        this.maxCapacity = maxCapacity;
        this.totalTickets = totalTickets;
        this.logger = Logger.getLogger("TicketPool");
        logger.info("TicketPool initialized - Capacity: " + maxCapacity + ", Total Tickets: " + totalTickets);
        logMessage("TicketPool initialized - Capacity: " + maxCapacity + ", Total Tickets: " + totalTickets);
    }

    public boolean addTicket() {
        synchronized(lock) {
            if (ticketsProduced >= totalTickets || tickets.size() >= maxCapacity) {
                if (ticketsProduced >= totalTickets) {
                    logger.info("Ticket production limit reached: " + totalTickets + " tickets");
                    logMessage("Ticket production limit reached: " + totalTickets + " tickets");
                }
                return false;
            }
            tickets.offer(++ticketsProduced);
            logger.info("Ticket #" + ticketsProduced + " added to pool. Pool size: " + tickets.size());
            logMessage("Ticket #" + ticketsProduced + " added to pool. Pool size: " + tickets.size());
            lock.notifyAll();
            return true;
        }
    }

    public Integer removeTicket() {
        synchronized(lock) {
            while (tickets.isEmpty() && ticketsSold < totalTickets) {
                try {
                    logger.info("Customer waiting for tickets. Pool empty.");
                    logMessage("Customer waiting for tickets. Pool empty.");
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.warning("Customer interrupted while waiting for tickets");
                    logMessage("Customer interrupted while waiting for tickets");
                    return null;
                }
            }
            if (!tickets.isEmpty()) {
                Integer ticket = tickets.poll();
                ticketsSold++;
                logger.info("Ticket #" + ticket + " sold. Remaining tickets: " + tickets.size());
                logMessage("Ticket #" + ticket + " sold. Remaining tickets: " + tickets.size());
                return ticket;
            }
            return null;
        }
    }

    public boolean isAllTicketsSold() {
        return ticketsSold >= totalTickets;
    }

    public void printStatistics() {
        String stats = String.format("Current Statistics:%n" +
                        "- Available: %d%n" +
                        "- Produced: %d%n" +
                        "- Sold: %d",
                tickets.size(), ticketsProduced, ticketsSold);
        System.out.println(stats);
        logger.info(stats);
        logMessage(stats);
    }

    private void logMessage(String message) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            out.println(timestamp + " - [TicketPool] " + message);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}