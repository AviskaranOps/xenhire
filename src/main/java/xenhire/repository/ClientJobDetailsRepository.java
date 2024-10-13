package xenhire.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xenhire.model.ClientJobDetails;

public interface ClientJobDetailsRepository extends JpaRepository<ClientJobDetails, Long>{

	Page<ClientJobDetails> findAllByClientId(long clientId, Pageable paging);

	Page<ClientJobDetails> findAllByClientIdAndActive(long clientId, boolean b, Pageable paging);

	Page<ClientJobDetails> findAllByClientIdAndClosed(long clientId, boolean b, Pageable paging);

	@Query(value="select * from client_job_details where client_id=:clientId and created_at > :ninetyDaysBack", nativeQuery=true)
	Page<ClientJobDetails> findAllByClientIdAndCreatedAtAfter90Days(long clientId, LocalDateTime ninetyDaysBack, Pageable paging);
	
	@Query(value="select * from client_job_details where client_id=:clientId and created_at > :yearBack", nativeQuery=true)
	Page<ClientJobDetails> findAllByClientIdAndCreatedAtAfter365Days(long clientId, LocalDateTime yearBack, Pageable paging);

	List<ClientJobDetails> findBySourcingHelpOrOnboardingHelpOrFullServiceStaffingHelp(boolean b, boolean c, boolean d);

	@Query(value="select count(*) from client_job_details where client_id=:clientId", nativeQuery=true)
	int getJobsCount(long clientId);

	@Query(value="select count(*) from client_job_details", nativeQuery=true)
	long getJobsCountofAllClients();

	Page<ClientJobDetails> findAllBySourcingHelp(Pageable paging, boolean b);

	Page<ClientJobDetails> findAllByOnboardingHelp(Pageable paging, boolean b);

	Page<ClientJobDetails> findAllByFullServiceStaffingHelp(Pageable paging, boolean b);

}
