package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.ClientAssessmentRanking;

public interface ClientAssessmentRankingRepository extends JpaRepository<ClientAssessmentRanking, Long>{

	List<ClientAssessmentRanking> findByClientId(long clientId);
	
	

}
