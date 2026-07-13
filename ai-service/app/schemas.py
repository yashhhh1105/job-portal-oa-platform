from pydantic import BaseModel
from typing import Optional, List


# Mirrors com.yash.jobportal.dto.ResumeSubmittedEvent (Java side).
# Field names must match exactly - there is no shared code between the
# two services, only this agreed-upon JSON shape.
class ResumeSubmittedEvent(BaseModel):
    submissionId: int
    resumeText: str
    jobDescription: Optional[str] = None


# Mirrors com.yash.jobportal.dto.ResumeAnalyzedEvent (Java side).
class ResumeAnalyzedEvent(BaseModel):
    submissionId: int
    status: str  # "DONE" or "FAILED"
    matchScore: Optional[float] = None
    matchedSkills: List[str] = []
    missingSkills: List[str] = []
    errorMessage: Optional[str] = None