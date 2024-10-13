package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.CandidateValueAssessmentVersion;

public interface CandidateValueAssessmentVersionRepository extends JpaRepository<CandidateValueAssessmentVersion, Long>{

	List<CandidateValueAssessmentVersion> findByCandidateId(long candidateId);
	
	
}
