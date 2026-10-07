package com.ishik.jobportal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * Entity representing a job application submitted by a candidate.
 * Maps to the 'job_applications' table in MySQL database.
 */
@Entity
@Table(name = "job_applications")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Applicant name is required")
    @Column(name = "applicant_name", nullable = false)
    private String applicantName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email")
    @Column(nullable = false)
    private String email;

    @Column(length = 15)
    private String phone;

    @Column(name = "cover_letter", length = 2000)
    private String coverLetter;

    @Column(name = "resume_summary", length = 1000)
    private String resumeSummary;

    @Column(name = "applied_date")
    private LocalDate appliedDate;

    @Column(nullable = false)
    private String status; // PENDING, REVIEWED, ACCEPTED, REJECTED

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    // Default constructor
    public JobApplication() {
    }

    // Parameterized constructor
    public JobApplication(String applicantName, String email, String phone,
                          String coverLetter, String resumeSummary, Job job) {
        this.applicantName = applicantName;
        this.email = email;
        this.phone = phone;
        this.coverLetter = coverLetter;
        this.resumeSummary = resumeSummary;
        this.appliedDate = LocalDate.now();
        this.status = "PENDING";
        this.job = job;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getApplicantName() { return applicantName; }
    public void setApplicantName(String applicantName) { this.applicantName = applicantName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCoverLetter() { return coverLetter; }
    public void setCoverLetter(String coverLetter) { this.coverLetter = coverLetter; }

    public String getResumeSummary() { return resumeSummary; }
    public void setResumeSummary(String resumeSummary) { this.resumeSummary = resumeSummary; }

    public LocalDate getAppliedDate() { return appliedDate; }
    public void setAppliedDate(LocalDate appliedDate) { this.appliedDate = appliedDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }

    @Override
    public String toString() {
        return "JobApplication{" +
                "id=" + id +
                ", applicantName='" + applicantName + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
