package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.CandidateQuestionnaire;

public interface CandidateQuestionnaireRepository extends JpaRepository<CandidateQuestionnaire, Long>{

	List<CandidateQuestionnaire> findBySection(String section);

	CandidateQuestionnaire findByQuestionNo(int questionnaireNo);

}
