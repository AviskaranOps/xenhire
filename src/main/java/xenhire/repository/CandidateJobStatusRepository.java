package xenhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.CandidateJobStatus;

public interface CandidateJobStatusRepository extends JpaRepository<CandidateJobStatus, Long>{

	long countByActive(boolean active);

	int countByJobId(long id);

}
