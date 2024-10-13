package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.CandidateReferences;

public interface CandidateReferencesRepository extends JpaRepository<CandidateReferences, Long>{

	List<CandidateReferences> findByClientIdAndCandidateId(long clientId, long candidateId);

	CandidateReferences findByCandidateIdAndReferenceName(long candidateId, String referenceName);

}
