package xenhire.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xenhire.model.ClientIcpTemplate;

public interface ClientIcpTemplateRepository extends JpaRepository<ClientIcpTemplate, Long>{

	@Query(value="select ifnull(max(template_no), 0) from client_icp_template", nativeQuery=true)
	int getMaxTemplateNo();

	@Query(value="select \r\n"
			+ "	csd.option_type, \r\n"
			+ "	q.correct_option,\r\n"
			+ "	csd.option_no, \r\n"
			+ "	qo.option_desc, \r\n"
			+ "	qo.option_level, \r\n"
			+ " qo.weightage, \r\n"
			+ "	qo.competency_id, \r\n"
			+ " csd.template_name, "
			+ " csd.created_by, "
			+ "	c.name as competencyName, \r\n"
			+ "	c.pillar_id, \r\n"
			+ "	p.name as pillarName \r\n"
			+ "	from \r\n"
			+ "	client_icp_template csd\r\n"
			+ "	right join client_options qo on csd.option_no=qo.id\r\n"
			+ "	left join client_questionnaire q on q.question_no=qo.questionnaire_no\r\n"
			+ "	right join competency c on qo.competency_id=c.id\r\n"
			+ "	left join pillar p on p.id=c.pillar_id \r\n"
			+ "	where csd.client_id=:clientId and csd.template_no=:templateNo order by csd.id",
			nativeQuery=true
			)
	List<Map<String, Object>> getClientAssessment(long clientId, long templateNo);

	@Query(value="select * from client_icp_template where template_no=:templateNo limit 1", nativeQuery=true)
	ClientIcpTemplate getOneRecord(long templateNo);

	
}
