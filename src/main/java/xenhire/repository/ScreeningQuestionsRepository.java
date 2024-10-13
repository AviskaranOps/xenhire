package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.ScreeningQuestions;

public interface ScreeningQuestionsRepository extends JpaRepository<ScreeningQuestions, Long>{

	List<ScreeningQuestions> findByJobId(long jobId);

	List<ScreeningQuestions> findAllByJobId(long jobId);

}
