package com.ishik.jobportal.service;

import com.ishik.jobportal.entity.Job;
import com.ishik.jobportal.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service class for Job-related business logic.
 * Acts as the middle layer between Controller and Repository.
 * Contains all business rules and data processing logic.
 */
@Service
public class JobService {

    private final JobRepository jobRepository;

    // Constructor Injection - Spring automatically injects JobRepository
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // Get all jobs from the database
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // Get only active jobs
    public List<Job> getActiveJobs() {
        return jobRepository.findByIsActiveTrue();
    }

    // Get a single job by its ID
    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }

    // Create a new job listing
    public Job createJob(Job job) {
        job.setPostedDate(LocalDate.now());
        job.setIsActive(true);
        return jobRepository.save(job);
    }

    // Update an existing job
    public Job updateJob(Long id, Job updatedJob) {
        Optional<Job> existingJobOpt = jobRepository.findById(id);
        if (existingJobOpt.isPresent()) {
            Job existingJob = existingJobOpt.get();
            existingJob.setTitle(updatedJob.getTitle());
            existingJob.setDescription(updatedJob.getDescription());
            existingJob.setCompany(updatedJob.getCompany());
            existingJob.setLocation(updatedJob.getLocation());
            existingJob.setSalary(updatedJob.getSalary());
            existingJob.setJobType(updatedJob.getJobType());
            existingJob.setExperienceRequired(updatedJob.getExperienceRequired());
            existingJob.setSkillsRequired(updatedJob.getSkillsRequired());
            return jobRepository.save(existingJob);
        }
        throw new RuntimeException("Job not found with id: " + id);
    }

    // Delete a job by ID
    public void deleteJob(Long id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
        } else {
            throw new RuntimeException("Job not found with id: " + id);
        }
    }

    // Search jobs by keyword
    public List<Job> searchJobs(String keyword) {
        return jobRepository.searchByKeyword(keyword);
    }

    // Filter jobs by type
    public List<Job> getJobsByType(String jobType) {
        return jobRepository.findByJobType(jobType);
    }

    // Filter jobs by location
    public List<Job> getJobsByLocation(String location) {
        return jobRepository.findByLocationContainingIgnoreCase(location);
    }

    // Get jobs posted by a specific employer
    public List<Job> getJobsByEmployer(Long employerId) {
        return jobRepository.findByEmployerId(employerId);
    }

    // Get count of all jobs
    public long getJobCount() {
        return jobRepository.count();
    }
}
