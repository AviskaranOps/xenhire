package xenhire.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.Assessments;

public interface AssessmentsRepository extends JpaRepository<Assessments, Long>{

	Assessments findByName(String assessment);

	Page<Assessments> findAllByActive(boolean active, Pageable paging);

}
