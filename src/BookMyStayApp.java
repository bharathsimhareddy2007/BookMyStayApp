/**
 * ============================================================
 * USE CASE 4 - Room Search & Availability Check
 * Single File Version
 * @version 4.1
 * ============================================================
 */

import java.util.HashMap;
import java.util.Map;

// ---------------- ABSTRACT CLASS ----------------
abstract class Room {

    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
    }
}

// ---------------- ROOM TYPES ----------------
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}

// ---------------- INVENTORY ----------------
class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }
}

// ---------------- SEARCH SERVICE ----------------
class RoomSearchService {

    public void displayAvailableRooms(RoomInventory inventory) {

        Map<String, Integer> availability = inventory.getRoomAvailability();

        System.out.println("Available Rooms:\n");

        // Single Room
        if (availability.get("Single") > 0) {
            System.out.println("Single Room:");
            Room room = new SingleRoom();
            room.displayRoomDetails();
            System.out.println("Available Rooms: " + availability.get("Single") + "\n");
        }

        // Double Room
        if (availability.get("Double") > 0) {
            System.out.println("Double Room:");
            Room room = new DoubleRoom();
            room.displayRoomDetails();
            System.out.println("Available Rooms: " + availability.get("Double") + "\n");
        }

        // Suite Room
        if (availability.get("Suite") > 0) {
            System.out.println("Suite Room:");
            Room room = new SuiteRoom();
            room.displayRoomDetails();
            System.out.println("Available Rooms: " + availability.get("Suite"));
        }
    }
}

// ---------------- MAIN CLASS ----------------
public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Search service (read-only)
        RoomSearchService searchService = new RoomSearchService();

        System.out.println("Hotel Room Search Results\n");

        // Display available rooms (NO modification)
        searchService.displayAvailableRooms(inventory);
    }
}