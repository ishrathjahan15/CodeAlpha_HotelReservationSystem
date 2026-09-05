# CodeAlpha — Hotel Reservation System

## Description

The Hotel Reservation System is a console-based Java application for managing hotel room reservations. Users can search for available rooms, book rooms, cancel reservations, view booking details, and check availability across all rooms.

This project demonstrates Java OOP concepts, ArrayList, file handling, exception handling, and user input with Scanner.

## Features

- Search available rooms by category
- Supports three room categories:
  - Standard
  - Deluxe
  - Suite
- View room number, category, price, and availability
- Book available rooms
- Store customer name and phone number
- Basic payment confirmation
- Generate a unique booking ID
- Cancel existing reservations
- Automatically make cancelled rooms available again
- View all booking details
- Save booking data to bookings.txt
- Automatically load previous bookings when the application starts
- Menu-driven console interface

## Room Categories

| Category | Price |
|---|---:|
| Standard | ₹1,500 |
| Deluxe | ₹2,500 |
| Suite | ₹4,000 |

## Available Rooms

| Category | Room Numbers |
|---|---|
| Standard | 101, 102, 103 |
| Deluxe | 201, 202, 203 |
| Suite | 301, 302 |

## Technologies Used

- Java
- Object-Oriented Programming
- ArrayList
- File Handling
- Exception Handling
- Scanner
- FileWriter
- FileReader
- BufferedReader

## Project Classes

### Room

Represents a hotel room and stores:

- Room number
- Category
- Price
- Availability status

### Booking

Stores reservation details:

- Booking ID
- Customer name
- Phone number
- Room number
- Room category
- Amount paid

### Payment

Handles basic payment confirmation before completing a reservation.

### HotelReservation

The main application class. It manages rooms, bookings, room searches, reservations, cancellations, file operations, and the main menu.

## How to Run

Compile the program:

```bash
javac HotelReservation.java
```

Run the program:

```bash
java HotelReservation
```

## Console Menu

```text
================================
      HOTEL RESERVATION SYSTEM
================================
1. Search Available Rooms
2. Book a Room
3. Cancel Reservation
4. View Booking Details
5. View All Rooms
6. Exit
================================
```

Enter the required option number to perform an operation.

## File Storage

The application stores reservations in a file named bookings.txt.

Example entry:

```text
1001,John,9876543210,101,Standard,1500.0
```

This represents:

1. Booking ID  
2. Customer Name  
3. Phone Number  
4. Room Number  
5. Room Category  
6. Amount Paid  

When the application starts, it loads existing bookings and automatically marks the corresponding rooms as booked.

## OOP Concepts Used

- **Encapsulation:** Class variables are private and accessed through getter and setter methods.
- **Classes and Objects:** Separate classes represent rooms, bookings, payments, and the reservation system.
- **Constructors:** Initialize Room and Booking objects.
- **Methods:** Each operation is organized into dedicated methods.
- **ArrayList:** Stores rooms and bookings dynamically.
- **Exception Handling:** Handles file- and data-related errors.
- **File Handling:** Permanently saves and retrieves booking data.

## Conclusion
=======
The Hotel Reservation System is a simple Java project that provides the core functionality needed to manage hotel reservations. It is useful for learning and demonstrating Java OOP, collections, file handling, exception handling, and console-based application development.
