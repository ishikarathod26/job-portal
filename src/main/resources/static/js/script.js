/**
 * MyJobPortal - Client-Side JavaScript
 * Handles live REST API calls for searching jobs and submitting applications.
 */

// Search jobs using REST API
function searchJobs() {
    const keyword = document.getElementById('searchInput').value.trim();
    if (!keyword) {
        window.location.href = '/jobs';
        return;
    }

    fetch(`/api/jobs/search?keyword=${encodeURIComponent(keyword)}`)
        .then(response => response.json())
        .then(jobs => {
            renderJobCards(jobs);
        })
        .catch(error => {
            console.error('Error searching jobs:', error);
        });
}

// Clear search input and show all jobs
function clearSearch() {
    document.getElementById('searchInput').value = '';
    window.location.href = '/jobs';
}

// Render dynamic job cards from JSON response
function renderJobCards(jobs) {
    const container = document.getElementById('jobCardsContainer');
    if (!container) return;

    if (jobs.length === 0) {
        container.innerHTML = `
            <div class="no-results" style="grid-column: 1/-1; text-align: center; padding: 40px;">
                <h3>No jobs found matching your search</h3>
                <p>Try searching with different keywords like 'Java', 'Python', 'React', etc.</p>
            </div>
        `;
        return;
    }

    container.innerHTML = jobs.map(job => `
        <div class="job-card">
            <div class="job-card-header">
                <h3 class="job-title">${escapeHtml(job.title)}</h3>
                <span class="job-type type-${job.jobType ? job.jobType.toLowerCase().replace('_', '') : 'fulltime'}">${escapeHtml(job.jobType || 'FULL_TIME')}</span>
            </div>
            <div class="job-card-body">
                <p class="job-company">🏢 ${escapeHtml(job.company)}</p>
                <p class="job-location">📍 ${escapeHtml(job.location)}</p>
                <p class="job-experience">💼 ${job.experienceRequired || 0} years experience</p>
                <p class="job-salary">💰 ₹${Number(job.salary || 0).toLocaleString('en-IN')}/year</p>
                <p class="job-description">${escapeHtml(job.description ? job.description.substring(0, 120) + '...' : '')}</p>
                ${job.skillsRequired ? `
                    <div class="job-skills">
                        ${job.skillsRequired.split(',').map(s => `<span class="skill-tag">${escapeHtml(s.trim())}</span>`).join('')}
                    </div>
                ` : ''}
            </div>
            <div class="job-card-footer">
                <span class="posted-date">Posted: ${job.postedDate || 'Recent'}</span>
                <div class="job-actions">
                    <a href="/jobs/${job.id}" class="btn btn-primary">View Details</a>
                    <a href="/jobs/${job.id}/apply" class="btn btn-success">Apply</a>
                </div>
            </div>
        </div>
    `).join('');
}

// Submit job application via REST API
function submitApplication(event, jobId) {
    event.preventDefault();
    const alertBox = document.getElementById('alertMessage');

    const applicationData = {
        applicantName: document.getElementById('applicantName').value,
        email: document.getElementById('email').value,
        phone: document.getElementById('phone').value,
        resumeSummary: document.getElementById('resumeSummary').value,
        coverLetter: document.getElementById('coverLetter').value
    };

    fetch(`/api/applications/job/${jobId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(applicationData)
    })
    .then(async response => {
        if (response.ok) {
            alertBox.className = 'alert alert-success';
            alertBox.style.display = 'block';
            alertBox.innerHTML = '🎉 Application submitted successfully!';
            document.getElementById('applyForm').reset();
            setTimeout(() => {
                window.location.href = `/jobs/${jobId}`;
            }, 2000);
        } else {
            const errorText = await response.text();
            alertBox.className = 'alert alert-danger';
            alertBox.style.display = 'block';
            alertBox.innerHTML = `⚠️ ${errorText || 'Failed to submit application'}`;
        }
    })
    .catch(error => {
        console.error('Error submitting application:', error);
        alertBox.className = 'alert alert-danger';
        alertBox.style.display = 'block';
        alertBox.innerHTML = '⚠️ An unexpected error occurred. Please try again.';
    });
}

// Helper function to prevent XSS in dynamic rendering
function escapeHtml(str) {
    if (!str) return '';
    return str
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}
