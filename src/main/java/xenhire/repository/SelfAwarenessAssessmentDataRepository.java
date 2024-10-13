package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.SelfAwarenessAssessmentData;

public interface SelfAwarenessAssessmentDataRepository extends JpaRepository<SelfAwarenessAssessmentData, Long> {

	List<SelfAwarenessAssessmentData> findByAssessmentBatchId(long assessmentId);

}
