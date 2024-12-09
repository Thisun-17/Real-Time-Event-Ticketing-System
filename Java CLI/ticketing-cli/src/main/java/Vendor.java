import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int releaseRate;
    private final String vendorId;
    private volatile boolean running = true;
    private int ticketsProduced = 0;
    private static final String LOG_FILE = "system_log.txt";
    private final Logger logger;

    public Vendor(String vendorId, TicketPool ticketPool, int releaseRate) {
        this.vendorId = vendorId;
        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
        this.logger = Logger.getLogger("Vendor-" + vendorId);
        logger.info("Vendor initialized - ID: " + vendorId + ", Release Rate: " + releaseRate + "ms");
        logMessage("Vendor initialized - ID: " + vendorId + ", Release Rate: " + releaseRate + "ms");
    }

    @Override
    public void run() {
        logger.info("Started producing tickets");
        logMessage("Started producing tickets");
        while (running) {
            if (ticketPool.addTicket()) {
                ticketsProduced++;
                logger.info("Successfully produced ticket #" + ticketsProduced);
                logMessage("Successfully produced ticket #" + ticketsProduced);
            }
            try {
                Thread.sleep(releaseRate);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warning("Interrupted while waiting between productions");
                logMessage("Interrupted while waiting between productions");
                break;
            }
        }
        logger.info("Stopped producing tickets. Total produced: " + ticketsProduced);
        logMessage("Stopped producing tickets. Total produced: " + ticketsProduced);
    }

    public void stop() {
        running = false;
        logger.info("Received stop signal");
        logMessage("Received stop signal");
    }

    public String getVendorId() {
        return vendorId;
    }

    public int getTicketsProduced() {
        return ticketsProduced;
    }

    private void logMessage(String message) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            out.println(timestamp + " - [Vendor-" + vendorId + "] " + message);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}