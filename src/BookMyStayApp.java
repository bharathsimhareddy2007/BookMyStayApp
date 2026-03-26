

import java.util.*;

// ---------------- RESERVATION ----------------
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// ---------------- QUEUE ----------------
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}

// ---------------- INVENTORY ----------------
class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 2); // limited to show allocation
        roomAvailability.put("Double", 1);
        roomAvailability.put("Suite", 1);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

// ---------------- ALLOCATION SERVICE ----------------
class RoomAllocationService {

    // Prevent duplicate room IDs
    private Set<String> allocatedRoomIds;

    // Track assigned rooms per type
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String roomType = reservation.getRoomType();
        Map<String, Integer> availability = inventory.getRoomAvailability();

        // Check availability
        if (availability.getOrDefault(roomType, 0) <= 0) {
            System.out.println("No rooms available for " + roomType +
                    " for Guest: " + reservation.getGuestName());
            return;
        }

        // Generate unique room ID
        String roomId = generateRoomId(roomType);

        // Store assigned room
        assignedRoomsByType.putIfAbsent(roomType, new HashSet<>());
        assignedRoomsByType.get(roomType).add(roomId);
        allocatedRoomIds.add(roomId);

        // Update inventory
        inventory.updateAvailability(roomType, availability.get(roomType) - 1);

        // Confirmation message
        System.out.println("Booking confirmed for Guest: "
                + reservation.getGuestName() + ", Room ID: " + roomId);
    }

    private String generateRoomId(String roomType) {

        int count = assignedRoomsByType.getOrDefault(roomType, new HashSet<>()).size() + 1;
        String roomId = roomType + "-" + count;

        // Ensure uniqueness (extra safety)
        while (allocatedRoomIds.contains(roomId)) {
            count++;
            roomId = roomType + "-" + count;
        }

        return roomId;
    }
}

// ---------------- MAIN CLASS ----------------
public class  BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing\n");

        // Queue
        BookingRequestQueue queue = new BookingRequestQueue();

        // Add requests
        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Single"));
        queue.addRequest(new Reservation("Vanmathi", "Suite"));

        // Inventory
        RoomInventory inventory = new RoomInventory();

        // Allocation service
        RoomAllocationService service = new RoomAllocationService();

        // Process FIFO
        while (queue.hasPendingRequests()) {
            Reservation r = queue.getNextRequest();
            service.allocateRoom(r, inventory);
        }
    }
}