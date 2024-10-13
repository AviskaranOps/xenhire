package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.CandidateInterviewFeedback;

public interface CandidateInterviewFeedbackRepository extends JpaRepository<CandidateInterviewFeedback, Long>{

	List<CandidateInterviewFeedback> findByCandidateIdAndJobId(long candidateId, long jobId);

}
