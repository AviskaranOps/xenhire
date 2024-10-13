package xenhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xenhire.model.CandidatePersonalInfo;

public interface CandidatePersonalInfoRepository extends JpaRepository<CandidatePersonalInfo, Long>{

	@Query(value="select ifnull(max(id), 0) from candidate_personal_info", nativeQuery=true)
	int getMaxVersion();

	CandidatePersonalInfo findByCandidateId(long candidateId);

}
