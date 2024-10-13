package xenhire.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import xenhire.model.CandidateInterviewFeedback;
import xenhire.model.ClientJobDetails;
import xenhire.model.CriticalScoring;
import xenhire.model.JobTemplate;
import xenhire.model.ScreeningQuestions;
import xenhire.model.User;
import xenhire.repository.CandidateInterviewFeedbackRepository;
import xenhire.repository.ClientJobDetailsRepository;
import xenhire.repository.CriticalScoringRepository;
import xenhire.repository.JobTemplateRepository;
import xenhire.repository.ScreeningQuestionsRepository;
import xenhire.repository.UserRepository;
import xenhire.request.CandidateInterviewFeedbackRequest;
import xenhire.request.ClientScreeningQuestionsRequest;
import xenhire.response.CandidateInfo;
import xenhire.response.CandidateJobInfo;
import xenhire.response.JobCandidateInfoResponse;

@Service
public class JobCandidateService {
	
	@Autowired
	JobTemplateRepository jobTemplateRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CriticalScoringRepository criticalScoringRepository;
	
	@Autowired
	CandidateInterviewFeedbackRepository candidateInterviewFeedbackRepository;
	
	@Autowired
	ClientJobDetailsRepository clientJobDetailsRepository;
	
	@Autowired
	ScreeningQuestionsRepository screeningQuestionsRepository; 
	
	public ResponseEntity<Object> getJobCandidateInfo(long clientId, long candidateId, long jobId){
		Optional<ClientJobDetails> jdOpt = clientJobDetailsRepository.findById(jobId);
		ClientJobDetails jd = jdOpt.get();
		Optional<JobTemplate> jtOpt = jobTemplateRepository.findById(jd.getJobDetailId());
		JobTemplate jt = jtOpt.get();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		Optional<User> uOpt = userRepository.findById(candidateId);
		User u = uOpt.get();
		CandidateJobInfo cji = new CandidateJobInfo();
		cji.setCode(jt.getJobCode());
		cji.setDate(formatter.format(jt.getCreatedAt()));
		cji.setDepartment(jt.getJobDepartment());
		cji.setFamily(jt.getJobFamily());
		cji.setLocation(jt.getJobLocation());
		cji.setStatus("New Requirement");
		cji.setTitle(jt.getJobTitle());
		
		CandidateInfo ci = new CandidateInfo();
		ci.setDate(formatter.format(LocalDateTime.now()));
		ci.setEducation("B.sc computer");
		ci.setEmail(u.getEmail());
		ci.setExperiance("25");
		ci.setId(u.getId());
		ci.setLocation("NY");
		ci.setName(u.getUsername());
		ci.setNo(u.getMobile());
		ci.setSkills(Arrays.asList("java","c","javascript","python"));
		ci.setStatus("interview");
		
		List<CandidateInterviewFeedback> cifList = candidateInterviewFeedbackRepository.findByCandidateIdAndJobId(candidateId, jobId);
		List<CandidateInterviewFeedbackRequest> cifResult = new ArrayList();
		for(CandidateInterviewFeedback req : cifList) {
			CandidateInterviewFeedbackRequest cif = new CandidateInterviewFeedbackRequest();
			cif.setCandidateId(candidateId);
			cif.setCreatedBy(req.getCreatedBy());
			cif.setCreatedDate(req.getCreatedDate());
			cif.setExperience(req.getExperience());
			cif.setExperienceWhy(req.getExperienceWhy());
			cif.setJobId(jobId);
			cif.setOverallRating(req.getOverallRating());
			cif.setRecommendation(req.getRecommendation());
			cif.setRecommendationWhy(req.getRecommendationWhy());
			cif.setId(req.getId());
			System.out.println(cif.getId());
			List<CriticalScoring> csList = criticalScoringRepository.findByInterviewFeedbackId(req.getId());
			cif.setCriticalScroing(csList);
			cifResult.add(cif);
		}
		JobCandidateInfoResponse cir = new JobCandidateInfoResponse();
		cir.setCandidateDetails(ci);
		cir.setInterviewFeedback(cifResult);
		cir.setJobDetails(cji);
		return new ResponseEntity<>(cir, null, HttpStatus.OK);
	}

	public ResponseEntity<Object> saveInterviewFeedback(long clientId, long jobId, long candidateId,  CandidateInterviewFeedbackRequest req) {
		// TODO Auto-generated method stub
		CandidateInterviewFeedback cif = new CandidateInterviewFeedback();
		if(req.getId() != 0) {
			cif.setId(req.getId());
		}
		cif.setCandidateId(candidateId);
		cif.setCreatedBy(req.getCreatedBy());
		cif.setCreatedDate(req.getCreatedDate());
		cif.setExperience(req.getExperience());
		cif.setExperienceWhy(req.getExperienceWhy());
		cif.setJobId(jobId);
		cif.setOverallRating(req.getOverallRating());
		cif.setRecommendation(req.getRecommendation());
		cif.setRecommendationWhy(req.getRecommendationWhy());
		candidateInterviewFeedbackRepository.save(cif);
		List<CriticalScoring> csList = new ArrayList();
		for(CriticalScoring cs : req.getCriticalScroing()) {
			cs.setInterviewFeedbackId(cif.getId());
			csList.add(cs);
			criticalScoringRepository.save(cs);
		}
//		req.setCriticalScroing(csList);
//		candidateInterviewFeedbackRepository.save(req);
		return new ResponseEntity<>("saved", null, HttpStatus.OK);
	}

	public ResponseEntity<Object> getJobScreeningQuestions(long clientId, long jobId) {
		// TODO Auto-generated method stub
		List<ScreeningQuestions> sqList = screeningQuestionsRepository.findAllByJobId(jobId);
		Optional<ClientJobDetails> cjdOpt = clientJobDetailsRepository.findById(jobId);
		ClientScreeningQuestionsRequest resp = new ClientScreeningQuestionsRequest();
		resp.setQuestions(sqList);
		resp.setProceed(cjdOpt.get().isScreeningQuestions());
		return new ResponseEntity<>(resp, null, HttpStatus.OK);
	}

	public ResponseEntity<Object> saveJobScreeningQuestions(long clientId, long jobId, ClientScreeningQuestionsRequest reqList) {
		// TODO Auto-generated method stub
		List<ScreeningQuestions> questionsList = screeningQuestionsRepository.findByJobId(jobId);
		int i=0;
		if(questionsList.size() == 0) questionsList = reqList.getQuestions(); 
		for(ScreeningQuestions sq : questionsList) {
			System.out.println(sq);
			sq.setJobId(jobId);
			sq.setClientId(clientId);
			sq.setQuestion(reqList.getQuestions().get(i).getQuestion());
			screeningQuestionsRepository.save(sq);
			i++;
		}
			Optional<ClientJobDetails> cjdOpt = clientJobDetailsRepository.findById(jobId);
			ClientJobDetails cjd = cjdOpt.get();
			cjd.setScreeningQuestions(reqList.isProceed());
			clientJobDetailsRepository.save(cjd);
		return new ResponseEntity<>("saved", null, HttpStatus.OK);
	}

}
