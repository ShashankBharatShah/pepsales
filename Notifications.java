import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.*;
import java.util.concurrent.*;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class Notifications { // Main Spring Boot application class
    public static void main(String[] args) {
        SpringApplication.run(Notifications.class, args);
    }
}

@RestController
@RequestMapping("/notifications")
class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        notificationService.sendNotification(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Notification is being processed");
    }

    @GetMapping("/users/{id}/notifications")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable("id") String userId) {
        List<Notification> notifications = notificationService.getUserNotifications(userId);
        return ResponseEntity.ok(notifications);
    }
}

class NotificationRequest {
    private String userId;
    private String type; // Email, SMS, In-App
    private String message;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}

class Notification {
    private String userId;
    private String type;
    private String message;
    private String status; // Sent, Failed, Pending

    public Notification(String userId, String type, String message, String status) {
        this.userId = userId;
        this.type = type;
        this.message = message;
        this.status = status;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

@Service
class NotificationService {

    private final Map<String, List<Notification>> userNotifications = new ConcurrentHashMap<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void sendNotification(NotificationRequest request) {
        Notification notification = new Notification(request.getUserId(), request.getType(), request.getMessage(), "Pending");

        executorService.submit(() -> {
            try {
                Thread.sleep(1000); // simulate delay
                notification.setStatus("Sent");
            } catch (InterruptedException e) {
                notification.setStatus("Failed");
            }
            userNotifications.computeIfAbsent(request.getUserId(), k -> new ArrayList<>()).add(notification);
        });
    }

    public List<Notification> getUserNotifications(String userId) {
        return userNotifications.getOrDefault(userId, Collections.emptyList());
    }
}
