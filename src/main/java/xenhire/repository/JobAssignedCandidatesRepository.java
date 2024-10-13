package xenhire.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xenhire.model.JobAssignedCandidates;

public interface JobAssignedCandidatesRepository extends JpaRepository<JobAssignedCandidates, Long>{

	List<JobAssignedCandidates> findAllByClientId(long clientId);

	List<JobAssignedCandidates> findAllByJobId(long jobId);

	
	@Query(value="select count(*) from job_assigned_candidates where client_id=:clientId", nativeQuery=true)
	int getCandidatesCount(long clientId);

	Page<JobAssignedCandidates> findAllByClientId(long clientId, Pageable paging);

}
