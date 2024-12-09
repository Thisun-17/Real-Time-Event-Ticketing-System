import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Configuration {
    // Instance variables for configuration parameters
    private int maxTicketCapacity;
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private final Logger logger;

    // Constants for file paths
    private static final String LOG_FILE = "system_log.txt";

    // Constants for validation
    private static final int MIN_CAPACITY = 1;
    private static final int MIN_TICKETS = 1;
    private static final int MIN_RATE = 100; // 100ms minimum rate

    // Constructor
    public Configuration() {
        this.logger = Logger.getLogger("Configuration");
        logger.info("Starting new configuration setup");
        setupConfiguration();
    }

    // Set up configuration with input validation
    private void setupConfiguration() {
        Scanner scanner = new Scanner(System.in);
        boolean validConfig = false;

        while (!validConfig) {
            try {
                getValidInputs(scanner);
                validConfig = validateConfiguration();
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid numbers only.");
                logger.warning("Invalid number format entered");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                logger.warning(e.getMessage());
            }
        }

        logger.info("Configuration completed successfully");
    }

    // Get valid inputs from user
    private void getValidInputs(Scanner scanner) {
        System.out.println("\nPlease enter configuration values:");

        totalTickets = getValidIntInput(scanner, "Enter total number of tickets (min " + MIN_TICKETS + "): ",
                "Total tickets must be at least " + MIN_TICKETS, MIN_TICKETS);

        maxTicketCapacity = getValidIntInput(scanner, "Enter maximum ticket pool capacity (min " + MIN_CAPACITY + "): ",
                "Capacity must be at least " + MIN_CAPACITY, MIN_CAPACITY);

        ticketReleaseRate = getValidIntInput(scanner, "Enter ticket release rate in milliseconds (min " + MIN_RATE + "ms): ",
                "Release rate must be at least " + MIN_RATE + "ms", MIN_RATE);

        customerRetrievalRate = getValidIntInput(scanner, "Enter customer retrieval rate in milliseconds (min " + MIN_RATE + "ms): ",
                "Retrieval rate must be at least " + MIN_RATE + "ms", MIN_RATE);
    }

    // Get valid integer input from user
    private int getValidIntInput(Scanner scanner, String prompt, String errorMessage, int minValue) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                int value = Integer.parseInt(input);
                if (value >= minValue) {
                    return value;
                }
                System.out.println(errorMessage);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }

    // Validate configuration
    private boolean validateConfiguration() {
        if (maxTicketCapacity > totalTickets) {
            throw new IllegalArgumentException("Maximum capacity cannot be greater than total tickets");
        }

        logger.info("Configuration validated successfully");
        return true;
    }

    // Initialize report file and return filename
    public String initializeReport() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename = "report_" + timestamp + ".txt";
        logger.info("Initialized new report file: " + filename);
        return filename;
    }

    // Log messages to file with timestamp
    private void logToFile(String message) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            out.println(timestamp + " - " + message);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    // Getters
    public int getMaxTicketCapacity() { return maxTicketCapacity; }
    public int getTotalTickets() { return totalTickets; }
    public int getTicketReleaseRate() { return ticketReleaseRate; }
    public int getCustomerRetrievalRate() { return customerRetrievalRate; }

    @Override
    public String toString() {
        return "Configuration Settings:\n" +
                "- Total Tickets: " + totalTickets + "\n" +
                "- Max Ticket Capacity: " + maxTicketCapacity + "\n" +
                "- Ticket Release Rate: " + ticketReleaseRate + "ms\n" +
                "- Customer Retrieval Rate: " + customerRetrievalRate + "ms";
    }
}