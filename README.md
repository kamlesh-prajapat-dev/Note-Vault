
# Note-Vault

An Android application that enables  users  to create, edit, delete, and organize personal notes securely.


## 🌟 Features

***The application provides a complete and secure personal note-taking experience:***
 
 - CRUD Operations: Seamlessly Create, Read, Update, and Delete notes.
 - Organization: Tools to efficiently organize and manage personal notes.
 - Secure Authentication: User signup and login managed via JWT authentication.
 - Real-time Synchronization: Notes are efficiently synchronized with the backend, ensuring data is always up-to-date across devices.
 - Offline Support: Robust offline caching allows users to view and modify notes even without an internet connection.
 - Secure Data Handling: All data storage and retrieval are private and secure using authenticated REST APIs.
 - Secure Logout: Implemented a secure mechanism for user session termination.

## 📐 Architecture

***This project is built as a complete, secure ecosystem composed of two main components :-***
1. **Mobile Client**: A modern Android application focused on a smooth user experience.
2. **Backend Server**: A scalable, secure API server managing user authentication and persistent note data.

### 📱 Mobile Client
 - *Frontend: -* Built with Kotlin and Jetpack Compose for a modern, declarative UI.
 - *Networking: -* Utilizes Retrofit for efficient, type-safe communication with the RESTful backend.

### 🌐 Backend Server
 - *Framework: -* Built on Spring Boot for a high-performance, maintainable REST API.
 - Database: -* Leverages MongoDB for flexible and scalable document storage of note data.
## 💻 Technology Stack

### 🚀 Getting Started
***To get a local copy of both the client and server running, follow these steps.***

**Prerequisites**
- For Android: Android Studio, Android SDK
 - For Backend: Java JDK 17+ (or equivalent), Docker (Recommended for MongoDB setup)

**Installation**

1. Backend Setup
 - Clone the repository:
 - Set up MongoDB: (Using Docker is highly recommended)
 - Configure and Run: Update the MongoDB connection string in src/main/resources/application.properties (if needed) and start the Spring Boot application.
 - The API should be running on http://localhost:8080.

2. Android Client Setup
 - Open in Android Studio: Open the client/ directory in Android Studio.
 - Configure API URL: Update the base URL for the backend API in the appropriate   configuration file (e.g., a Constants.kt file) to point to your running server (e.g., http://10.0.2.2:8080 for the Android emulator).
 - Run: Build and run the application on an emulator or physical device.
## 🔒 Security
***Security was a primary design consideration for this project:***

 - RESTful Authentication: All user interactions are secured using JWT (JSON Web Token) passed over every authenticated REST API request.
 - Private Data: The system ensures that users can only access their own note data, enforced by the server-side authentication and authorization logic.
