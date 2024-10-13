package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.DecisionMakingAssessmentData;

public interface DecisionMakingAssessmentDataRepository extends JpaRepository<DecisionMakingAssessmentData, Long> {

	List<DecisionMakingAssessmentData> findByAssessmentBatchId(long assessmentId);

}
