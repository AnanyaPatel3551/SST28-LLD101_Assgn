package com.example.tickets;

import java.util.List;

/**
 * Service layer that creates tickets.
 *
 * REFACTORED TO:
 * - create immutable tickets
 * - no mutation after creation
 * - updates return new ticket instances
 */
public class TicketService {

    public IncidentTicket createTicket(String id, String reporterEmail, String title) {
        // Create immutable ticket using Builder
        IncidentTicket ticket = new IncidentTicket.Builder(id, reporterEmail, title)
                .priority("MEDIUM")
                .source("CLI")
                .customerVisible(false)
                .tags(List.of("NEW"))
                .build();
        return ticket;
    }

    public IncidentTicket escalateToCritical(IncidentTicket ticket) {
        // Return new ticket instance instead of mutating
        return IncidentTicket.Builder.from(ticket)
                .priority("CRITICAL")
                .tags(List.of("ESCALATED")) // new list for immutability
                .build();
    }

    public IncidentTicket assign(IncidentTicket ticket, String assigneeEmail) {
        return IncidentTicket.Builder.from(ticket)
                .assigneeEmail(assigneeEmail)
                .build();
    }
}