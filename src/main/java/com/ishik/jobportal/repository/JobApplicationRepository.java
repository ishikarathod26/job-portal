package com.ishik.jobportal.repository;

import com.ishik.jobportal.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for JobApplication entity.
 * Extends JpaRepository for built-in CRUD operations.
 */
@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    // Find all applications for a specific job
    List<JobApplication> findByJobId(Long jobId);

    // Find all applications by applicant email
    List<JobApplication> findByEmail(String email);

    // Find applications by status
    List<JobApplication> findByStatus(String status);

    // Check if an applicant already applied for a job
    boolean existsByEmailAndJobId(String email, Long jobId);
}
