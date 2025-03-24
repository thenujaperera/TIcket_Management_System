# Ticket Booking System

This application leverages Spring Boot (backend) and React (frontend) to create a system for simultaneous ticket buying and selling, simulating real-world scenarios. Users can configure the system to load previously saved data, save new data, start the simulation, and stop it while viewing logs of concurrent operations. Additionally, a real-time ticket management dashboard has been implemented to display ticket availability, tickets released, and tickets sold in real time.

## Features

- **Simultaneous Ticket Operations**: Vendors can release tickets while customers purchase them concurrently, demonstrating the system's multithreading capabilities.
- **Real-Time Dashboard**: A live dashboard updates ticket availability, tickets released, and tickets sold, providing real-time insights into system operations.
- **Save and Load Configuration**:
  - **Save Button**: Allows users to save their inputs to a JSON file.
  - **Load Button**: Loads pre-saved configurations from a JSON file.
- **Control Panel**:
  - **Start Button**: Initiates the vendor and customer threads.
  - **Stop Button**: Halts the threads and stops the system until reconfigured or restarted.
- **Responsive User Interface**: The application provides a user-friendly, responsive interface built with React, ensuring ease of control and interaction.

## Technologies Used

### Backend
- **Language**: Java
- **Framework**: Spring Boot

### Frontend
- **Technologies**: React, JavaScript, CSS

## Prerequisites

### Backend
- Java 23

### Frontend
- Node.js version 20 or higher

## Setup Instructions

### Backend Setup
1. Download the Spring Boot application.
2. Build and run the application.
3. Ensure the backend is running at [http://localhost:8080](http://localhost:8080).

### Frontend Setup
1. Navigate to the frontend directory.
2. Install dependencies using `npm install`.
3. Start the application using `npm start`.
4. Ensure the frontend is running at [http://localhost:3000](http://localhost:3000).

## Running the Application

1. Start the backend application.
2. Start the frontend application.
3. Open the application in your browser.
4. Use the control panel to configure and operate the system:
   - Save new configurations or load existing ones.
   - Start the system to initiate concurrent ticket buying and selling.
   - Stop the system as needed.
5. View logs in the log panel to monitor system activity and thread operations.

---

This application showcases how threads can operate concurrently in a simulation while providing a robust and user-friendly interface for managing ticket transactions.

