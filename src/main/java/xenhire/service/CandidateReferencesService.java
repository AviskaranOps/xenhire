package xenhire.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import xenhire.constants.AppConstants;
import xenhire.dto.ClientAssessmentDataResponseDTO;
import xenhire.dto.InterviewPassedCandidatesDTO;
import xenhire.model.CandidateReferenceQuestionsData;
import xenhire.model.CandidateReferenceRanking;
import xenhire.model.CandidateReferences;
import xenhire.model.ClientAssessmentData;
import xenhire.model.ClientAssessmentRanking;
import xenhire.model.ClientAssessmentVersion;
import xenhire.model.ClientOptions;
import xenhire.model.ClientQuestionnaire;
import xenhire.model.ClientReferencesOptions;
import xenhire.model.ClientReferencesQuestionnaire;
import xenhire.model.CompetencyScore;
import xenhire.model.EmailNotifications;
import xenhire.model.PillarScore;
import xenhire.model.User;
import xenhire.repository.CandidateJobStatusRepository;
import xenhire.repository.CandidateReferenceQuestionsDataRepository;
import xenhire.repository.CandidateReferenceRankingRepository;
import xenhire.repository.CandidateReferencesRepository;
import xenhire.repository.ClientReferencesOptionsRepository;
import xenhire.repository.ClientReferencesQuestionnaireRepository;
import xenhire.repository.CompetencyScoreRepository;
import xenhire.repository.EmailNotificationRepository;
import xenhire.repository.PillarScoreRepository;
import xenhire.repository.UserRepository;
import xenhire.request.ClientAssessmentRequest;
import xenhire.request.ClientReferenceQuestionsRequest;
import xenhire.request.ClientReferenceRequest;
import xenhire.response.ClientQuestionnaireResponse;
import xenhire.response.CommonResponse;

@Service
@Slf4j
public class CandidateReferencesService {
	
	@Autowired
	private CandidateJobStatusRepository candidateJobStatusRepository;
	
	@Autowired
	private CandidateReferencesRepository candidateReferencesRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	EmailNotificationRepository emailNotificationRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ClientReferencesQuestionnaireRepository referencesQuestionnaireRepository;
	
	@Autowired
	ClientReferencesOptionsRepository clientReferencesOptionsRepository;
	
	@Autowired
	CandidateReferenceQuestionsDataRepository candidateReferenceQuestionsDataRepository;
	
	@Autowired
	CompetencyScoreRepository competencyScoreRepository;
	
	@Autowired
	PillarScoreRepository pillarScoreRepository;
	
	@Autowired
	CandidateReferenceRankingRepository candidateReferenceRankingRepository;
	
	public ResponseEntity<Object> getAllCandidatsWithReferences(long clientId){
		List<InterviewPassedCandidatesDTO> dtoList = new ArrayList();
		String query = "select u.id, u.username, u.email, u.mobile, u.resume_name, cjs.passed_date, cjs.references_given, cjs.references_status from user u left join user_role ur on ur.user_id=u.id left join candidate_job_status cjs on cjs.candidate_id=u.id where ur.role_id=3 and cjs.client_id=? and cjs.interview_phase=?;";
		dtoList = jdbcTemplate.query(query, new Object[]{clientId, "Interview"}, new InterviewPassedCandidatesDTO());
		
		CommonResponse cr = CommonResponse.builder().data(dtoList).message(AppConstants.FOUND).result(AppConstants.SUCCESS).build();
		return new ResponseEntity<>(cr, HttpStatus.OK);
	}


	public ResponseEntity<Object> saveCandidateReferences(CandidateReferences req) {
		// TODO Auto-generated method stub
		CommonResponse cr = null;
		CandidateReferences candidateReference = candidateReferencesRepository.findByCandidateIdAndReferenceName(req.getCandidateId(), req.getReferenceName());
		if(candidateReference != null) {
			cr = CommonResponse.builder().data(null).message(AppConstants.RECORD_ALREADY_EXISTS).result(AppConstants.SUCCESS).build();
			return new ResponseEntity<>(cr, HttpStatus.OK);
		}
		candidateReferencesRepository.save(req);
		cr = CommonResponse.builder().data(req).message(AppConstants.SAVED).result(AppConstants.SUCCESS).build();
		return new ResponseEntity<>(cr, HttpStatus.OK);
	}
	
	
	public ResponseEntity<Object> getAllCandidateReferences(long clientId, long candidateId){
		List<CandidateReferences> referencesList = candidateReferencesRepository.findByClientIdAndCandidateId(clientId, candidateId);
		CommonResponse cr = CommonResponse.builder().data(referencesList).message(AppConstants.FOUND).result(AppConstants.SUCCESS).build();
		return new ResponseEntity<>(cr, HttpStatus.OK);
	}


	public ResponseEntity<Object> sendAllReferenceNotification(long clientId, long candidateId) throws MessagingException {
		// TODO Auto-generated method stub
		List<CandidateReferences> referencesList = candidateReferencesRepository.findByClientIdAndCandidateId(clientId, candidateId);
		for(CandidateReferences cr : referencesList) {
			String subject = "Reference Check";
			String body = "Please verify the link and give necessary inputs for your reference";
			EmailNotifications en = EmailNotifications.builder()
										.body(body)
										.createdAt(LocalDateTime.now())
										.notificationType("Reference")
										.sentBy("Xenspire")
										.sentDate(LocalDateTime.now())
										.status("sent")
										.subject(subject)
										.sentTo(cr.getEmail())
										.build();
			emailService.sendSimpleEmail(cr.getEmail(), subject, body);
			
			emailNotificationRepository.save(en);
		}
		CommonResponse cr = CommonResponse.builder().data(null).message(AppConstants.NOTIFICATION_SENT).result(AppConstants.SUCCESS).build();
		return new ResponseEntity<>(cr, HttpStatus.OK);
	}
	
	
	
	
	public ResponseEntity<Object> sendReferenceNotification(long referenceId) throws MessagingException {
		// TODO Auto-generated method stub
		Optional<CandidateReferences> reference = candidateReferencesRepository.findById(referenceId);
		if(reference.isEmpty()) return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
		
		String subject = "Reference Check";
		String body = "Please verify the link and give necessary inputs for your reference";
		EmailNotifications en = EmailNotifications.builder()
										.body(body)
										.createdAt(LocalDateTime.now())
										.notificationType("Reference")
										.sentBy("Xenspire")
										.sentDate(LocalDateTime.now())
										.status("sent")
										.subject(subject)
										.sentTo(reference.get().getEmail())
										.build();
		emailService.sendSimpleEmail(reference.get().getEmail(), subject, body);
			
		emailNotificationRepository.save(en);
		CommonResponse cr = CommonResponse.builder().data(null).message(AppConstants.NOTIFICATION_SENT).result(AppConstants.SUCCESS).build();
		return new ResponseEntity<>(cr, HttpStatus.OK);
	}


	public ResponseEntity<Object> sendAskReferenceNotification(long clientId, long candidateId) throws MessagingException {
		// TODO Auto-generated method stub
		Optional<User> userOpt = userRepository.findById(candidateId);
		String subject = "Reference Check";
		String body = "Please verify the link and give your references";
		EmailNotifications en = EmailNotifications.builder()
										.body(body)
										.createdAt(LocalDateTime.now())
										.notificationType("Reference")
										.sentBy("Xenspire")
										.sentDate(LocalDateTime.now())
										.status("sent")
										.subject(subject)
										.sentTo(userOpt.get().getEmail())
										.build();
		emailService.sendSimpleEmail(userOpt.get().getEmail(), subject, body);
			
		emailNotificationRepository.save(en);
		CommonResponse cr = CommonResponse.builder().data(null).message(AppConstants.NOTIFICATION_SENT).result(AppConstants.SUCCESS).build();
		return new ResponseEntity<>(cr, HttpStatus.OK);
	}
	
	
	public ResponseEntity<Object> getClientQuestionnaire() {
		// TODO Auto-generated method stub
		List<ClientReferencesQuestionnaire> cqList = referencesQuestionnaireRepository.findAll();
		//System.out.println(cqList);
		List<ClientQuestionnaireResponse> resp = new ArrayList();
		for(ClientReferencesQuestionnaire cq : cqList) {
			List<ClientReferencesOptions> coList = clientReferencesOptionsRepository.findByQuestionnaireNo(cq.getQuestionNo());
			List<String> options = coList.stream().map(ClientReferencesOptions::getOptionDesc).collect(Collectors.toList());
			ClientQuestionnaireResponse cqr = new ClientQuestionnaireResponse();
			cqr.setQuestionNo(cq.getQuestionNo());
			cqr.setQuestion(cq.getQuestion());
			cqr.setQuestionType(cq.getOptionType());
			cqr.setOptions(options);
			cqr.setOptionCategory(cq.getOptionCategory());
			resp.add(cqr);
		}
		return new ResponseEntity<>(resp, null, HttpStatus.OK);
	}


	public ResponseEntity<Object> saveClientReferencesQuestionnaire(ClientReferenceRequest req) {
		
		Optional<CandidateReferences> crOpt = candidateReferencesRepository.findById(req.getId());
		CandidateReferences cr = crOpt.get();
		cr.setWorkDuration(req.getWorkDuration());
		cr.setFormallyEvaluated(req.getFormallyEvaluated());
		cr.setCapacity(req.getCapacity());
		candidateReferencesRepository.save(cr);
		
		for(ClientReferenceQuestionsRequest reference : req.getReferenceQuestions()) {
			if(reference.getQuestionType().equals("RANKING")) {
				for(String opts : reference.getOptions()) {
					System.out.println("questionNo = " + reference.getQuestionNo() + " option = " + opts);
					ClientReferencesOptions co = clientReferencesOptionsRepository.findByQuestionnaireNoAndOptionDesc(reference.getQuestionNo(), opts);
					//ClientOptions co = clientOptionsRepository.findByOptionDesc(opts);
					CandidateReferenceQuestionsData cad = new CandidateReferenceQuestionsData();
					cad.setReferenceId(req.getId());
					cad.setQuestionNo(reference.getQuestionNo());
					cad.setOptionType(reference.getOptionCategory());
					cad.setOptionNo(co.getId());	
					candidateReferenceQuestionsDataRepository.save(cad);
				}
			}
			else {
				System.out.println("questionNo = " + reference.getQuestionNo() + " option = " + reference.getSelectedOption());
				//ClientOptions co = clientOptionsRepository.findByQuestionnaireNoAndOptionDesc(req.getQuestionNo(), req.getSelectedOption());
				ClientReferencesOptions co = clientReferencesOptionsRepository.findByOptionDesc(reference.getSelectedOption());
				CandidateReferenceQuestionsData cad = new CandidateReferenceQuestionsData();
				cad.setReferenceId(req.getId());
				cad.setQuestionNo(reference.getQuestionNo());
				cad.setOptionType(reference.getQuestionType());
				cad.setOptionNo(co.getId());	
				candidateReferenceQuestionsDataRepository.save(cad);
			}
			
			
		}
		
		extractClientAssessmentScoreAndSave(req.getId());
		
		return new ResponseEntity<>("data saved successfully", null, HttpStatus.CREATED);
	}
	
	
	public void extractClientAssessmentScoreAndSave(long referenceId) {
		
		System.out.println("reference ranking is called = " + referenceId);
		
		List<Map<String, Object>> dataList = candidateReferenceQuestionsDataRepository.getCandidateReferenceData(referenceId);
		List<ClientAssessmentDataResponseDTO> respList = new ArrayList();
		
		for(Map<String, Object> m : dataList) {
			ClientAssessmentDataResponseDTO data = new ClientAssessmentDataResponseDTO();
			data.setCompetencyId((long) m.get("competency_id"));
			data.setCorrectOption((int) m.get("correct_option"));
			data.setOptionDesc(m.get("option_desc").toString());
			data.setOptionLevel(m.get("option_level").toString());
			data.setOptionNo((long) m.get("option_no"));
			data.setOptionType(m.get("option_type").toString());
			data.setPillar_id((long) m.get("pillar_id"));
			data.setPillarName(m.get("pillarName").toString());
			data.setCompetencyName(m.get("competencyName").toString());
			respList.add(data);
		}
		
		System.out.println("client ranking is called = " + respList);
		
		
		List<ClientAssessmentDataResponseDTO> rankingPillar = respList.stream().filter(card -> card.getOptionType().equals("Ranking_Pillar")).collect(Collectors.toList());
														
		List<ClientAssessmentDataResponseDTO> rankingCompetency = respList.stream().filter(card -> card.getOptionType().equals("Ranking_Competency")).toList(); 
		List<ClientAssessmentDataResponseDTO> rating = respList.stream().filter(card -> card.getOptionType().equals("RATING")).toList(); 
		
		respList.forEach(System.out::println);
//		System.out.println(rankingCompetency);
//		System.out.println(rating);
		 int pillarRank = 1;
		 int competencyRank = 1;
		 String competency = "";
		for(ClientAssessmentDataResponseDTO data1 : rankingPillar) {
			System.out.println("pillar iteration " + pillarRank);
			if(data1.getPillarName().equals("Values") || data1.getPillarName().equals("Skills and Proficiency")) {
				CompetencyScore cs = competencyScoreRepository.findByCompetencyRank(competencyRank);
				PillarScore ps = pillarScoreRepository.findByPillarRank(pillarRank);
				
				CandidateReferenceRanking car = new CandidateReferenceRanking();
				car.setCompetency(data1.getCompetencyName());
				car.setCompetencyRank(competencyRank);
				car.setPillar(data1.getPillarName());
				car.setPillarRank(pillarRank);
				car.setCompetencyScore(cs.getCompetencyScore());
				car.setPillarScore(ps.getPillarScore());
				car.setMaxScore(cs.getCompetencyScore());
				car.setReferenceId(referenceId);
				car.setCreatedAt(LocalDateTime.now());
				car.setRating("Low");
				candidateReferenceRankingRepository.save(car);
				
				if(pillarRank == 4) { competencyRank ++; }
				else { competencyRank = competencyRank + 4; }
			}
			
			for(ClientAssessmentDataResponseDTO data2 : rankingCompetency) {
				
				if(data2.getPillarName().equals(data1.getPillarName())) {
					System.out.println(data2.getCompetencyName());
					for(ClientAssessmentDataResponseDTO data3 : rating) {
//						System.out.println(data2.getCompetencyName());
//						System.out.println(data3.getCompetencyName());
						if(data2.getCompetencyName().equals(data3.getCompetencyName())) {
							System.out.println(competencyRank);
							CompetencyScore cs = competencyScoreRepository.findByCompetencyRank(competencyRank);
							PillarScore ps = pillarScoreRepository.findByPillarRank(pillarRank);
							CandidateReferenceRanking car = new CandidateReferenceRanking();
							car.setCompetency(data3.getCompetencyName());
							car.setCompetencyRank(competencyRank);
							car.setPillar(data1.getPillarName());
							car.setPillarRank(pillarRank);
							car.setCompetencyScore(cs.getCompetencyScore());
							car.setPillarScore(ps.getPillarScore());
							car.setMaxScore(cs.getCompetencyScore());
							car.setReferenceId(referenceId);
							car.setCreatedAt(LocalDateTime.now());
							car.setRating(data3.getOptionLevel());
							CandidateReferenceRanking r = candidateReferenceRankingRepository.findByCompetency(data3.getCompetencyName());
							
							if(r == null) candidateReferenceRankingRepository.save(car);
						}
					
					}
					
					 if(pillarRank == 5) pillarRank ++;
					 competencyRank ++; 
				}
				
			}
			pillarRank ++;
		}
	}
	
	
	

	
}
