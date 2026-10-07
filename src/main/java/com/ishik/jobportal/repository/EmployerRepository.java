package com.ishik.jobportal.repository;

import com.ishik.jobportal.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Employer entity.
 * Extends JpaRepository for built-in CRUD operations.
 */
@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {

    // Find employer by email
    Optional<Employer> findByEmail(String email);

    // Check if employer exists by email
    boolean existsByEmail(String email);
}
