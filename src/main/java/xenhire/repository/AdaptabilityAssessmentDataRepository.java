package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.Adaptability;
import xenhire.model.AdaptabilityAssessmentData;

public interface AdaptabilityAssessmentDataRepository extends JpaRepository<AdaptabilityAssessmentData, Long>{

	List<AdaptabilityAssessmentData> findByAssessmentBatchId(long assessmentId);

}
