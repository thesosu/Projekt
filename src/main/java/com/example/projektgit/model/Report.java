package com.example.projektgit.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User reporter;

    @ManyToOne(optional = false)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    public Report() {
    }

    public Report(Long id, String description, User reporter, Status status) {
        this.id = id;
        this.description = description;
        this.reporter = reporter;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Report report = (Report) o;
        return Objects.equals(id, report.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
