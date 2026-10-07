package com.ishik.jobportal.controller;

import com.ishik.jobportal.entity.Job;
import com.ishik.jobportal.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Job API endpoints.
 * Handles all HTTP requests related to Job CRUD operations.
 * Returns JSON responses for API consumers (e.g., Postman, frontend JS).
 */
@RestController
@RequestMapping("/api/jobs")
public class JobApiController {

    private final JobService jobService;

    // Constructor Injection
    public JobApiController(JobService jobService) {
        this.jobService = jobService;
    }

    // GET /api/jobs - Get all jobs
    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    // GET /api/jobs/{id} - Get a specific job by ID
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        Optional<Job> job = jobService.getJobById(id);
        return job.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/jobs - Create a new job
    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        Job createdJob = jobService.createJob(job);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJob);
    }

    // PUT /api/jobs/{id} - Update an existing job
    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job updatedJob) {
        try {
            Job job = jobService.updateJob(id, updatedJob);
            return ResponseEntity.ok(job);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/jobs/{id} - Delete a job
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        try {
            jobService.deleteJob(id);
            return ResponseEntity.ok("Job deleted successfully with id: " + id);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/jobs/search?keyword=java - Search jobs by keyword
    @GetMapping("/search")
    public ResponseEntity<List<Job>> searchJobs(@RequestParam String keyword) {
        List<Job> jobs = jobService.searchJobs(keyword);
        return ResponseEntity.ok(jobs);
    }

    // GET /api/jobs/type/{jobType} - Filter by job type
    @GetMapping("/type/{jobType}")
    public ResponseEntity<List<Job>> getJobsByType(@PathVariable String jobType) {
        List<Job> jobs = jobService.getJobsByType(jobType);
        return ResponseEntity.ok(jobs);
    }

    // GET /api/jobs/location?location=bangalore - Filter by location
    @GetMapping("/location")
    public ResponseEntity<List<Job>> getJobsByLocation(@RequestParam String location) {
        List<Job> jobs = jobService.getJobsByLocation(location);
        return ResponseEntity.ok(jobs);
    }

    // GET /api/jobs/count - Get total job count
    @GetMapping("/count")
    public ResponseEntity<Long> getJobCount() {
        long count = jobService.getJobCount();
        return ResponseEntity.ok(count);
    }
}
