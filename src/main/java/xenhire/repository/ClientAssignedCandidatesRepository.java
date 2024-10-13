package xenhire.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.ClientAssignedCandidates;

public interface ClientAssignedCandidatesRepository extends JpaRepository<ClientAssignedCandidates, Long>{

	Page<ClientAssignedCandidates> findAllByClientId(long clientId, Pageable paging);

}
