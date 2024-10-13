package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.StressManagementAssessmentData;

public interface StressManagementAssessmentDataRepository extends JpaRepository<StressManagementAssessmentData, Long>{

	List<StressManagementAssessmentData> findByAssessmentBatchId(long assessmentId);

}
