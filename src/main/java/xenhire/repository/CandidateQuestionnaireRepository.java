package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xenhire.model.CandidateQuestionnaire;

public interface CandidateQuestionnaireRepository extends JpaRepository<CandidateQuestionnaire, Long>{

	List<CandidateQuestionnaire> findBySection(String section);

	CandidateQuestionnaire findByQuestionNo(int questionnaireNo);

	@Query(value="select * from candidate_questionnaire where section in ('section1') ", nativeQuery=true)
	List<CandidateQuestionnaire> getAnalysisQuestions();

}
