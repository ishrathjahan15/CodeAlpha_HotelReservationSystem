import java.io.*;
import java.util.*;

// ---------------- ROOM CLASS ----------------
class Room {
    private final int roomNumber;
    private final String category;
    private final double price;
    private boolean available;

    public Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.available = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void displayRoom() {
        System.out.println(
                "Room: " + roomNumber +
                " | Category: " + category +
                " | Price: ₹" + price +
                " | " + (available ? "Available" : "Booked")
        );
    }
}


// ---------------- BOOKING CLASS ----------------
class Booking {
    private final int bookingId;
    private final String customerName;
    private final String phoneNumber;
    private final int roomNumber;
    private final String category;
    private final double amount;

    public Booking(int bookingId, String customerName,
                   String phoneNumber, int roomNumber,
                   String category, double amount) {

        this.bookingId = bookingId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.roomNumber = roomNumber;
        this.category = category;
        this.amount = amount;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void displayBooking() {
        System.out.println("\n---------- BOOKING DETAILS ----------");
        System.out.println("Booking ID : " + bookingId);
        System.out.println("Customer : " + customerName);
        System.out.println("Phone Number : " + phoneNumber);
        System.out.println("Room Number : " + roomNumber);
        System.out.println("Category : " + category);
        System.out.println("Amount Paid : ₹" + amount);
        System.out.println("-------------------------------------");
    }

    public String toFileString() {
        return bookingId + "," +
                customerName + "," +
                phoneNumber + "," +
                roomNumber + "," +
                category + "," +
                amount;
    }
}


// ---------------- PAYMENT CLASS ----------------
class Payment {

    public static boolean makePayment(double amount) {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n---------- PAYMENT ----------");
        System.out.println("Amount to pay: ₹" + amount);
        System.out.println("1. Pay Now");
        System.out.println("2. Cancel Payment");
        System.out.print("Enter choice: ");

        int choice = sc.nextInt();

        if (choice == 1) {
            System.out.println("Processing payment...");
            System.out.println("Payment successful!");
            return true;
        }

        System.out.println("Payment cancelled.");
        return false;
    }
}


// ---------------- HOTEL SYSTEM CLASS ----------------
public class HotelReservation {

    private static final String FILE_NAME = "bookings.txt";

    private static final ArrayList<Room> rooms = new ArrayList<>();
    private static final ArrayList<Booking> bookings = new ArrayList<>();

    private static final Scanner sc = new Scanner(System.in);

    private static int nextBookingId = 1001;


    // Initialize rooms
    public static void initializeRooms() {

        // Standard Rooms
        rooms.add(new Room(101, "Standard", 1500));
        rooms.add(new Room(102, "Standard", 1500));
        rooms.add(new Room(103, "Standard", 1500));

        // Deluxe Rooms
        rooms.add(new Room(201, "Deluxe", 2500));
        rooms.add(new Room(202, "Deluxe", 2500));
        rooms.add(new Room(203, "Deluxe", 2500));

        // Suite Rooms
        rooms.add(new Room(301, "Suite", 4000));
        rooms.add(new Room(302, "Suite", 4000));
    }


    // Search rooms
    public static void searchRooms() {

        System.out.println("\n---------- SEARCH ROOMS ----------");

        System.out.println("1. Standard");
        System.out.println("2. Deluxe");
        System.out.println("3. Suite");
        System.out.print("Enter category: ");

        int choice = sc.nextInt();

        String category;

        switch (choice) {
            case 1:
                category = "Standard";
                break;

            case 2:
                category = "Deluxe";
                break;

            case 3:
                category = "Suite";
                break;

            default:
                System.out.println("Invalid category.");
                return;
        }

        boolean found = false;

        for (Room room : rooms) {

            if (room.getCategory().equalsIgnoreCase(category)
                    && room.isAvailable()) {

                room.displayRoom();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No available rooms in this category.");
        }
    }


    // Book room
    public static void bookRoom() {

        System.out.println("\n---------- BOOK ROOM ----------");

        System.out.print("Enter room number: ");
        int roomNumber = sc.nextInt();
        sc.nextLine();

        Room selectedRoom = null;

        for (Room room : rooms) {

            if (room.getRoomNumber() == roomNumber) {

                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Room does not exist.");
            return;
        }

        if (!selectedRoom.isAvailable()) {
            System.out.println("Room is already booked.");
            return;
        }

        System.out.print("Enter customer name: ");
        String name = sc.nextLine();

        System.out.print("Enter phone number: ");
        String phone = sc.nextLine();

        double amount = selectedRoom.getPrice();

        System.out.println("\nRoom Category : " + selectedRoom.getCategory());
        System.out.println("Room Price : ₹" + amount);

        // Payment
        boolean paymentSuccessful = Payment.makePayment(amount);

        if (!paymentSuccessful) {
            System.out.println("Booking cancelled.");
            return;
        }

        Booking booking = new Booking(
                nextBookingId,
                name,
                phone,
                roomNumber,
                selectedRoom.getCategory(),
                amount
        );

        bookings.add(booking);

        selectedRoom.setAvailable(false);

        saveBookingToFile(booking);

        System.out.println("\nBooking successful!");
        System.out.println("Your Booking ID: " + nextBookingId);

        nextBookingId++;
    }


    // Cancel booking
    public static void cancelBooking() {

        System.out.println("\n---------- CANCEL BOOKING ----------");

        System.out.print("Enter booking ID: ");
        int bookingId = sc.nextInt();

        Booking bookingToCancel = null;

        for (Booking booking : bookings) {

            if (booking.getBookingId() == bookingId) {

                bookingToCancel = booking;
                break;
            }
        }

        if (bookingToCancel == null) {
            System.out.println("Booking not found.");
            return;
        }

        // Make room available again
        for (Room room : rooms) {

            if (room.getRoomNumber()
                    == bookingToCancel.getRoomNumber()) {

                room.setAvailable(true);
                break;
            }
        }

        bookings.remove(bookingToCancel);

        updateBookingFile();

        System.out.println("Booking cancelled successfully.");
    }


    // View all bookings
    public static void viewBookings() {

        System.out.println("\n---------- ALL BOOKINGS ----------");

        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Booking booking : bookings) {
            booking.displayBooking();
        }
    }


    // Save booking to file
    static void saveBookingToFile(Booking booking) {

        try (FileWriter writer =
                     new FileWriter(FILE_NAME, true)) {

            writer.write(booking.toFileString());
            writer.write("\n");

        } catch (IOException e) {

            System.out.println("Error saving booking.");
        }
    }


    // Update complete booking file
    public static void updateBookingFile() {

        try (FileWriter writer =
                     new FileWriter(FILE_NAME)) {

            for (Booking booking : bookings) {

                writer.write(booking.toFileString());
                writer.write("\n");
            }

        } catch (IOException e) {

            System.out.println("Error updating booking file.");
        }
    }


    // Load bookings from file
    public static void loadBookingsFromFile() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length != 6) {
                    continue;
                }

                int bookingId = Integer.parseInt(data[0]);
                String name = data[1];
                String phone = data[2];
                int roomNumber = Integer.parseInt(data[3]);
                String category = data[4];
                double amount = Double.parseDouble(data[5]);

                Booking booking = new Booking(
                        bookingId,
                        name,
                        phone,
                        roomNumber,
                        category,
                        amount
                );

                bookings.add(booking);

                // Mark room as booked
                for (Room room : rooms) {

                    if (room.getRoomNumber() == roomNumber) {
                        room.setAvailable(false);
                        break;
                    }
                }

                if (bookingId >= nextBookingId) {
                    nextBookingId = bookingId + 1;
                }
            }

        } catch (IOException | NumberFormatException e) {

            System.out.println("Error loading bookings.");
        }
    }


    // Main menu
    public static void main(String[] args) {

        initializeRooms();

        loadBookingsFromFile();

        int choice;

        do {

            System.out.println("\n================================");
            System.out.println(" HOTEL RESERVATION SYSTEM");
            System.out.println("================================");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Booking Details");
            System.out.println("5. View All Rooms");
            System.out.println("6. Exit");
            System.out.println("================================");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    searchRooms();
                    break;

                case 2:
                    bookRoom();
                    break;

                case 3:
                    cancelBooking();
                    break;

                case 4:
                    viewBookings();
                    break;

                case 5:

                    System.out.println("\n---------- ALL ROOMS ----------");

                    for (Room room : rooms) {
                        room.displayRoom();
                    }

                    break;

                case 6:
                    System.out.println("Thank you for using the Hotel Reservation System!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 6);

        //sc.close();
    }
}