package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.ConflictManagementAssessmentData;

public interface ConflictManagementAssessmentDataRepository extends JpaRepository<ConflictManagementAssessmentData, Long>{

	List<ConflictManagementAssessmentData> findByAssessmentBatchId(long assessmentId);

}
