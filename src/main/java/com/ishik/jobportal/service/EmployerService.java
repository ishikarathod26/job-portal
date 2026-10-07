package com.ishik.jobportal.service;

import com.ishik.jobportal.entity.Employer;
import com.ishik.jobportal.repository.EmployerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for Employer-related business logic.
 */
@Service
public class EmployerService {

    private final EmployerRepository employerRepository;

    // Constructor Injection
    public EmployerService(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    // Get all employers
    public List<Employer> getAllEmployers() {
        return employerRepository.findAll();
    }

    // Get employer by ID
    public Optional<Employer> getEmployerById(Long id) {
        return employerRepository.findById(id);
    }

    // Create a new employer
    public Employer createEmployer(Employer employer) {
        if (employerRepository.existsByEmail(employer.getEmail())) {
            throw new RuntimeException("Employer with email " + employer.getEmail() + " already exists");
        }
        return employerRepository.save(employer);
    }

    // Update employer details
    public Employer updateEmployer(Long id, Employer updatedEmployer) {
        Optional<Employer> existingOpt = employerRepository.findById(id);
        if (existingOpt.isPresent()) {
            Employer existing = existingOpt.get();
            existing.setCompanyName(updatedEmployer.getCompanyName());
            existing.setContactPerson(updatedEmployer.getContactPerson());
            existing.setEmail(updatedEmployer.getEmail());
            existing.setPhone(updatedEmployer.getPhone());
            existing.setAbout(updatedEmployer.getAbout());
            return employerRepository.save(existing);
        }
        throw new RuntimeException("Employer not found with id: " + id);
    }

    // Delete employer
    public void deleteEmployer(Long id) {
        if (employerRepository.existsById(id)) {
            employerRepository.deleteById(id);
        } else {
            throw new RuntimeException("Employer not found with id: " + id);
        }
    }
}
