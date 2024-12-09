public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int retrievalRate;
    private final String customerId;
    private volatile boolean running = true;
    private int ticketsPurchased = 0;

    public Customer(String customerId, TicketPool ticketPool, int retrievalRate) {
        this.customerId = customerId;
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
    }

    @Override
    public void run() {
        while (running) {
            if (ticketPool.addTicket()) {
                ticketsPurchased++;
            }
            try {
                Thread.sleep(retrievalRate);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void stop() {
        running = false;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getTicketsPurchased() {
        return ticketsPurchased;
    }
}