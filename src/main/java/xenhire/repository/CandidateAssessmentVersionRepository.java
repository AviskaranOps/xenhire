package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xenhire.model.CandidateAssessmentVersion;

public interface CandidateAssessmentVersionRepository extends JpaRepository<CandidateAssessmentVersion, Long>{

	
	
	@Query(value="select ifnull(max(version_no),0) from candidate_assessment_version where candidate_id=:candidateId order by version_no desc limit 1", nativeQuery=true)
	int getMaxVersionofcandidate(long candidateId);

	CandidateAssessmentVersion findByCandidateIdAndVersionNo(long candidateId, int versionNo);

	List<CandidateAssessmentVersion> findByCandidateId(long candidateId);

}
