package com.ishik.jobportal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Entity representing a job listing posted by an employer.
 * Maps to the 'jobs' table in MySQL database.
 */
@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Job title is required")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Job description is required")
    @Column(nullable = false, length = 2000)
    private String description;

    @NotBlank(message = "Company name is required")
    @Column(nullable = false)
    private String company;

    @NotBlank(message = "Location is required")
    @Column(nullable = false)
    private String location;

    @NotNull(message = "Salary is required")
    @Min(value = 0, message = "Salary must be positive")
    @Column(nullable = false)
    private Double salary;

    @NotBlank(message = "Job type is required")
    @Column(name = "job_type", nullable = false)
    private String jobType; // FULL_TIME, PART_TIME, INTERNSHIP, CONTRACT

    @NotNull(message = "Experience is required")
    @Min(value = 0, message = "Experience must be zero or positive")
    @Column(name = "experience_required")
    private Integer experienceRequired;

    @Column(name = "skills_required", length = 500)
    private String skillsRequired; // Comma-separated skills

    @Column(name = "posted_date")
    private LocalDate postedDate;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id")
    private Employer employer;

    // Default constructor required by JPA
    public Job() {
    }

    // Parameterized constructor for convenience
    public Job(String title, String description, String company, String location,
               Double salary, String jobType, Integer experienceRequired, String skillsRequired) {
        this.title = title;
        this.description = description;
        this.company = company;
        this.location = location;
        this.salary = salary;
        this.jobType = jobType;
        this.experienceRequired = experienceRequired;
        this.skillsRequired = skillsRequired;
        this.postedDate = LocalDate.now();
        this.isActive = true;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }

    public String getJobType() { return jobType; }
    public void setJobType(String jobType) { this.jobType = jobType; }

    public Integer getExperienceRequired() { return experienceRequired; }
    public void setExperienceRequired(Integer experienceRequired) { this.experienceRequired = experienceRequired; }

    public String getSkillsRequired() { return skillsRequired; }
    public void setSkillsRequired(String skillsRequired) { this.skillsRequired = skillsRequired; }

    public LocalDate getPostedDate() { return postedDate; }
    public void setPostedDate(LocalDate postedDate) { this.postedDate = postedDate; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Employer getEmployer() { return employer; }
    public void setEmployer(Employer employer) { this.employer = employer; }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                ", jobType='" + jobType + '\'' +
                '}';
    }
}
