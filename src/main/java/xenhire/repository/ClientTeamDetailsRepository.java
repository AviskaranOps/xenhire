package xenhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.ClientTeamDetails;

public interface ClientTeamDetailsRepository extends JpaRepository<ClientTeamDetails, Long>{

	ClientTeamDetails findByClientId(long clientId);

}
