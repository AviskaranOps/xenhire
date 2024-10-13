package xenhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.ConflictManagement;

public interface ConflictManagementRepository extends JpaRepository<ConflictManagement, Long>{

}
