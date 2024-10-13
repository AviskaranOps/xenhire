package xenhire.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xenhire.model.CandidateReferenceQuestionsData;

public interface CandidateReferenceQuestionsDataRepository extends JpaRepository<CandidateReferenceQuestionsData, Long>{

	
	@Query(value="select csd.option_type, q.correct_option,csd.option_no, qo.option_desc, qo.option_level, qo.competency_id, c.name as competencyName, c.pillar_id, p.name as pillarName from candidate_reference_questions_data csd right join client_references_options qo on csd.option_no=qo.id left join client_references_questionnaire q on q.question_no=qo.questionnaire_no right join competency c on qo.competency_id=c.id left join pillar p on p.id=c.pillar_id where csd.reference_id = :referenceId order by csd.id;", nativeQuery=true)
	List<Map<String, Object>> getCandidateReferenceData(long referenceId);

}
