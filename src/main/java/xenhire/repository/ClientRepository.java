package xenhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{

	long countByActive(boolean active);

	Client findByCompanyName(String clientName);

}
