package xenhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.ClientDetails;

public interface ClientDetailsRepository extends JpaRepository<ClientDetails, Long>{

	 ClientDetails findByClientId(long clientId);

}
