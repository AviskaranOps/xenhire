package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.RelationshipBuildingAssessmentData;

public interface RelationshipBuildingAssessmentDataRepository extends JpaRepository<RelationshipBuildingAssessmentData, Long> {

	List<RelationshipBuildingAssessmentData> findByAssessmentBatchId(long assessmentId);

}
