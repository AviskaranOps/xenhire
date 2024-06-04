package xenhire.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xenhire.dto.ClientAssessmentDataResponseDTO;
import xenhire.model.ClientAssessmentData;

public interface ClientAssessmentDataRepository extends JpaRepository<ClientAssessmentData, Long>{

	
	@Query(value="select \r\n"
			+ "	csd.option_type, \r\n"
			+ "	q.correct_option,\r\n"
			+ "	csd.option_no, \r\n"
			+ "	qo.option_desc, \r\n"
			+ "	qo.option_level, \r\n"
			+ "	qo.competency_id, \r\n"
			+ "	c.name as competencyName, \r\n"
			+ "	c.pillar_id, \r\n"
			+ "	p.name as pillarName \r\n"
			+ "	from \r\n"
			+ "	client_assessment_data csd\r\n"
			+ "	right join client_options qo on csd.option_no=qo.id\r\n"
			+ "	left join client_questionnaire q on q.question_no=qo.questionnaire_no\r\n"
			+ "	right join competency c on qo.competency_id=c.id\r\n"
			+ "	left join pillar p on p.id=c.pillar_id \r\n"
			+ "	where csd.client_id=:clientId order by csd.id",
			nativeQuery=true
			)
	List<Map<String, Object>> getClientAssessment(long clientId);
	
	
	



	public interface ClientAssessmentResponseData{
		public String getoption_type();
		public String getcorrect_option();
		public int getoption_no();
		public String getoption_desc();
		public String getoption_level();
		public int getcompetency_id();
		public String getcompetencyName();
		public int getpillar_id();
		public String getpillarName();
	}
}
