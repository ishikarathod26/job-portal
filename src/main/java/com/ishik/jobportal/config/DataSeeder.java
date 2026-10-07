package com.ishik.jobportal.config;

import com.ishik.jobportal.entity.Employer;
import com.ishik.jobportal.entity.Job;
import com.ishik.jobportal.repository.EmployerRepository;
import com.ishik.jobportal.repository.JobRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * DataSeeder loads sample data into the database when the application starts.
 * Implements CommandLineRunner so it runs automatically after Spring Boot starts.
 * Only seeds data if the database is empty (first run).
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final JobRepository jobRepository;
    private final EmployerRepository employerRepository;

    // Constructor Injection
    public DataSeeder(JobRepository jobRepository, EmployerRepository employerRepository) {
        this.jobRepository = jobRepository;
        this.employerRepository = employerRepository;
    }

    @Override
    public void run(String... args) {
        // Only seed if database is empty
        if (jobRepository.count() == 0 && employerRepository.count() == 0) {
            seedData();
            System.out.println("=== Sample data loaded successfully! ===");
        } else {
            System.out.println("=== Database already has data. Skipping seeder. ===");
        }
    }

    private void seedData() {
        // Create sample employers
        Employer techCorp = new Employer("TechCorp Solutions", "Rahul Sharma",
                "hr@techcorp.com", "9876543210", "Leading IT solutions company based in Bangalore");
        Employer dataInc = new Employer("DataInc Analytics", "Priya Patel",
                "careers@datainc.com", "9876543211", "Data analytics and AI startup in Hyderabad");
        Employer webWorks = new Employer("WebWorks Studio", "Amit Kumar",
                "jobs@webworks.com", "9876543212", "Creative web development agency in Mumbai");

        employerRepository.save(techCorp);
        employerRepository.save(dataInc);
        employerRepository.save(webWorks);

        // Create sample jobs
        Job job1 = new Job("Java Backend Developer", 
                "Develop and maintain RESTful APIs using Spring Boot. Work with microservices architecture and MySQL databases.",
                "TechCorp Solutions", "Bangalore", 800000.0, "FULL_TIME", 2, "Java, Spring Boot, MySQL, REST APIs");
        job1.setEmployer(techCorp);

        Job job2 = new Job("Frontend Developer",
                "Build responsive user interfaces using React.js. Collaborate with backend team for API integration.",
                "WebWorks Studio", "Mumbai", 700000.0, "FULL_TIME", 1, "HTML, CSS, JavaScript, React");
        job2.setEmployer(webWorks);

        Job job3 = new Job("Data Analyst",
                "Analyze large datasets using Python and SQL. Create dashboards and reports for business insights.",
                "DataInc Analytics", "Hyderabad", 600000.0, "FULL_TIME", 1, "Python, SQL, Excel, Tableau");
        job3.setEmployer(dataInc);

        Job job4 = new Job("DevOps Engineer",
                "Manage CI/CD pipelines, deploy applications on AWS. Automate infrastructure using Docker and Kubernetes.",
                "TechCorp Solutions", "Bangalore", 1000000.0, "FULL_TIME", 3, "Docker, Kubernetes, AWS, Jenkins");
        job4.setEmployer(techCorp);

        Job job5 = new Job("Java Intern",
                "Learn and contribute to Java Spring Boot projects. Great opportunity for freshers to gain industry experience.",
                "TechCorp Solutions", "Bangalore", 200000.0, "INTERNSHIP", 0, "Java, Spring Boot, SQL");
        job5.setEmployer(techCorp);

        Job job6 = new Job("UI/UX Designer",
                "Design intuitive user interfaces and create wireframes. Work closely with developers to implement designs.",
                "WebWorks Studio", "Mumbai", 550000.0, "FULL_TIME", 2, "Figma, Adobe XD, HTML, CSS");
        job6.setEmployer(webWorks);

        Job job7 = new Job("Python Developer",
                "Develop backend services using Python Django. Build data processing pipelines and REST APIs.",
                "DataInc Analytics", "Hyderabad", 750000.0, "FULL_TIME", 2, "Python, Django, PostgreSQL, REST APIs");
        job7.setEmployer(dataInc);

        Job job8 = new Job("Part-Time Content Writer",
                "Write technical blogs and documentation for software products. Remote-friendly position.",
                "WebWorks Studio", "Remote", 300000.0, "PART_TIME", 0, "Technical Writing, SEO, WordPress");
        job8.setEmployer(webWorks);

        jobRepository.save(job1);
        jobRepository.save(job2);
        jobRepository.save(job3);
        jobRepository.save(job4);
        jobRepository.save(job5);
        jobRepository.save(job6);
        jobRepository.save(job7);
        jobRepository.save(job8);
    }
}
