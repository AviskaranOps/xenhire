package xenhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.CandidateAssessmentSectionTracking;

public interface CandidateAssessmentSectionTrackingRepository extends JpaRepository<CandidateAssessmentSectionTracking, Long>{


	CandidateAssessmentSectionTracking findByCandidateId(long candidateId);

	CandidateAssessmentSectionTracking findByCandidateIdAndAssessmentVersionId(long candidateId, int versionNo);

}
