package com.ishik.jobportal.controller;

import com.ishik.jobportal.entity.JobApplication;
import com.ishik.jobportal.service.JobApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Job Application API endpoints.
 */
@RestController
@RequestMapping("/api/applications")
public class ApplicationApiController {

    private final JobApplicationService applicationService;

    // Constructor Injection
    public ApplicationApiController(JobApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    // GET /api/applications - Get all applications
    @GetMapping
    public ResponseEntity<List<JobApplication>> getAllApplications() {
        List<JobApplication> applications = applicationService.getAllApplications();
        return ResponseEntity.ok(applications);
    }

    // GET /api/applications/{id} - Get application by ID
    @GetMapping("/{id}")
    public ResponseEntity<JobApplication> getApplicationById(@PathVariable Long id) {
        Optional<JobApplication> application = applicationService.getApplicationById(id);
        return application.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/applications/job/{jobId} - Get applications for a job
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<JobApplication>> getApplicationsByJob(@PathVariable Long jobId) {
        List<JobApplication> applications = applicationService.getApplicationsByJob(jobId);
        return ResponseEntity.ok(applications);
    }

    // POST /api/applications/job/{jobId} - Submit application for a job
    @PostMapping("/job/{jobId}")
    public ResponseEntity<?> submitApplication(@PathVariable Long jobId,
                                                @RequestBody JobApplication application) {
        try {
            JobApplication submitted = applicationService.submitApplication(jobId, application);
            return ResponseEntity.status(HttpStatus.CREATED).body(submitted);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT /api/applications/{id}/status?status=REVIEWED - Update application status
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            JobApplication updated = applicationService.updateApplicationStatus(id, status);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/applications/{id} - Delete application
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplication(@PathVariable Long id) {
        try {
            applicationService.deleteApplication(id);
            return ResponseEntity.ok("Application deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
