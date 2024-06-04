package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xenhire.model.CandidateAssessmentRanking;
import xenhire.model.CandidateValueAssessmentData;

public interface CandidateValueAssessmentDataRepository extends JpaRepository<CandidateValueAssessmentData, Long>{

	@Query(value="select ifnull(max(version_no),0) from candidate_value_assessment_data where candidate_id=:candidateId order by version_no desc limit 1", nativeQuery=true)
	int getMaxVersionNo(long candidateId);

	List<CandidateValueAssessmentData> findByCandidateId(long candidateId);

}
