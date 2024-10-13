package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.EmpathyAssessmentData;

public interface EmpathyAssessmentDataRepository extends JpaRepository<EmpathyAssessmentData, Long> {

	List<EmpathyAssessmentData> findByAssessmentBatchId(long assessmentId);

}
