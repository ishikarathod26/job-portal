package com.ishik.jobportal.controller;

import com.ishik.jobportal.entity.Job;
import com.ishik.jobportal.entity.Employer;
import com.ishik.jobportal.service.JobService;
import com.ishik.jobportal.service.EmployerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * MVC Controller for serving Thymeleaf HTML pages.
 * Handles page navigation and form submissions for the web UI.
 */
@Controller
public class PageController {

    private final JobService jobService;
    private final EmployerService employerService;

    // Constructor Injection
    public PageController(JobService jobService, EmployerService employerService) {
        this.jobService = jobService;
        this.employerService = employerService;
    }

    // Home page
    @GetMapping("/")
    public String homePage(Model model) {
        long jobCount = jobService.getJobCount();
        model.addAttribute("jobCount", jobCount);
        return "home";
    }

    // View all jobs page
    @GetMapping("/jobs")
    public String viewAllJobs(Model model) {
        List<Job> jobs = jobService.getAllJobs();
        model.addAttribute("jobs", jobs);
        return "jobs";
    }

    // View single job details
    @GetMapping("/jobs/{id}")
    public String viewJobDetails(@PathVariable Long id, Model model) {
        Optional<Job> job = jobService.getJobById(id);
        if (job.isPresent()) {
            model.addAttribute("job", job.get());
            return "job-details";
        }
        return "redirect:/jobs";
    }

    // Show post job form
    @GetMapping("/post-job")
    public String showPostJobForm(Model model) {
        model.addAttribute("job", new Job());
        List<Employer> employers = employerService.getAllEmployers();
        model.addAttribute("employers", employers);
        return "post-job";
    }

    // Handle post job form submission
    @PostMapping("/post-job")
    public String handlePostJob(@ModelAttribute Job job) {
        jobService.createJob(job);
        return "redirect:/jobs?success=posted";
    }

    // Show edit job form
    @GetMapping("/jobs/{id}/edit")
    public String showEditJobForm(@PathVariable Long id, Model model) {
        Optional<Job> job = jobService.getJobById(id);
        if (job.isPresent()) {
            model.addAttribute("job", job.get());
            return "edit-job";
        }
        return "redirect:/jobs";
    }

    // Handle edit job form submission
    @PostMapping("/jobs/{id}/edit")
    public String handleEditJob(@PathVariable Long id, @ModelAttribute Job job) {
        jobService.updateJob(id, job);
        return "redirect:/jobs/{id}?success=updated";
    }

    // Delete job and redirect
    @GetMapping("/jobs/{id}/delete")
    public String deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return "redirect:/jobs?success=deleted";
    }

    // Show apply form
    @GetMapping("/jobs/{id}/apply")
    public String showApplyForm(@PathVariable Long id, Model model) {
        Optional<Job> job = jobService.getJobById(id);
        if (job.isPresent()) {
            model.addAttribute("job", job.get());
            return "apply";
        }
        return "redirect:/jobs";
    }
}
