package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.CriticalScoring;

public interface CriticalScoringRepository extends JpaRepository<CriticalScoring, Long> {


	List<CriticalScoring> findByInterviewFeedbackId(long id);

}
