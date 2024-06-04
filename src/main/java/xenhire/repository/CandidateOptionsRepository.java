package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.CandidateOptions;

public interface CandidateOptionsRepository extends JpaRepository<CandidateOptions, Long>{

	List<CandidateOptions> findByQuestionnaireNo(int questionNo);

	CandidateOptions findByQuestionnaireNoAndOptionDesc(int questionNo, String opts);

	CandidateOptions findByOptionDesc(String opts);

}
