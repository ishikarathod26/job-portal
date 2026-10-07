package com.ishik.jobportal.controller;

import com.ishik.jobportal.entity.Employer;
import com.ishik.jobportal.service.EmployerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Employer API endpoints.
 */
@RestController
@RequestMapping("/api/employers")
public class EmployerApiController {

    private final EmployerService employerService;

    // Constructor Injection
    public EmployerApiController(EmployerService employerService) {
        this.employerService = employerService;
    }

    // GET /api/employers - Get all employers
    @GetMapping
    public ResponseEntity<List<Employer>> getAllEmployers() {
        List<Employer> employers = employerService.getAllEmployers();
        return ResponseEntity.ok(employers);
    }

    // GET /api/employers/{id} - Get employer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employer> getEmployerById(@PathVariable Long id) {
        Optional<Employer> employer = employerService.getEmployerById(id);
        return employer.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/employers - Create a new employer
    @PostMapping
    public ResponseEntity<?> createEmployer(@RequestBody Employer employer) {
        try {
            Employer created = employerService.createEmployer(employer);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT /api/employers/{id} - Update employer
    @PutMapping("/{id}")
    public ResponseEntity<Employer> updateEmployer(@PathVariable Long id, @RequestBody Employer updatedEmployer) {
        try {
            Employer employer = employerService.updateEmployer(id, updatedEmployer);
            return ResponseEntity.ok(employer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/employers/{id} - Delete employer
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployer(@PathVariable Long id) {
        try {
            employerService.deleteEmployer(id);
            return ResponseEntity.ok("Employer deleted successfully with id: " + id);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
