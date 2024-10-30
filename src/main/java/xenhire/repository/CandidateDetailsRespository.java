package xenhire.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.CandidateDetails;

public interface CandidateDetailsRespository extends JpaRepository<CandidateDetails, Long> {

	Optional<CandidateDetails> findByCandidateId(long candidateId);

}
