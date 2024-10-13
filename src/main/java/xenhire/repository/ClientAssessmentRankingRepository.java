package xenhire.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xenhire.model.ClientAssessmentRanking;

public interface ClientAssessmentRankingRepository extends JpaRepository<ClientAssessmentRanking, Long>{

	List<ClientAssessmentRanking> findByClientId(long clientId);


	List<ClientAssessmentRanking> findByClientIdAndTemplateNo(long clientId, long templateNo);
	
	@Query(value="select template_no, template_name, created_by from client_assessment_ranking where client_id=:clientId and template=true", nativeQuery=true)
	Page<Object[]> getTemplateNos(long clientId, Pageable paging);


	ClientAssessmentRanking findByCompetencyAndCount(String competencyName, int i);


	ClientAssessmentRanking findByCompetency(String competencyName);
	
	

}
