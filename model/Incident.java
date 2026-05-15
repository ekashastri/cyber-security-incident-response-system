package model;

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Incident {

    private String id, type, severity, status, assignedTo, description;
    private List<String> notes = new ArrayList<>();

    private LocalDate createdDate;
    private LocalDate resolvedDate;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Incident(String id, String type, String severity, String description) {
        this.id = id;
        this.type = type;
        this.severity = severity;
        this.description = description;
        this.status = "ASSIGNED";
        this.assignedTo = "UNASSIGNED";
        this.createdDate = LocalDate.now();
    }

    public String getId() { return id; }
    public String getAssignedTo() { return assignedTo; }
    public String getStatus() { return status; }

    public void setAssignedTo(String a) { assignedTo = a; }

    public boolean canInvestigate() { return status.equals("ASSIGNED"); }
    public boolean canResolve() { return status.equals("IN_PROGRESS"); }

    public void beginInvestigation() {
        if(canInvestigate()) status = "IN_PROGRESS";
    }

    public void resolve() {
        if(canResolve()) {
            status = "RESOLVED";
            resolvedDate = LocalDate.now();
        }
    }

    public void addNote(String n) { notes.add(n); }

    public boolean isResolved() { return status.equals("RESOLVED"); }
    public boolean isRecent() { return !status.equals("RESOLVED"); }

    public String getCreatedDateFormatted() {
        return createdDate.format(formatter);
    }

    public String getResolvedDateFormatted() {
        if(resolvedDate == null) return "-";
        return resolvedDate.format(formatter);
    }

    @Override
    public String toString() {
        return id + "," + type + "," + severity + "," + status + "," +
                assignedTo + "," + description + "," +
                createdDate + "," + (resolvedDate == null ? "-" : resolvedDate) + "," +
                String.join("|", notes);
    }

    public static Incident fromString(String s) {
        String[] p = s.split(",", -1);

        Incident i = new Incident(p[0], p[1], p[2], p[5]);
        i.status = p[3];
        i.assignedTo = p[4];

        i.createdDate = LocalDate.parse(p[6]);

        if(!p[7].equals("-"))
            i.resolvedDate = LocalDate.parse(p[7]);

        if(p.length > 8 && !p[8].isEmpty())
            i.notes = new ArrayList<>(Arrays.asList(p[8].split("\\|")));

        return i;
    }

    public void display() {
        System.out.println("------------------------------");
        System.out.println("Incident Details");
        System.out.println("------------------------------");
        System.out.println("ID: " + id);
        System.out.println("Type: " + type);
        System.out.println("Severity: " + severity);
        System.out.println("Status: " + status);
        System.out.println("Description: " + description);
        System.out.println("Created Date: " + getCreatedDateFormatted());
        System.out.println("Resolved Date: " + getResolvedDateFormatted());

        System.out.println("Notes:");
        for(String n : notes) {
            System.out.println("- " + n);
        }

        System.out.println("------------------------------");
    }
}