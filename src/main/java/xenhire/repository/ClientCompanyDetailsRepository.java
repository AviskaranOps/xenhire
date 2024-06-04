package xenhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.ClientCompanyDetails;

public interface ClientCompanyDetailsRepository extends JpaRepository<ClientCompanyDetails, Long>{

	ClientCompanyDetails findByClientId(long clientId);

}
