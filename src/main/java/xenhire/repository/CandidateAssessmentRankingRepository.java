package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.CandidateAssessmentRanking;

public interface CandidateAssessmentRankingRepository extends JpaRepository<CandidateAssessmentRanking, Long>{

	List<CandidateAssessmentRanking> findByCandidateId(long candidateId);

}
