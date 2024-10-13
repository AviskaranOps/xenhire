package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.CandidateScreeningQuestions;

public interface CandidateScreeningQuestionsRepository extends JpaRepository<CandidateScreeningQuestions, Long>{

	List<CandidateScreeningQuestions> findByCandidateIdAndJobId(long candidateId, long jobId);

}
