import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.*;

public class Main {
    private static List<Thread> threads;
    private static List<Vendor> vendors;
    private static List<Customer> customers;
    private static TicketPool ticketPool;
    private static volatile boolean isRunning = false;
    private static final Scanner scanner = new Scanner(System.in);
    private static Configuration config;
    private static final Logger logger = Logger.getLogger("Main");

    public static void main(String[] args) {
        setupLogging();
        System.out.println("Welcome to Ticket System Application");

        try {
            config = new Configuration();
            System.out.println(config.toString());

            while (true) {
                showMenu();
                String command = scanner.nextLine().trim().toLowerCase();

                switch (command) {
                    case "1":
                    case "start":
                        if (!isRunning) {
                            setupAndStartSystem();
                        } else {
                            System.out.println("System is already running!");
                        }
                        break;

                    case "2":
                    case "stop":
                        if (isRunning) {
                            stopSystem();
                        } else {
                            System.out.println("System is not running!");
                        }
                        break;

                    case "3":
                    case "status":
                        if (isRunning) {
                            showStatus();
                        } else {
                            System.out.println("System is not running!");
                        }
                        break;

                    case "4":
                    case "reconfigure":
                        if (!isRunning) {
                            config = new Configuration();
                            System.out.println(config.toString());
                        } else {
                            System.out.println("Cannot reconfigure while system is running!");
                        }
                        break;

                    case "5":
                    case "exit":
                        if (isRunning) {
                            stopSystem();
                        }
                        System.out.println("Thank you for using Ticket System Application");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid command! Please try again.");
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Fatal error occurred", e);
            System.err.println("Fatal error occurred: " + e.getMessage());
        }
    }

    private static void showMenu() {
        System.out.println("\nAvailable Commands:");
        System.out.println("1. Start - Start the ticket system");
        System.out.println("2. Stop - Stop the ticket system");
        System.out.println("3. Status - Show current system status");
        System.out.println("4. Reconfigure - Reconfigure system parameters");
        System.out.println("5. Exit - Exit the application");
        System.out.print("Enter command: ");
    }

    private static void setupAndStartSystem() {
        setupSystem();
        startSystem();
        System.out.println("System started successfully!");
    }

    private static void setupSystem() {
        ticketPool = new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets());
        threads = new ArrayList<>();
        vendors = new ArrayList<>();
        customers = new ArrayList<>();

        // Initialize vendors and customers
        int numVendors = Math.max(2, config.getMaxTicketCapacity() / 50);
        int numCustomers = Math.max(3, config.getMaxTicketCapacity() / 30);

        for (int i = 1; i <= numVendors; i++) {
            vendors.add(new Vendor("V" + i, ticketPool, config.getTicketReleaseRate()));
        }

        for (int i = 1; i <= numCustomers; i++) {
            customers.add(new Customer("C" + i, ticketPool, config.getCustomerRetrievalRate()));
        }

        logger.info("System setup completed with " + numVendors + " vendors and " + numCustomers + " customers");
    }

    private static void startSystem() {
        isRunning = true;

        // Start vendor threads
        for (Vendor vendor : vendors) {
            Thread thread = new Thread(vendor);
            threads.add(thread);
            thread.start();
        }

        // Start customer threads
        for (Customer customer : customers) {
            Thread thread = new Thread(customer);
            threads.add(thread);
            thread.start();
        }

        // Start monitoring thread
        startMonitoring();
    }

    private static void stopSystem() {
        isRunning = false;

        // Interrupt all threads
        for (Thread thread : threads) {
            thread.interrupt();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join(1000);
            } catch (InterruptedException e) {
                logger.warning("Interrupted while waiting for thread to finish");
            }
        }

        threads.clear();
        vendors.clear();
        customers.clear();
        System.out.println("System stopped successfully!");
        logger.info("System stopped");
    }

    private static void startMonitoring() {
        Thread monitorThread = new Thread(() -> {
            while (isRunning) {
                try {
                    Thread.sleep(5000);
                    if (isRunning) {
                        showStatus();
                    }

                    if (ticketPool.isAllTicketsSold()) {
                        System.out.println("\nAll tickets have been sold! System complete.");
                        stopSystem();
                        break;
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        threads.add(monitorThread);
        monitorThread.start();
    }

    private static void showStatus() {
        System.out.println("\n=== System Status ===");
        ticketPool.printStatistics();
        System.out.println("\nVendor Statistics:");
        vendors.forEach(vendor ->
                System.out.println(vendor.getVendorId() + " - Produced: " + vendor.getTicketsProduced()));
        System.out.println("\nCustomer Statistics:");
        customers.forEach(customer ->
                System.out.println(customer.getCustomerId() + " - Purchased: " + customer.getTicketsPurchased()));
    }

    private static void setupLogging() {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter() {
            @Override
            public String format(LogRecord record) {
                return String.format("[%s] %s%n",
                        record.getLoggerName(),
                        record.getMessage()
                );
            }
        });

        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.INFO);

        Handler[] handlers = rootLogger.getHandlers();
        for(Handler h : handlers) {
            rootLogger.removeHandler(h);
        }

        rootLogger.addHandler(handler);
    }
}