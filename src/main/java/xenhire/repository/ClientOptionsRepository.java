package xenhire.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.ClientOptions;

public interface ClientOptionsRepository extends JpaRepository<ClientOptions, Long>{

	List<ClientOptions> findByQuestionnaireNo(int questionNo);

	ClientOptions findByQuestionnaireNoAndOptionDesc(int questionNo, String opts);

	ClientOptions findByOptionDesc(String opts);

}
