package xenhire.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.Pillar;

public interface PillarRepository extends JpaRepository<Pillar, Long>{

	Optional<Pillar> findByName(String name);
}
