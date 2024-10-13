package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.CommunicationSkillsAssessmentData;

public interface CommunicationSkillsAssessmentDataRepository extends JpaRepository<CommunicationSkillsAssessmentData, Long> {

	List<CommunicationSkillsAssessmentData> findByAssessmentBatchId(long assessmentId);

}
