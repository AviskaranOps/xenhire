package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.ClientPreferences;

public interface ClientPreferencesRepository extends JpaRepository<ClientPreferences, Long>{

	List<ClientPreferences> findByClientId(long clientId);

}
