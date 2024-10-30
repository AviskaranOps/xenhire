package xenhire.service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.stereotype.Service;

import xenhire.model.CandidateDetails;
import xenhire.repository.CandidateDetailsRespository;

@Service
public class CandidateDetailsService {
	private final CandidateDetailsRespository candidateDetailsRespository;
	public CandidateDetailsService(CandidateDetailsRespository candidateDetailsRespository) {
		this.candidateDetailsRespository = candidateDetailsRespository;
	}

	public CandidateDetails saveCandidateDetailsForm(CandidateDetails candidateDetails, long candidateId) {
		
		Optional<CandidateDetails> candidateDetailsExisting = candidateDetailsRespository.findByCandidateId(candidateId);
		AtomicReference<CandidateDetails> candidateDetailsReference = new AtomicReference<CandidateDetails>();
		candidateDetailsExisting.ifPresentOrElse(
				data->{
					data.setCandidateId(candidateDetails.getCandidateId());
					data.setCandidateFullName(candidateDetails.getCandidateFullName());
					data.setNoticePeriod(candidateDetails.getNoticePeriod());
					data.setMobileNumber(candidateDetails.getMobileNumber());
					data.setProfilePicture(candidateDetails.getProfilePicture());
					data.setResume(candidateDetails.getResume());
					data.setSummary(candidateDetails.getSummary());
					data.setCandidateEducationDetails(candidateDetails.getCandidateEducationDetails());
					data.setCandidateProfessionalDetails(candidateDetails.getCandidateProfessionalDetails());
					data.setCandidateSkillDetails(candidateDetails.getCandidateSkillDetails());
					CandidateDetails updated = candidateDetailsRespository.save(data);
					candidateDetailsReference.set(updated);
				}
				, 
				()->{
					candidateDetails.setId(null);
					candidateDetails.getCandidateEducationDetails().forEach(data->data.setId(null));
					candidateDetails.getCandidateProfessionalDetails().forEach(data->data.setId(null));
					candidateDetails.getCandidateSkillDetails().forEach(data->data.setId(null));
					CandidateDetails saved = candidateDetailsRespository.save(candidateDetails);
					candidateDetailsReference.set(saved);
				});
		
		return candidateDetailsReference.get();
	}

	public CandidateDetails getCandidateDetailsForm(long candidateId) {
		Optional<CandidateDetails> candidateDetailsExisting = candidateDetailsRespository.findByCandidateId(candidateId);
		return candidateDetailsExisting.orElse(CandidateDetails.builder().build());
	}

}
