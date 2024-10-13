package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.CandidateEducationDetails;

public interface CandidateEducationDetailsRepository extends JpaRepository<CandidateEducationDetails, Long>{

	List<CandidateEducationDetails> findByCandidateId(long candidateId);

}
