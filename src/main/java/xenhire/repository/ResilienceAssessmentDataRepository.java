package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.ResilienceAssessmentData;

public interface ResilienceAssessmentDataRepository extends JpaRepository<ResilienceAssessmentData, Long> {

	List<ResilienceAssessmentData> findByAssessmentBatchId(long assessmentId);

}
