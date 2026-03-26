

import java.util.*;

// ---------------- SERVICE CLASS ----------------
class Service {

    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

// ---------------- SERVICE MANAGER ----------------
class AddOnServiceManager {

    // Map: Reservation ID -> List of Services
    private Map<String, List<Service>> servicesByReservation;

    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, Service service) {

        servicesByReservation.putIfAbsent(reservationId, new ArrayList<>());
        servicesByReservation.get(reservationId).add(service);
    }

    // Calculate total service cost
    public double calculateTotalServiceCost(String reservationId) {

        List<Service> services = servicesByReservation.get(reservationId);

        if (services == null) return 0;

        double total = 0;

        for (Service s : services) {
            total += s.getCost();
        }

        return total;
    }
}

// ---------------- MAIN CLASS ----------------
public class  BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Add-On Service Selection");

        // Example reservation ID (from previous use case)
        String reservationId = "Single-1";

        // Create manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Create services
        Service s1 = new Service("Breakfast", 500);
        Service s2 = new Service("Spa", 1000);

        // Add services
        manager.addService(reservationId, s1);
        manager.addService(reservationId, s2);

        // Display result
        System.out.println("Reservation ID: " + reservationId);

        double totalCost = manager.calculateTotalServiceCost(reservationId);

        System.out.println("Total Add-On Cost: " + totalCost);
    }
}