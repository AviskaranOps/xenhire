package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.ClientAssessmentPerBatch;

public interface ClientAssessmentPerBatchRepository extends JpaRepository<ClientAssessmentPerBatch, Long>{

	List<ClientAssessmentPerBatch> findByBatchId(long id);

}
