package xenhire.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xenhire.model.JobTemplate;

public interface JobTemplateRepository extends JpaRepository<JobTemplate, Long>{

	JobTemplate findByClientIdAndTemplateName(long clientId, String templateName);

	
	@Query(value="select ifnull(max(version_id), 0) from job_template where client_id=:clientId and job_id=:jobId", nativeQuery=true)
	int getMaxVersionId(long clientId, long jobId);


	List<JobTemplate> findByClientIdAndJobId(long clientId, long jobId);


	Page<JobTemplate> findAllByTemplate(boolean b, Pageable paging);

	//JobTemplate findByClientIdAndTitle(long clientId, String title);

}
