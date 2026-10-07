package com.ishik.jobportal.service;

import com.ishik.jobportal.entity.Job;
import com.ishik.jobportal.entity.JobApplication;
import com.ishik.jobportal.repository.JobApplicationRepository;
import com.ishik.jobportal.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service class for Job Application business logic.
 */
@Service
public class JobApplicationService {

    private final JobApplicationRepository applicationRepository;
    private final JobRepository jobRepository;

    // Constructor Injection
    public JobApplicationService(JobApplicationRepository applicationRepository,
                                  JobRepository jobRepository) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
    }

    // Get all applications
    public List<JobApplication> getAllApplications() {
        return applicationRepository.findAll();
    }

    // Get application by ID
    public Optional<JobApplication> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }

    // Get all applications for a specific job
    public List<JobApplication> getApplicationsByJob(Long jobId) {
        return applicationRepository.findByJobId(jobId);
    }

    // Submit a new application
    public JobApplication submitApplication(Long jobId, JobApplication application) {
        // Check if job exists
        Optional<Job> jobOpt = jobRepository.findById(jobId);
        if (jobOpt.isEmpty()) {
            throw new RuntimeException("Job not found with id: " + jobId);
        }

        // Check if already applied
        if (applicationRepository.existsByEmailAndJobId(application.getEmail(), jobId)) {
            throw new RuntimeException("You have already applied for this job");
        }

        application.setJob(jobOpt.get());
        application.setAppliedDate(LocalDate.now());
        application.setStatus("PENDING");
        return applicationRepository.save(application);
    }

    // Update application status (PENDING, REVIEWED, ACCEPTED, REJECTED)
    public JobApplication updateApplicationStatus(Long id, String status) {
        Optional<JobApplication> appOpt = applicationRepository.findById(id);
        if (appOpt.isPresent()) {
            JobApplication app = appOpt.get();
            app.setStatus(status);
            return applicationRepository.save(app);
        }
        throw new RuntimeException("Application not found with id: " + id);
    }

    // Delete an application
    public void deleteApplication(Long id) {
        if (applicationRepository.existsById(id)) {
            applicationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Application not found with id: " + id);
        }
    }

    // Get applications by applicant email
    public List<JobApplication> getApplicationsByEmail(String email) {
        return applicationRepository.findByEmail(email);
    }
}
