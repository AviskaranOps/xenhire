package xenhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.CandidateReferenceRanking;

public interface CandidateReferenceRankingRepository extends JpaRepository<CandidateReferenceRanking, Long>{

	CandidateReferenceRanking findByCompetency(String competencyName);

}
