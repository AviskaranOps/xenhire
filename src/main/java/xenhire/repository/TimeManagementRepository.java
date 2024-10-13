package xenhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.TimeManagement;

public interface TimeManagementRepository extends JpaRepository<TimeManagement, Long>{

}
