package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.TimeManagementAssessmentData;

public interface TimeManagementAssessmentDataRepository extends JpaRepository<TimeManagementAssessmentData, Long> {

	List<TimeManagementAssessmentData> findByAssessmentBatchId(long assessmentId);

}
