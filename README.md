# pepsales

# Notifications Application

A Spring Boot application for sending and retrieving user notifications.

---

## Setup Instructions

### Prerequisites

* **Java 17 or later**: Ensure `java -version` works.
* **Maven**: Ensure `mvn -v` works.
* **IntelliJ IDEA** (recommended).

---

### Steps to Run

1. **Navigate to the Project Directory**:

   ```bash
   cd "C:\Users\KIIT\Downloads\demo (1)\demo"
   ```

2. **Run the Application**:

   ```bash
   mvn spring-boot:run
   ```

3. **APIs**:

   * **Send Notification**:
     POST `http://localhost:8080/notifications`
     Body:

     ```json
     {
       "userId": "user123",
       "type": "Email",
       "message": "Hello, World!"
     }
     ```
   * **Get Notifications**:
     GET `http://localhost:8080/notifications/users/user123/notifications`.

---

### Assumptions

1. Notifications are stored in memory and will reset on restart.
2. No authentication or real notification services integrated.

