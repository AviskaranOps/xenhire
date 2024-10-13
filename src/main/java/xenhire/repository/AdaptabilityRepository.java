package xenhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.Adaptability;

public interface AdaptabilityRepository extends JpaRepository<Adaptability, Long>{

	Adaptability findByQuestion(String question);

}
