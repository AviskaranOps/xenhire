package xenhire.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xenhire.model.BatchAssessmentCandidates;

public interface BatchAssessmentCandidatesRepository extends JpaRepository<BatchAssessmentCandidates, Long>{

	@Query(value="select bac.assessment_name, bac.created_at, bac.status, datediff(current_timestamp, bac.created_at) as daysDifff, u.username, bac.batch_id from batch_assessment_candidates bac left join user u on u.id=bac.candidate_id where batch_id=1", nativeQuery=true)
	Page<Object[]> getCandidateAssessmentStatus(long batchId, Pageable paging);

	List<BatchAssessmentCandidates> findAllByBatchId(long id);

	@Query(value="select ifnull(count(*),0) from batch_assessment_candidates where candidate_id=:candidateId", nativeQuery=true)
	int getCandidateAssessmentCount(long candidateId);

	List<BatchAssessmentCandidates> findByCandidateId(long candidateId);

}
