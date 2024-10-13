package xenhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.ClientSettings;

public interface ClientSettingsRepository extends JpaRepository<ClientSettings, Long>{

	ClientSettings findByClientId(long clientId);

}
