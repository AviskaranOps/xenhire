package xenhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.CandidatePreferences;

public interface CandidatePreferencesRepository extends JpaRepository<CandidatePreferences, Long>{

	CandidatePreferences findByCandidateId(long candidateId);

}
