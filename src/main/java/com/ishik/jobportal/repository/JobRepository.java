package com.ishik.jobportal.repository;

import com.ishik.jobportal.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Job entity.
 * Extends JpaRepository to get built-in CRUD operations.
 * Spring Data JPA automatically provides the implementation at runtime.
 */
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    // Find all active jobs
    List<Job> findByIsActiveTrue();

    // Find jobs by job type (e.g., FULL_TIME, PART_TIME)
    List<Job> findByJobType(String jobType);

    // Find jobs by company name
    List<Job> findByCompanyContainingIgnoreCase(String company);

    // Find jobs by location
    List<Job> findByLocationContainingIgnoreCase(String location);

    // Search jobs by keyword in title, description, or skills
    @Query("SELECT j FROM Job j WHERE " +
           "LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(j.skillsRequired) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Job> searchByKeyword(@Param("keyword") String keyword);

    // Find jobs by employer id
    List<Job> findByEmployerId(Long employerId);

    // Find jobs with experience requirement less than or equal to given value
    List<Job> findByExperienceRequiredLessThanEqual(Integer experience);
}
