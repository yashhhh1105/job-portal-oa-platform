import re

# A small reference skill vocabulary to check for. In Week 3 this goes
# away entirely in favor of proper extraction, but it's a reasonable
# stand-in for now.
KNOWN_SKILLS = [
    "java", "spring boot", "spring security", "python", "fastapi",
    "javascript", "react", "node.js", "express.js", "mongodb",
    "postgresql", "sql", "redis", "docker", "kafka", "kubernetes",
    "aws", "git", "rest api", "microservices", "jwt",
]


def _extract_present_skills(text: str) -> set:
    text_lower = text.lower()
    present = set()
    for skill in KNOWN_SKILLS:
        # \b word boundaries prevent "sql" from matching inside "postgresql",
        # or e.g. "java" incorrectly matching inside "javascript".
        pattern = r"\b" + re.escape(skill) + r"\b"
        if re.search(pattern, text_lower):
            present.add(skill)
    return present


def analyze_resume(resume_text: str, job_description: str | None) -> dict:
    """
    Returns a dict shaped like ResumeAnalyzedEvent's fields (minus
    submissionId/status, which the caller sets).
    """
    resume_skills = _extract_present_skills(resume_text)

    if job_description:
        job_skills = _extract_present_skills(job_description)
        matched = sorted(resume_skills & job_skills)
        missing = sorted(job_skills - resume_skills)
        denominator = len(job_skills) if job_skills else 1
        score = round(len(matched) / denominator, 2)
    else:
        # No specific job to match against - just report what was found
        # and treat it as a general skill-coverage score against the
        # known vocabulary.
        matched = sorted(resume_skills)
        missing = []
        score = round(len(resume_skills) / len(KNOWN_SKILLS), 2)

    return {
        "matchScore": score,
        "matchedSkills": matched,
        "missingSkills": missing,
    }