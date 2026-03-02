import com.example.tickets.IncidentTicket;
import com.example.tickets.TicketService;

import java.util.List;

/**
 * Demonstrates why immutability is safer.
 *
 * - No setters, so direct mutation is impossible
 * - External modifications to tags cannot affect the ticket
 * - Service "updates" return NEW ticket instances
 */
public class TryIt {

    public static void main(String[] args) {
        TicketService service = new TicketService();

        // Create ticket
        IncidentTicket t1 = service.createTicket("TCK-1001", "reporter@example.com", "Payment failing on checkout");
        System.out.println("Created: " + t1);

        // Attempt to "update" ticket → returns new instance
        IncidentTicket t2 = service.assign(t1, "agent@example.com");
        IncidentTicket t3 = service.escalateToCritical(t2);

        System.out.println("\nAfter assigning and escalating (immutable updates):");
        System.out.println("t2 (assigned): " + t2);
        System.out.println("t3 (escalated): " + t3);

        // Original ticket remains unchanged
        System.out.println("\nOriginal ticket remains unchanged:");
        System.out.println("t1: " + t1);

        // Demonstrate external modification attempt
        List<String> tags = t3.getTags();
        System.out.println("\nAttempting external modification of tags:");
        try {
            tags.add("HACKED_FROM_OUTSIDE"); // throws exception
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot modify tags externally! ✅");
        }

        // Print final ticket to show tags are intact
        System.out.println("\nFinal ticket after attempted external tag modification:");
        System.out.println(t3);
    }
}