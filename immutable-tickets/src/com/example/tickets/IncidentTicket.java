package com.example.tickets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * INTENTION: A ticket should be an immutable record-like object.
 *
 * REFACTORED TO:
 * - immutable fields
 * - Builder pattern
 * - defensive copy for tags
 * - centralized validation
 */
public final class IncidentTicket {

    private final String id;
    private final String reporterEmail;
    private final String title;
    private final String description;
    private final String priority; // LOW, MEDIUM, HIGH, CRITICAL
    private final List<String> tags; // immutable list
    private final String assigneeEmail;
    private final boolean customerVisible;
    private final Integer slaMinutes; // optional
    private final String source; // e.g. "CLI", "WEBHOOK", "EMAIL"

    // Private constructor, only Builder can create instances
    private IncidentTicket(Builder builder) {
        this.id = builder.id;
        this.reporterEmail = builder.reporterEmail;
        this.title = builder.title;
        this.description = builder.description;
        this.priority = builder.priority;
        this.tags = builder.tags != null ? Collections.unmodifiableList(new ArrayList<>(builder.tags))
                : Collections.emptyList();
        this.assigneeEmail = builder.assigneeEmail;
        this.customerVisible = builder.customerVisible;
        this.slaMinutes = builder.slaMinutes;
        this.source = builder.source;
    }

    // Getters
    public String getId() { return id; }
    public String getReporterEmail() { return reporterEmail; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public List<String> getTags() { return tags; } // safe: unmodifiable
    public String getAssigneeEmail() { return assigneeEmail; }
    public boolean isCustomerVisible() { return customerVisible; }
    public Integer getSlaMinutes() { return slaMinutes; }
    public String getSource() { return source; }

    /**
     * Builder for IncidentTicket
     */
    public static class Builder {
        // required
        private final String id;
        private final String reporterEmail;
        private final String title;

        // optional
        private String description;
        private String priority;
        private List<String> tags;
        private String assigneeEmail;
        private boolean customerVisible = false;
        private Integer slaMinutes;
        private String source;

        // Required fields in Builder constructor
        public Builder(String id, String reporterEmail, String title) {
            this.id = id;
            this.reporterEmail = reporterEmail;
            this.title = title;
        }

        // Fluent setters for optional fields
        public Builder description(String description) { this.description = description; return this; }
        public Builder priority(String priority) { this.priority = priority; return this; }
        public Builder tags(List<String> tags) { this.tags = tags != null ? new ArrayList<>(tags) : null; return this; }
        public Builder assigneeEmail(String assigneeEmail) { this.assigneeEmail = assigneeEmail; return this; }
        public Builder customerVisible(boolean visible) { this.customerVisible = visible; return this; }
        public Builder slaMinutes(Integer slaMinutes) { this.slaMinutes = slaMinutes; return this; }
        public Builder source(String source) { this.source = source; return this; }

        /**
         * Build method with centralized validation
         */
        public IncidentTicket build() {
            Validation.requireTicketId(id);
            Validation.requireEmail(reporterEmail, "reporterEmail");
            Validation.requireNonBlank(title, "title");
            Validation.requireMaxLen(title, 80, "title");
            if (assigneeEmail != null) Validation.requireEmail(assigneeEmail, "assigneeEmail");
            Validation.requireOneOf(priority, "priority", "LOW","MEDIUM","HIGH","CRITICAL");
            Validation.requireRange(slaMinutes, 5, 7200, "slaMinutes");

            return new IncidentTicket(this);
        }

        /**
         * Copy builder for creating new instance from existing ticket
         */
        public static Builder from(IncidentTicket ticket) {
            Builder b = new Builder(ticket.id, ticket.reporterEmail, ticket.title);
            b.description = ticket.description;
            b.priority = ticket.priority;
            b.tags = ticket.tags != null ? new ArrayList<>(ticket.tags) : null;
            b.assigneeEmail = ticket.assigneeEmail;
            b.customerVisible = ticket.customerVisible;
            b.slaMinutes = ticket.slaMinutes;
            b.source = ticket.source;
            return b;
        }
    }

    @Override
    public String toString() {
        return "IncidentTicket{" +
                "id='" + id + '\'' +
                ", reporterEmail='" + reporterEmail + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", tags=" + tags +
                ", assigneeEmail='" + assigneeEmail + '\'' +
                ", customerVisible=" + customerVisible +
                ", slaMinutes=" + slaMinutes +
                ", source='" + source + '\'' +
                '}';
    }
}