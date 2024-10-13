package xenhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xenhire.model.CandidatePreferences;

public interface CandidatePreferencesRepository extends JpaRepository<CandidatePreferences, Long>{

	CandidatePreferences findByCandidateId(long candidateId);

	@Query(value="select ifnull(max(id), 0) from candidate_preferences", nativeQuery=true)
	int getMaxVersionId();

}
