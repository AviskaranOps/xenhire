package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.ClientOptions;
import xenhire.model.ClientReferencesOptions;

public interface ClientReferencesOptionsRepository extends JpaRepository<ClientReferencesOptions, Long>{

	List<ClientReferencesOptions> findByQuestionnaireNo(int questionNo);

	ClientReferencesOptions findByQuestionnaireNoAndOptionDesc(int questionNo, String opts);

	ClientReferencesOptions findByOptionDesc(String selectedOption);

}
