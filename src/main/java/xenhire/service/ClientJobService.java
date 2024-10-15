package xenhire.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import xenhire.model.CandidateDTPReportData;
import xenhire.model.CandidateDtpAccess;
import xenhire.model.Client;
import xenhire.model.ClientAssignedCandidates;
import xenhire.model.ClientIcpTemplate;
import xenhire.model.ClientJobDetails;
import xenhire.model.ClientOptions;
import xenhire.model.ClientPreferenceTemplate;
import xenhire.model.ClientSkills;
import xenhire.model.ClientTeamTemplate;
import xenhire.model.ClientValueAssessmentData;
import xenhire.model.JobAssignedCandidates;
import xenhire.model.JobTemplate;
import xenhire.model.Role;
import xenhire.model.ScreeningQuestions;
import xenhire.model.User;
import xenhire.repository.CandidateDTPReportDataRepository;
import xenhire.repository.CandidateDtpAccessRepository;
import xenhire.repository.ClientAssessmentRankingRepository;
import xenhire.repository.ClientAssignedCandidatesRepository;
import xenhire.repository.ClientIcpTemplateRepository;
import xenhire.repository.ClientJobDetailsRepository;
import xenhire.repository.ClientOptionsRepository;
import xenhire.repository.ClientPreferenceTemplateRepository;
import xenhire.repository.ClientQuestionnaireRepository;
import xenhire.repository.ClientRepository;
import xenhire.repository.ClientSkillsRepository;
import xenhire.repository.ClientTeamTemplateRepository;
import xenhire.repository.ClientValuesDataRepository;
import xenhire.repository.ClientValuesRepository;
import xenhire.repository.JobAssignedCandidatesRepository;
import xenhire.repository.JobPreferenceTemplateRepository;
import xenhire.repository.JobTemplateRepository;
import xenhire.repository.RoleRepository;
import xenhire.repository.ScreeningQuestionsRepository;
import xenhire.repository.UserRepository;
import xenhire.request.AssessmentBatchAddCandidateRequest;
import xenhire.request.ClientAssessmentRequest;
import xenhire.request.ClientIcpRequest;
import xenhire.request.ClientScreeningQuestionsRequest;
import xenhire.request.ClientValuesRequest;
import xenhire.request.ClinetPreferenceTemplateRequest;
import xenhire.request.JobDetailsRequest;
import xenhire.request.JobStatusRequest;
import xenhire.request.JobTitleRequest;
import xenhire.request.ValuesRating;
import xenhire.response.BatchCandidateResponse;
import xenhire.response.CandidateSpectrumResults;
import xenhire.response.ClientAssignedCandidatesResponse;
import xenhire.response.ClientJobInfoResponse;
import xenhire.response.ExternalStaffingResponse;
import xenhire.response.JobCandidatesResponse;
import xenhire.response.JobDescriptionResponse;
import xenhire.response.JobDetailVersions;
import xenhire.response.JobDetailsResponse;
import xenhire.response.PaginatedResponse;

@Service
public class ClientJobService {
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy hh:mm a", Locale.ENGLISH);
	DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
	
	@Autowired
	ClientJobDetailsRepository clientJobDetailsRepository;
	
	@Autowired
	JobTemplateRepository jobTemplateRepository;
	
	@Autowired
	ClientTeamTemplateRepository clientTeamTemplateRepository;
	
	@Autowired
	JobPreferenceTemplateRepository jobPreferenceTemplateRepository;
	
	@Autowired
	UtilityService utilityService;
	
	@Autowired
	ClientValuesDataRepository clientValuesDataRepository;
	
	@Autowired
	ClientValuesRepository clientValuesRepository;
	
	@Autowired
	ClientPreferenceTemplateRepository clientPreferenceTemplateRepository;
	
	@Autowired
	ClientSkillsRepository clientSkillsRepository;
	
	@Autowired
	ClientQuestionnaireRepository clientQuestionnaireRepository;
	
	@Autowired
	ClientOptionsRepository clientOptionsRepository;
	
	@Autowired
	ClientIcpTemplateRepository clientIcpTemplateRepository;
	
	@Autowired
	ClientAssessmentRankingRepository clientAssessmentRankingRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	CandidateDtpAccessRepository candidateDtpAccessRepository;

	
	@Autowired
	JobAssignedCandidatesRepository jobAssignedCandidatesRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	CandidateDTPReportDataRepository candidateDTPReportDataRepository;
	
	@Autowired
	ClientTemplateService clientTemplateService;
	
	@Autowired
	ScreeningQuestionsRepository screeningQuestionsRepository;
	
	@Autowired
	ClientAssignedCandidatesRepository clientAssignedCandidatesRepository;
	
//	private final BCryptPasswordEncoder bCryptPasswordEncoder; 
//	
//	
//	@Autowired
//	ClientJobService(BCryptPasswordEncoder bCryptPasswordEncode) {
//		this.bCryptPasswordEncoder = bCryptPasswordEncode;
//	}
	
	public ResponseEntity<Object> getClientJobDetails(long clientId, long jobId){
		ClientJobDetails job = new ClientJobDetails();
		Optional<ClientJobDetails> jobOpt = clientJobDetailsRepository.findById(jobId);	
		Optional<User> uOpt = userRepository.findById(clientId);
		Optional<Client> c = clientRepository.findById(uOpt.get().getClientId());
		System.out.println(jobOpt.get());
		if(jobOpt.isPresent()) {
			job = jobOpt.get();
			ClientJobInfoResponse resp = new ClientJobInfoResponse();
			resp.setClientId(clientId);
			resp.setId(job.getId());
			resp.setIcp(job.isIcp());
			resp.setIcpId(job.getIcpId());
			resp.setJobDetail(job.isJobDetail());
			resp.setJobDetailId(job.getJobDetailId());
			resp.setTeam(job.isTeam());
			resp.setTeamId(job.getTeamId());
			resp.setScreening(job.isScreening());
			resp.setAssessment(job.isAssessment());
			resp.setJobTitle(job.getJobTitle());
			resp.setWorkValues(job.isWorkValues());
			resp.setWorkValuesId(job.getWorkValuesId());
			resp.setCompanyName(c.get().getCompanyName());
			resp.setScreeningQuestions(job.isScreeningQuestions());
			resp.setAssessmentAssigned(job.isAssessmentAssigned());
			resp.setSourcingHelp(job.isSourcingHelp());
			resp.setOnboardingHelp(job.isOnboardingHelp());
			resp.setFullServiceStaffingHelp(job.isFullServiceStaffingHelp());
			resp.setPublishFeature(job.isPublishFeature());
			resp.setJobStatus(job.getJobStatus());
			resp.setJobSubStatus(job.getJobSubStatus());;
			return new ResponseEntity<>(resp, null, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(job, null, HttpStatus.OK);
			
		}
	}
	
	public ResponseEntity<Object> getJobDetailVersions(long clientId, long jobId){
		List<JobTemplate> jtList = jobTemplateRepository.findByClientIdAndJobId(clientId, jobId);
		List<JobDetailsRequest> resp = new ArrayList();
		for(JobTemplate jt : jtList) {
			JobDetailsRequest jtr = new JobDetailsRequest();
			List<String> certifications = new ArrayList();
			for(String cert : jt.getCertifications().split(",")) {
				certifications.add(cert);
			}
			List<String> softwares = new ArrayList();
			for(String cert : jt.getSoftwares().split(",")) {
				softwares.add(cert);
			}
			jtr.setVersionId(jt.getVersionId());
			jtr.setJobId(jobId);
			jtr.setCreatedBy(jt.getCreatedBy());
			jtr.setBenefits(jt.getBenefits());
			jtr.setCertifications(certifications);
			jtr.setCompanyInfo(jt.getCompanyInfo());
			jtr.setDifferentAcademic(jt.getDifferentAcademic());
			jtr.setEnvision(jt.getEnvision());
			jtr.setEqualEmployeeOpportunity(jt.getEqualEmployeeOpportunity());
			jtr.setIndustryKnowledge(jt.getIndustryKnowledge());
			jtr.setJobCode(jt.getJobCode());
			jtr.setWorkSetting(jt.getWorkSetting());
			jtr.setVisa(jt.getVisa());
			jtr.setTemplateTag(jt.getTemplateTag());
			jtr.setTemplateName(jt.getTemplateName());
			jtr.setTemplateDescription(jt.getTemplateDescription());
			jtr.setSpecifyIndustryExp(jt.getSpecifyIndustryExp());
			jtr.setSpecificIndustryExperience(jt.getSpecificIndustryExperience());
			jtr.setSoftwares(softwares);
			jtr.setSalary(jt.getSalary());
			jtr.setRoleType(jt.getRoleType());
			jtr.setRoleTravel(jt.getRoleTravel());
			jtr.setRoleTimings(jt.getRoleTimings());
			jtr.setResponsibilities(jt.getResponsibilities());
			jtr.setRequireRegulatory(jt.getRequireRegulatory());
			jtr.setPositionSummry(jt.getPositionSummry());
			jtr.setMinimumLevelQualification(jt.getMinimumLevelQualification());
			jtr.setJobTitle(jt.getJobTitle());
			jtr.setJobLocation(jt.getJobLocation());
			jtr.setJobFamily(jt.getJobFamily());
			jtr.setJobDepartment(jt.getJobDepartment());
			jtr.setJobDescription(jt.getJobDescription());
			resp.add(jtr);
		}
		
		return new ResponseEntity<>(resp, null, HttpStatus.OK);
	}

	public ResponseEntity<Object> saveJobTemplate(long clientId, JobDetailsRequest req, long jobId, int template){
			ClientJobDetails job = new ClientJobDetails();
			Optional<ClientJobDetails> jobOpt = clientJobDetailsRepository.findById(jobId);
			if(jobOpt.isPresent()) {
				job = jobOpt.get();
			}
			job.setJobDetail(true);
			job.setClientId(clientId);
			clientJobDetailsRepository.save(job);
			int versionNo = jobTemplateRepository.getMaxVersionId(clientId, jobId);
			JobTemplate jt = new JobTemplate();
//			if(req.getId() != 0) {
//				Optional<JobTemplate> jtOpt = jobTemplateRepository.findById(req.getId());
//				jt = jtOpt.get();
//			}
			String username = utilityService.getLoggedInUsername(clientId);
			System.out.println(username);
			String certifications = "";
			for(String cert : req.getCertifications()) {
				certifications = certifications + cert + ",";
			}
			String softwares = "";
			for(String cert : req.getSoftwares()) {
				softwares = softwares + cert + ",";
			}
			jt.setClientId(clientId);
			jt.setJobId(job.getId());
			jt.setTemplate(template == 0 ? false : true);
			jt.setVersionId(versionNo + 1);
			jt.setCreatedBy(username);
			jt.setBenefits(req.getBenefits());
			jt.setCertifications(certifications);
			jt.setCompanyInfo(req.getCompanyInfo());
			jt.setDifferentAcademic(req.getDifferentAcademic());
			jt.setEnvision(req.getEnvision());
			jt.setEqualEmployeeOpportunity(req.getEqualEmployeeOpportunity());
			jt.setIndustryKnowledge(req.getIndustryKnowledge());
			jt.setJobCode(req.getJobCode());
			jt.setWorkSetting(req.getWorkSetting());
			jt.setVisa(req.getVisa());
			jt.setTemplateTag(req.getTemplateTag());
			jt.setTemplateName(req.getTemplateName());
			jt.setTemplateDescription(req.getTemplateDescription());
			jt.setSpecifyIndustryExp(req.getSpecifyIndustryExp());
			jt.setSpecificIndustryExperience(req.getSpecificIndustryExperience());
			jt.setSoftwares(softwares);
			jt.setSalary(req.getSalary());
			jt.setRoleType(req.getRoleType());
			jt.setRoleTravel(req.getRoleTravel());
			jt.setRoleTimings(req.getRoleTimings());
			jt.setResponsibilities(req.getResponsibilities());
			jt.setRequireRegulatory(req.getRequireRegulatory());
			jt.setPositionSummry(req.getPositionSummry());
			jt.setMinimumLevelQualification(req.getMinimumLevelQualification());
			jt.setJobTitle(req.getJobTitle());
			jt.setJobLocation(req.getJobLocation());
			jt.setJobFamily(req.getJobFamily());
			jt.setJobDepartment(req.getJobDepartment());
			jt.setJobDescription(req.getJobDescription());
			
			
			jobTemplateRepository.save(jt);
			
			job.setJobDetailId(jt.getId());
			
			clientJobDetailsRepository.save(job);
			
			Map m = new HashMap();
			m.put("jobId", job.getId());
			return new ResponseEntity<>( m, null, HttpStatus.OK);
		}
	
	
		public ResponseEntity<Object> saveClientValues(long clientId, long jobId, ClientValuesRequest reqList, int template){
			ClientValueAssessmentData cvd = new ClientValueAssessmentData();
			ClientJobDetails job = new ClientJobDetails();
			Optional<ClientJobDetails> jobOpt = clientJobDetailsRepository.findById(jobId);	
			if(jobOpt.isPresent()) {
				job = jobOpt.get();
			}
			int maxId = clientValuesDataRepository.getMaxId();
			String username = utilityService.getLoggedInUsername(clientId);
			for(ValuesRating req : reqList.getRatingList()) {
					if(req.getValue().equals("Stimulation")) cvd.setStimulation(req.getRating());
					if(req.getValue().equals("Self-Direction")) cvd.setSelfDirection(req.getRating());
					if(req.getValue().equals("Hedonism")) cvd.setHedonism(req.getRating());
					if(req.getValue().equals("Achievement")) cvd.setAchievement(req.getRating());
					if(req.getValue().equals("Power")) cvd.setPower(req.getRating());
					if(req.getValue().equals("Security")) cvd.setSecurity(req.getRating());
					if(req.getValue().equals("Conformity")) cvd.setConformity(req.getRating());
					if(req.getValue().equals("Tradition")) cvd.setTradition(req.getRating());
					if(req.getValue().equals("Benevolence")) cvd.setBenevolence(req.getRating());
					if(req.getValue().equals("Universalism")) cvd.setUniversalism(req.getRating());
			}
			cvd.setCreatedBy(username);
			cvd.setClientId(clientId);
			cvd.setTemplateName(reqList.getTemplateName());
			cvd.setTemplateTag(reqList.getTemplateTag());
			cvd.setTemplateDescription(reqList.getTemplateDescription());
			cvd.setTemplate(template == 0 ? false : true);
			clientValuesDataRepository.save(cvd);
			
			job.setWorkValues(true);
			job.setWorkValuesId(cvd.getId());
			clientJobDetailsRepository.save(job);
			
			return new ResponseEntity<>("saved", null, HttpStatus.CREATED);
			
		}
		
		
		public ResponseEntity<Object> assignClientValues(long clientId, long jobId, long valuesId){
			Optional<ClientJobDetails> jobOpt = clientJobDetailsRepository.findById(jobId);	
			ClientJobDetails job = jobOpt.get();
			job.setWorkValuesId(valuesId);
			job.setWorkValues(true);
			clientJobDetailsRepository.save(job);
			
			return new ResponseEntity<>("saved", null, HttpStatus.CREATED);
		}
		
		
		public ResponseEntity<Object> saveTeamTemplate(long clientId, long jobId, ClientTeamTemplate req, int template){
			System.out.println(req);
			String username = utilityService.getLoggedInUsername(clientId);
			int maxId = clientTeamTemplateRepository.getMaxId();
			req.setClientId(clientId);
			req.setCreatedBy(username);
			req.setTemplate(template == 0 ? false : true);
			clientTeamTemplateRepository.save(req);
			ClientJobDetails job = new ClientJobDetails();
			Optional<ClientJobDetails> jobOpt = clientJobDetailsRepository.findById(jobId);	
			if(jobOpt.isPresent()) {
				job = jobOpt.get();
			}
			job.setTeam(true);
			job.setTeamId(req.getId());
			clientJobDetailsRepository.save(job);
			return new ResponseEntity<>("saved", null, HttpStatus.OK);
		}
		
		
		public ResponseEntity<Object> savePreferenceTemplate(long clientId, long jobId, ClinetPreferenceTemplateRequest req) {
			ClientPreferenceTemplate cpt = null;
			int maxId = clientPreferenceTemplateRepository.getMaxId();
			String username = utilityService.getLoggedInUsername(clientId);
			if(req.getId() != 0) {	
				Optional<ClientPreferenceTemplate> cptOpt = clientPreferenceTemplateRepository.findById(req.getId());
				cpt = cptOpt.get();
			}
			else {
				cpt = new ClientPreferenceTemplate();
				cpt.setClientId(clientId);
				cpt.setTemplateName("preferences"+ (maxId + 1));
			}
			String certifications = "";
			for(Map<String, String> cert : req.getCertificationsOrLicenses()) {
				certifications = certifications + cert.get("certificate").toString() + ",";
			}
			String softwares = "";
			for(Map<String, String> cert : req.getToolsOrSoftwaresetToolsOrSoftware()) {
				softwares = softwares + cert.get("tools").toString() + ",";
			}
			cpt.setCertifications(certifications.substring(0, certifications.length() -1));
			cpt.setCompensation(req.getCompensationOffered());
			cpt.setCrossFunctinality(req.getExperianceInIndustry());
			cpt.setDifferentAcademic(req.getDifferentAcademic());
			cpt.setEnvision(req.getSuccessThreeyear());
			cpt.setIndustrySpecificExperience(req.getSpecifyIndusrtyExp());
			cpt.setIndustrySpecificIndepthKnowledge(req.getDepthKnowledge());
			cpt.setJobDescription(req.getJobDescription());
			cpt.setMinQualification(req.getMinimumLevelQualification());
			cpt.setRelocation(req.getRelocation());
			cpt.setRelocationBudget(req.getRelocationBudget());
			cpt.setRequireAcademicQualification(req.getRequireAcademicQualification());
			cpt.setResponsesibilities(req.getScopOfRole());
			cpt.setRoleLocation(req.getLocationRole());
			cpt.setRoletimings(req.getTimeOfRole());
			cpt.setRoleTravel(req.getTravelRole());
			cpt.setRoleType(req.getTypeOfRoles());
			cpt.setSoftwares(softwares.substring(0, softwares.length() - 1));
			cpt.setVisa(req.getVisa());
			cpt.setWorkSetting(req.getWorkSetting());
			cpt.setCreatedBy(username);
			cpt.setTemplateName(req.getTemplateName());
			cpt.setTemplateTag(req.getTemplateTag());
			cpt.setTemplateDescription(req.getTemplateDescription());
			clientPreferenceTemplateRepository.save(cpt);
			
			
			for(Map<String,String> skill : req.getPrimarySkills()) {
				ClientSkills cs = new ClientSkills();
				cs.setClientId(clientId);
				cs.setClientPreferencesId(cpt.getId());
				cs.setSkill(skill.get("skill"));
				cs.setSkillLevel(skill.get("expertise"));
				cs.setSkillType("primary");
				clientSkillsRepository.save(cs);
			}
			for(Map<String,String> skill : req.getSecoundrySkills()) {
				ClientSkills cs = new ClientSkills();
				cs.setClientId(clientId);
				cs.setClientPreferencesId(cpt.getId());
				cs.setSkill(skill.get("skill"));
				cs.setSkillLevel(skill.get("expertise"));
				cs.setSkillType("secondary");
				clientSkillsRepository.save(cs);
			}
			
			ClientJobDetails job = new ClientJobDetails();
			Optional<ClientJobDetails> jobOpt = clientJobDetailsRepository.findById(jobId);	
			if(jobOpt.isPresent()) {
				job = jobOpt.get();
			}
//			job.setPreference(true);
//			job.setPreferenceId(cpt.getId());
			clientJobDetailsRepository.save(job);
			
			return new ResponseEntity<>("saved", null, HttpStatus.OK);
		}
		
		
		public ResponseEntity<Object> saveClientICPTempalte(ClientIcpRequest reqList, long clientId, long jobId, int template) {
			int maxTemplateNo = clientIcpTemplateRepository.getMaxTemplateNo();
			String username = utilityService.getLoggedInUsername(clientId);
			for(ClientAssessmentRequest req : reqList.getQuestionList()) {
				if(req.getQuestionType().equals("RANKING")) {
					for(String opts : req.getOptions()) {
						System.out.println("questionNo = " + req.getQuestionNo() + " option = " + opts);
						ClientOptions co = clientOptionsRepository.findByQuestionnaireNoAndOptionDesc(req.getQuestionNo(), opts);
						ClientIcpTemplate cad = new ClientIcpTemplate();
						cad.setClientId(clientId);
						cad.setTemplateNo(maxTemplateNo + 1);
						cad.setTemplate(template == 0 ? false : true);
						cad.setCreatedBy(username);
						cad.setQuestionNo(req.getQuestionNo());
						cad.setOptionType(req.getOptionCategory());
						cad.setOptionNo(co.getId());	
						cad.setQuestion(req.getQuestion());
						cad.setOptionDesc(opts);
						cad.setTemplateName(reqList.getTemplateName());
						cad.setTemplateDescription(reqList.getTemplateDescription());
						cad.setTemplateTag(reqList.getTemplateTag());
						clientIcpTemplateRepository.save(cad);
					}
				}
				else {
					System.out.println("questionNo = " + req.getQuestionNo() + " option = " + req.getSelectedOption());
					//ClientOptions co = clientOptionsRepository.findByQuestionnaireNoAndOptionDesc(req.getQuestionNo(), req.getSelectedOption());
					ClientOptions co = clientOptionsRepository.findByOptionDesc(req.getSelectedOption());
					ClientIcpTemplate cad = new ClientIcpTemplate();
					cad.setClientId(clientId);
					cad.setTemplateNo(maxTemplateNo + 1);
					cad.setTemplate(template == 0 ? false : true);
					cad.setCreatedBy(username);
					cad.setQuestionNo(req.getQuestionNo());
					cad.setOptionType(req.getOptionCategory());
					cad.setOptionNo(co.getId());
					cad.setQuestion(req.getQuestion());
					cad.setOptionDesc(req.getSelectedOption());
					cad.setTemplateName(reqList.getTemplateName());
					cad.setTemplateDescription(reqList.getTemplateDescription());
					cad.setTemplateTag(reqList.getTemplateTag());
					clientIcpTemplateRepository.save(cad);
				}
				
				
			}
			Optional<ClientJobDetails> jobOpt = clientJobDetailsRepository.findById(jobId);	
			ClientJobDetails job = jobOpt.get();
			job.setIcp(true);
			job.setIcpId(maxTemplateNo + 1);
			clientJobDetailsRepository.save(job);
			utilityService.extractClientAssessmentScoreAndSave(clientId, maxTemplateNo + 1, template);
			
			return new ResponseEntity<>("data saved successfully", null, HttpStatus.CREATED);
		}
		
		
		
//		public ResponseEntity<Object> getAllJobsofClient(long clientId, int pageNo, int pageSize){
//			pageNo = pageNo - 1;
//			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
//			Page<ClientJobDetails> pagedResult = clientJobDetailsRepository.findAllByClientId(clientId, paging);
//			List<JobDetailsResponse> respList = new ArrayList();
//			for(ClientJobDetails details : pagedResult.getContent()) {
//				Optional<JobTemplate> jtOpt = jobTemplateRepository.findById(details.getJobDetailId());
//				JobTemplate jt = jtOpt.get();
//				Optional<ClientPreferenceTemplate> preOpt = clientPreferenceTemplateRepository.findById(details.getPreferenceId());
//				ClientPreferenceTemplate preference = preOpt.get();
//				Optional<User> userOpt = userRepository.findById(clientId);
//				Optional<Client> cliOpt = clientRepository.findById(userOpt.get().getClientId());
//				
//				List<ClientSkills> skills = clientSkillsRepository.findByClientPreferencesId(preference.getId());
//				List<String> skillsList = new ArrayList();
//				for(ClientSkills skill : skills) {
//					skillsList.add(skill.getSkill());
//				}
//				List<String> responsibility = new ArrayList();
//				responsibility.add(preference.getMinQualification() + " " + preference.getRequireAcademicQualification());
//				responsibility.add("proficient in " + preference.getSoftwares());
//				responsibility.add("proficient in " + preference.getCertifications());
//				List<String> typeOfHire = new ArrayList();
//				typeOfHire.add(preference.getWorkSetting());
//				JobDetailsResponse jdr = new JobDetailsResponse();
//				jdr.setId(details.getId());
//				jdr.setApplicants(10);
//				jdr.setCompanyName(cliOpt.get().getCompanyName());
//				jdr.setDescription(preference.getJobDescription());
//				jdr.setHiringManager(cliOpt.get().getName());
//				jdr.setCompensation(preference.getCompensation());
//				jdr.setImage("https://picsum.photos/200");
//				jdr.setJobName(jt.getTitle());
//				jdr.setJobProgress(getJobProgress(details));
//				jdr.setLocation(jt.getLocation());
//				jdr.setPostedTime(getJobElapsedTime(details.getCreatedAt()));
//				jdr.setRequiredSkills(skillsList);
//				jdr.setRequirements(responsibility);
//				jdr.setResponsibility(preference.getResponsesibilities().split("'"));
//				jdr.setTypeOfHire(typeOfHire);
//				jdr.setExperience(7);
//				jdr.setNoticePeriod("3 months");
//				respList.add(jdr);
//			}
//			PaginatedResponse paginationResponse = PaginatedResponse.builder().data(respList)
//					.pageNo(pagedResult.getNumber() + 1).pageSize(pageSize)
//					.totalCount(pagedResult.getTotalElements()).message("found").result("success")
//					.build();
//			
//			return new ResponseEntity<>(paginationResponse, null, HttpStatus.OK);
//		}
		
		
		public ResponseEntity<Object> getAllJobsofClient(long clientId, int pageNo, int pageSize){
			pageNo = pageNo - 1;
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
			Page<ClientJobDetails> pagedResult = clientJobDetailsRepository.findAllByClientId(clientId, paging);
			List<JobDetailsResponse> respList = new ArrayList();
			Optional<User> userOpt = userRepository.findById(clientId);
			Optional<Client> cliOpt = clientRepository.findById(userOpt.get().getClientId());
			for(ClientJobDetails details : pagedResult.getContent()) {
				Optional<JobTemplate> jtOpt = jobTemplateRepository.findById(details.getJobDetailId());
				JobTemplate jt = null;
				if(jtOpt.isPresent()) jt = jtOpt.get();
				List<JobAssignedCandidates> candidatesList = jobAssignedCandidatesRepository.findAllByJobId(details.getId());
				long newlyCount = candidatesList.stream()
			            .filter(obj -> ChronoUnit.DAYS.between(obj.getCreatedAt(), LocalDateTime.now()) < 2)
			            .collect(Collectors.counting());
				long activeCount = candidatesList.stream()
			            .filter(obj -> obj.isActive())
			            .collect(Collectors.counting());
				long hiredCount = candidatesList.stream()
			            .filter(obj -> obj.isHired())
			            .collect(Collectors.counting()); 
				JobDetailsResponse resp = new JobDetailsResponse();
				resp.setId(details.getId());
				resp.setApplicants(pageSize);
				resp.setCompanyName(cliOpt.isPresent() ? cliOpt.get().getCompanyName() : "");
				resp.setCompensation(jt == null ? 0 : jt.getSalary());
				resp.setHiringManager(cliOpt.isPresent() ? cliOpt.get().getName() : "");
				resp.setImage("https://picsum.photos/200");
				resp.setJobName(details.getJobTitle());
				resp.setJobProgress(getJobProgress(details));
				resp.setLocation(jt == null ? "NO Location" : jt.getJobLocation());
				resp.setJobDepartment(jt == null ? "not defined" : jt.getJobDepartment());
				resp.setPostedTime(getJobElapsedTime(details.getCreatedAt()));
				resp.setJobStatus(details.getJobStatus());
				resp.setJobSubStatus(details.getJobSubStatus());	
				resp.setTotal(candidatesList.size());
				resp.setNewly(newlyCount);
				resp.setActive(activeCount);
				resp.setHired(hiredCount);
				resp.setCreatedDate(formatter1.format(details.getCreatedAt()));
				resp.setJobType(jt == null ? "not defined" : jt.getJobFamily());
				resp.setNewJob(ChronoUnit.DAYS.between(details.getCreatedAt(), LocalDateTime.now()) < 2);
				List<String> hireType = new ArrayList();
				hireType.add(jt == null ? "not defined" : jt.getRoleType());
				resp.setTypeOfHire(hireType);
				respList.add(resp);
			}
			PaginatedResponse paginationResponse = PaginatedResponse.builder().data(respList)
					.pageNo(pagedResult.getNumber() + 1).pageSize(pageSize)
					.totalCount(pagedResult.getTotalElements()).message("found").result("success")
					.build();
			
			return new ResponseEntity<>(paginationResponse, null, HttpStatus.OK);

		}

		public String getJobElapsedTime(LocalDateTime createdAt) {
			// TODO Auto-generated method stub
			LocalDateTime now = LocalDateTime.now();
	        
	        // Step 3: Calculate the difference

	        // Calculate the difference in hours
	        long hoursDifference = Duration.between(createdAt, now).toHours();

	        // Calculate the difference in days
	        long daysDifference = Duration.between(createdAt, now).toDays();

	        // Calculate the difference in months and years using Period
	        Period periodDifference = Period.between(createdAt.toLocalDate(), now.toLocalDate());
	        int yearsDifference = periodDifference.getYears();
	        int monthsDifference = periodDifference.getMonths();
	        int totalDaysDifference = periodDifference.getDays();
	        String resp = hoursDifference + " hours";
	        if(hoursDifference > 23 && hoursDifference < 720) {
	        	resp = totalDaysDifference + " days";
	        }
			if(totalDaysDifference > 30) {
				resp = monthsDifference + " days";
			}
			if(monthsDifference > 12) {
				resp = yearsDifference + " years";
			}
			
			return resp;
		}

		int getJobProgress(ClientJobDetails details) {
			// TODO Auto-generated method stub
			int progress = 0;
			if(details.isJobDetail()) {
				progress = progress + 25;
			}
			if(details.isIcp()) {
				progress = progress + 25;
			}
			if(details.isWorkValues()) {
				progress = progress + 25;
			}
			if(details.isTeam()) {
				progress = progress + 25;
			}
			
			return progress;
		}
		
		
		
		public ResponseEntity<Object> showCandidates(long clientId, long jobId, int pageNo, int pageSize){
			pageNo = pageNo - 1;
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
//			Page<User> pagedResult = userRepository.getCandidates(paging);
			List<JobAssignedCandidates> candidatesList = jobAssignedCandidatesRepository.findAllByJobId(jobId);
			List<JobCandidatesResponse> respList = new ArrayList();
			Optional<ClientJobDetails> jobOpt = clientJobDetailsRepository.findById(jobId);
			Optional<User> uOpt = userRepository.findById(clientId);
			Optional<Client> cliOpt = clientRepository.findById(uOpt.get().getClientId());
			for(JobAssignedCandidates jac : candidatesList) {
				CandidateDTPReportData cdrd = candidateDTPReportDataRepository.getRecentRecord(jac.getCandidateId());
				String dtpStatus = "Not Completed";
				if(cdrd != null) {
					if(cdrd.isAssessment() && cdrd.isPersonalInfo() && cdrd.isPreferences() && cdrd.isValueAssessment()) {
						dtpStatus = "Completed";
					}
				}
				List<CandidateDtpAccess> dtpList = candidateDtpAccessRepository.findByClientIdAndCandidateIdOrderByCreatedAtDesc(cliOpt.get().getId(), jac.getCandidateId());
				CandidateDtpAccess dtp = dtpList.get(0);				
				Optional<User> userOpt = userRepository.findById(jac.getCandidateId());
				User u = userOpt.get();
				boolean dtpAccess = false;
				String notify = "Notify";
				if(dtp != null && dtp.isAuthorized() == true) {
					dtpAccess = true;
					notify = "Show DTP";
				}
//				if(dtp == null )dtpStatus = "Request Access";
//				if(dtp != null) dtpStatus = dtp.getDtpStatus();
				JobCandidatesResponse resp = new JobCandidatesResponse();;
				resp.setId(jac.getId());
				resp.setCandidateId(u.getId());
				resp.setRole(u.getTitle());
				resp.setClientId(cliOpt.get().getId());
				resp.setDtpAccess(dtpAccess);
				resp.setAlignmentDate(jac.getCreatedAt().format(formatter));
				resp.setRequestedData(jac.getCreatedAt().format(formatter));;
				resp.setUsername(u.getUsername());
				resp.setNotify(notify);
				resp.setDtpStatus(dtpStatus);
				resp.setDtpReportId(dtp.getDtpReportId());
				resp.setApplicationStatus(jac.getApplicationStatus());
				resp.setApplicationSubStatus(jac.getApplicationSubStatus());
				resp.setProfileStatus("20");
				resp.setCompletedStatus("60");	
				resp.setCreatedTime(formatter1.format(jac.getCreatedAt()));
				resp.setApplicationSource(jac.getApplicationSource());
				resp.setRecommendationStatus(jac.getRecommendationStatus());
				resp.setRecommendationRank(jac.getRecommendationRank());
				resp.setResume(u.getResumeName());
				int score = utilityService.getJobMatchingScores(clientId, jobOpt.get().getIcpId(), jobOpt.get().getWorkValuesId() , u.getId());
				System.out.println("result score = " + score);
				String matchingScore = "0";
				if(score != 0) matchingScore = score + "";
				resp.setMatchingScore(matchingScore);
				respList.add(resp);
			}
			
			return new ResponseEntity<>(respList, null, HttpStatus.OK);
		}

		public ResponseEntity<Object> assignCandidatesToJob(long clientId, long jobId, List<BatchCandidateResponse> reqList) throws MessagingException {
			// TODO Auto-generated method stub
			for(BatchCandidateResponse bcr : reqList) {
				System.out.println(clientId);
				Optional<User> userOpt = userRepository.findById(clientId);
				System.out.println(userOpt.get());
				Optional<Client> cliOpt = clientRepository.findById(userOpt.get().getClientId());
				CandidateDtpAccess dtp = new CandidateDtpAccess();
				dtp.setCandidateId(bcr.getId());
				dtp.setClientId(cliOpt.get().getId());
				dtp.setClientName(cliOpt.get().getCompanyName());
				dtp.setDtpStatus("Request Sent");
				candidateDtpAccessRepository.save(dtp);

				JobAssignedCandidates jac = new JobAssignedCandidates();
				jac.setClientId(clientId);
				jac.setCandidateId(bcr.getId());
				jac.setJobId(jobId);
				jac.setDtpAccessId(dtp.getId());
				jac.setActive(true);
				jac.setApplicationStatus("Initiated");
				jobAssignedCandidatesRepository.save(jac);
				
				ClientAssignedCandidates cand = new ClientAssignedCandidates();
				cand.setCandidateId(bcr.getId());
				cand.setClientId(clientId);
				cand.setAssigned(true);
				cand.setCreated(false);
				clientAssignedCandidatesRepository.save(cand);
				
				String body = "Hi " + userOpt.get().getUsername() + "\n"
						+ "\n"
						+ "client "+ cliOpt.get().getCompanyName() + " sents a request to see your Digital Talent Profile (DTP) results"
						+ "\n"
						+ "If you want to give Access " 
						+ "\n please login to your account and go authorize section and approve accesss"
						+ "\n \n"
						+ "Best regards,\n"
						+ "Ram Konduru";
				emailService.sendSimpleEmail(userOpt.get().getEmail(), "Request Access for DTP", body);	
			}
			
			return new ResponseEntity<>("saved", null, HttpStatus.OK);
		}
		
		
		public ResponseEntity<Object> addCandidateToJob(AssessmentBatchAddCandidateRequest req, long clientId, long jobId) throws MessagingException{
			System.out.println(req);
			User u = new User();
			u.setUsername(req.getCandidateName());
			u.setEmail(req.getCandidateEmail());
			u.setLinkedIn(req.getCandidateLinkedin());
			u.setMobile(req.getCandidateNo());
			Role r = roleRepository.findByName("CANDIDATE");
			Set s = new HashSet();
			s.add(r);
			u.setRoles(s);
			String password = utilityService.generatePassword(8);
			//u.setPassword(bCryptPasswordEncoder.encode(password));
			userRepository.save(u);
			
			Optional<User> userOpt = userRepository.findById(clientId);
			Optional<Client> cliOpt = clientRepository.findById(userOpt.get().getClientId());
			CandidateDtpAccess dtp = new CandidateDtpAccess();
			dtp.setCandidateId(u.getId());
			dtp.setClientId(cliOpt.get().getId());
			dtp.setClientName(cliOpt.get().getCompanyName());
			dtp.setDtpStatus("Request Sent");
			candidateDtpAccessRepository.save(dtp);
			
			System.out.println(jobId);
			if(jobId != 0) {
				JobAssignedCandidates jac = new JobAssignedCandidates();
				jac.setClientId(clientId);
				jac.setCandidateId(u.getId());
				jac.setJobId(jobId);
				jac.setDtpAccessId(dtp.getId());
				jobAssignedCandidatesRepository.save(jac);
			}
			
			ClientAssignedCandidates cand = new ClientAssignedCandidates();
			cand.setCandidateId(u.getId());
			cand.setClientId(clientId);
			cand.setAssigned(true);
			cand.setCreated(false);
			clientAssignedCandidatesRepository.save(cand);
			
			String body = "Hi " + userOpt.get().getUsername() + "\n"
					+ "\n"
					+ "client "+ cliOpt.get().getCompanyName() + " created your account and sents a request to complete the Digital Talent Profile (DTP) and give access to them"
					+ "\n"
					+ "" 
					+ "\n please login to your account with these credentials"
					+ " username : " + u.getUsername()
					+ " password : " + password
					+ "\n \n"
					+ "Best regards,\n"
					+ "Ram Konduru";
			emailService.sendSimpleEmail(u.getEmail(), "Request Access for DTP", body);	
			Map m = new HashMap();
			m.put("userId", u.getId());
			return new ResponseEntity<>( m, null, HttpStatus.OK);
			
		}

		public ResponseEntity<Object> getJobDescription(long clientId, long jobId) {
			// TODO Auto-generated method stub
			Optional<ClientJobDetails> cjdOpt = clientJobDetailsRepository.findById(jobId);
			JobDetailsRequest jobDetail = clientTemplateService.getJobTemplate(clientId, cjdOpt.get().getJobDetailId());
			ClientTeamTemplate team = clientTemplateService.getTeamTemplate(clientId, cjdOpt.get().getTeamId());
			List<Map> values = clientTemplateService.getValueTemplate(clientId, cjdOpt.get().getWorkValuesId());
			CandidateSpectrumResults icp = clientTemplateService.getICPResult(clientId, cjdOpt.get().getIcpId());
			JobDescriptionResponse resp = new JobDescriptionResponse();
			resp.setJobDetail(jobDetail);
			resp.setTeam(team);
			resp.setValues(values);
			resp.setIcp(icp);
			return new ResponseEntity<>(resp, null, HttpStatus.OK);
		}

		public ResponseEntity<Object> cloneJob(long clientId, long jobId) {
			// TODO Auto-generated method stub
			Optional<ClientJobDetails> jobOpt = clientJobDetailsRepository.findById(jobId);
			ClientJobDetails sourceJob = jobOpt.get();
			ClientJobDetails newJob = new ClientJobDetails();
			newJob.setClientId(clientId);
			newJob.setIcp(sourceJob.isIcp());
			newJob.setIcpId(sourceJob.getIcpId());
			newJob.setJobDetail(sourceJob.isJobDetail());
			newJob.setJobDetailId(sourceJob.getJobDetailId());
			newJob.setTeam(sourceJob.isTeam());
			newJob.setTeamId(sourceJob.getTeamId());
			newJob.setWorkValues(sourceJob.isWorkValues());
			newJob.setWorkValuesId(sourceJob.getWorkValuesId());
			clientJobDetailsRepository.save(newJob);
			return getAllJobsofClient(clientId, 1, 5);
		}
		
		
		public ResponseEntity<Object> deleteJob(long clientId, long jobId){
			Optional<ClientJobDetails> jobOpt = clientJobDetailsRepository.findById(jobId);
			clientJobDetailsRepository.delete(jobOpt.get());
			return getAllJobsofClient(clientId, 1, 5);
		}

		public ResponseEntity<Object> getFilterJobs(long clientId, String filterValue) {
			int pageNo = 0;
			int pageSize=10;
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
			Page<ClientJobDetails> pagedResult = null;
			if(filterValue.equals("Active")){
				pagedResult = clientJobDetailsRepository.findAllByClientIdAndActive(clientId, true, paging);
			}
			if(filterValue.equals("All")){
				pagedResult = clientJobDetailsRepository.findAllByClientId(clientId, paging);
			}
			if(filterValue.equals("Closed")){
				pagedResult = clientJobDetailsRepository.findAllByClientIdAndClosed(clientId, true, paging);
			}
			if(filterValue.equals("90Days")){
				LocalDateTime now = LocalDateTime.now();
		        LocalDateTime ninetyDaysBack = now.minusDays(90);
				pagedResult = clientJobDetailsRepository.findAllByClientIdAndCreatedAtAfter90Days(clientId, ninetyDaysBack, paging);
			}
			if(filterValue.equals("365Days")){
				LocalDateTime now = LocalDateTime.now();
		        LocalDateTime yearBack = now.minusYears(1);
				pagedResult = clientJobDetailsRepository.findAllByClientIdAndCreatedAtAfter365Days(clientId, yearBack, paging);
			}
			List<JobDetailsResponse> respList = new ArrayList();
			Optional<User> userOpt = userRepository.findById(clientId);
			Optional<Client> cliOpt = clientRepository.findById(userOpt.get().getClientId());
			for(ClientJobDetails details : pagedResult.getContent()) {
				Optional<JobTemplate> jtOpt = jobTemplateRepository.findById(details.getJobDetailId());
				JobTemplate jt = jtOpt.get();
				JobDetailsResponse resp = new JobDetailsResponse();
				resp.setId(details.getId());
				resp.setApplicants(pageSize);
				resp.setCompanyName(cliOpt.get().getCompanyName());
				resp.setCompensation(jt.getSalary());
				resp.setHiringManager(cliOpt.get().getName());
				resp.setImage("https://picsum.photos/200");
				resp.setJobName(jt.getJobTitle());
				resp.setJobProgress(getJobProgress(details));
				resp.setLocation(jt.getJobLocation());
				resp.setPostedTime(getJobElapsedTime(details.getCreatedAt()));
				respList.add(resp);
			}
			PaginatedResponse paginationResponse = PaginatedResponse.builder().data(respList)
					.pageNo(pagedResult.getNumber() + 1).pageSize(pageSize)
					.totalCount(pagedResult.getTotalElements()).message("found").result("success")
					.build();
			
			return new ResponseEntity<>(paginationResponse, null, HttpStatus.OK);

		}

//		public ResponseEntity<Object> updateScreening(long clientId, long jobId, boolean screening) {
//			// TODO Auto-generated method stub
//			Optional<ClientJobDetails> cjdOpt = clientJobDetailsRepository.findById(jobId);
//			ClientJobDetails cjd = cjdOpt.get();
//			cjd.setScreening(screening);
//			clientJobDetailsRepository.save(cjd);
//			return new ResponseEntity<>("updated", null, HttpStatus.OK);
//		}
		
		
		public ResponseEntity<Object> updateAssessment(long clientId, long jobId, String switchType, boolean value) {
			// TODO Auto-generated method stub
			Optional<ClientJobDetails> cjdOpt = clientJobDetailsRepository.findById(jobId);
			ClientJobDetails cjd = cjdOpt.get();
			if(switchType.equals("screening")) cjd.setScreening(value);
			if(switchType.equals("assessment")) cjd.setAssessment(value);
			if(switchType.equals("sourcing")) cjd.setSourcingHelp(value);
			if(switchType.equals("onboarding")) cjd.setOnboardingHelp(value);
			if(switchType.equals("serviceStaffing")) { 
				cjd.setFullServiceStaffingHelp(value);
				cjd.setSourcingHelp(false);
				cjd.setOnboardingHelp(false);
			}
			if(switchType.equals("publishFeature")) cjd.setPublishFeature(value);
			clientJobDetailsRepository.save(cjd);
			return new ResponseEntity<>("updated", null, HttpStatus.OK);
		}

//		public ResponseEntity<Object> saveScreeningQuestions(long clientId, long jobId, ClientScreeningQuestionsRequest req) {
//			// TODO Auto-generated method stub
//			List<ScreeningQuestions> questionsList = screeningQuestionsRepository.findByJobId(jobId);
//			int i = 0;
//			if(questionsList.isEmpty() == false) {
//				for(ScreeningQuestions question : questionsList) {
//					question.setQuestion(req.getQuestions().get(i));
//					screeningQuestionsRepository.save(question);
//					i++;
//				}
//			}
//			else {
//				for(String ques : req.getQuestions()) {
//					ScreeningQuestions sq = new ScreeningQuestions();
//					sq.setClientId(clientId);
//					sq.setJobId(jobId);
//					sq.setQuestion(ques);
//					screeningQuestionsRepository.save(sq);
//				}
//			}
//			if(req.isProceed()) {
//				Optional<ClientJobDetails> cjdOpt = clientJobDetailsRepository.findById(jobId);
//				ClientJobDetails cjd = cjdOpt.get();
//				cjd.setScreeningQuestions(false);
//			}
//			return new ResponseEntity<>("saved", null, HttpStatus.OK);
//		}

		public ResponseEntity<Object> createJob(JobTitleRequest req) {
			// TODO Auto-generated method stub
			
			ClientJobDetails job = new ClientJobDetails();
			job.setClientId(req.getClientId());
			job.setJobTitle(req.getJobTitle());
			job.setJobStatus("New Requirement");
			clientJobDetailsRepository.save(job);
			Optional<User> uOpt = userRepository.findById(req.getClientId());
			Optional<Client> c = clientRepository.findById(uOpt.get().getClientId());
				ClientJobInfoResponse resp = new ClientJobInfoResponse();
				resp.setClientId(job.getClientId());
				resp.setId(job.getId());
				resp.setIcp(job.isIcp());
				resp.setIcpId(job.getIcpId());
				resp.setJobDetail(job.isJobDetail());
				resp.setJobDetailId(job.getJobDetailId());
				resp.setTeam(job.isTeam());
				resp.setTeamId(job.getTeamId());
				resp.setScreening(job.isScreening());
				resp.setAssessment(job.isAssessment());
				resp.setJobTitle(job.getJobTitle());
				resp.setWorkValues(job.isWorkValues());
				resp.setWorkValuesId(job.getWorkValuesId());
				resp.setCompanyName(c.get().getCompanyName());
				resp.setJobStatus(job.getJobStatus());
				return new ResponseEntity<>(resp, null, HttpStatus.OK);
			
		}
		
		
		public ResponseEntity<Object> getJD(long clientId, long jobId){
			Optional<ClientJobDetails> cjdOpt = clientJobDetailsRepository.findById(jobId);
//			Optional<JobTemplate> jtOpt = jobTemplateRepository.findById(cjdOpt.get().getJobDetailId());
			return new ResponseEntity<>(cjdOpt.get().getJobTitle(), null, HttpStatus.OK);
		}

		public ResponseEntity<Object> getExternalStaffingJobs(long clientId) {
			// TODO Auto-generated method stub
			List<ClientJobDetails> cjdList = clientJobDetailsRepository.findBySourcingHelpOrOnboardingHelpOrFullServiceStaffingHelp(true, true, true);
			List<ExternalStaffingResponse> respList = new ArrayList();
			//DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
			for(ClientJobDetails cjd : cjdList) {
				Optional<JobTemplate> jtOpt = jobTemplateRepository.findById(cjd.getJobDetailId());
				ExternalStaffingResponse resp = new ExternalStaffingResponse();
				resp.setJobId(cjd.getId());
				resp.setJobName(jtOpt.get().getJobTitle());
				resp.setDepartment(jtOpt.get().getJobDepartment());
				resp.setLocation(jtOpt.get().getJobLocation());		
				resp.setPostedDate(formatter1.format(cjd.getCreatedAt()));
				resp.setStatus("Opened");
				resp.setDeclineDate(formatter1.format(cjd.getCreatedAt().plusDays(10)));
				if(cjd.isSourcingHelp()) resp.setSourcing("sourcing");
				if(cjd.isOnboardingHelp()) resp.setOnboarding("onboarding");
				if(cjd.isFullServiceStaffingHelp()) resp.setExternalService("externalService");
				respList.add(resp);
			}
			return new ResponseEntity<>(respList, null, HttpStatus.OK);
		}

		public ResponseEntity<Object> updateJobStatus(long clientId, JobStatusRequest req) {
			// TODO Auto-generated method stub
			Optional<ClientJobDetails> cjdOpt = clientJobDetailsRepository.findById(req.getJobId());
			ClientJobDetails cjd = cjdOpt.get();
			if(req.getStatus().equals("New_Requirement")) cjd.setJobStatus("New Requirement");
			if(req.getStatus().equals("Sourcing_Candidates")) cjd.setJobStatus("Sourcing Candidates");
			if(req.getStatus().equals("Screening_&_Evaluation")) cjd.setJobStatus("Screening & Evaluation");
			if(req.getStatus().equals("Interviewing")) cjd.setJobStatus("Interviewing");
			if(req.getStatus().equals("Offer_Processing")) cjd.setJobStatus("Offer Processing");
			if(req.getStatus().equals("Placement_Confirmed")) cjd.setJobStatus("Placement Cofirmed");
			if(req.getStatus().equals("Onboarding")) cjd.setJobStatus("Onboarding");
			if(req.getStatus().equals("Closed_Successful")) cjd.setJobStatus("Closed Successful");
			if(req.getStatus().equals("Closed_Onhold")) cjd.setJobStatus("Closed Position Onhold");
			if(req.getStatus().equals("Closed_Unsuccessful")) cjd.setJobStatus("Closed UnSuccessful");
			clientJobDetailsRepository.save(cjd);
			return new ResponseEntity<>("saved", null, HttpStatus.OK);
		}

		public ResponseEntity<Object> updateCandidateStatus(long clientId, JobStatusRequest req) {
			// TODO Auto-generated method stub
			Optional<JobAssignedCandidates> jacOpt = jobAssignedCandidatesRepository.findById(req.getJobId());
			JobAssignedCandidates jac = jacOpt.get();
			if(req.getStatus().equals("Initiated")) jac.setApplicationStatus("Initiated");  
			if(req.getStatus().equals("In_Progress")) jac.setApplicationStatus("In Progress");
			if(req.getStatus().equals("Approved ")) jac.setApplicationStatus("Approved");
			if(req.getStatus().equals("Rejected")) jac.setApplicationStatus("Rejected");
			if(req.getStatus().equals("Sourced")) jac.setApplicationStatus("Sourced");
			if(req.getStatus().equals("Contracted")) jac.setApplicationStatus("Contracted");
			if(req.getStatus().equals("Screened")) jac.setApplicationStatus("Screened");
			if(req.getStatus().equals("Shortlisted")) jac.setApplicationStatus("Shortlisted");
			if(req.getStatus().equals("Hiring_Manager_Submission")) jac.setApplicationStatus("Hiring Manager Submission");
			if(req.getStatus().equals("Hiring_Manager_Approved")) jac.setApplicationStatus("Hiring Manager Approved");
			if(req.getStatus().equals("Hiring_Manager_Interview_Scheduled")) jac.setApplicationStatus("Hiring Manager Interview Scheduled");
			if(req.getStatus().equals("Interviewing")) jac.setApplicationStatus("Interviewing");
			if(req.getStatus().equals("Offer_Extended")) jac.setApplicationStatus("Offer Extended");
			if(req.getStatus().equals("Offer_Accepted")) jac.setApplicationStatus("Offer Accepted");
			if(req.getStatus().equals("Onboarding")) jac.setApplicationStatus("Onboarding");
			if(req.getStatus().equals("Placed")) jac.setApplicationStatus("Placed");
			if(req.getStatus().equals("Closed-Not_Selected")) jac.setApplicationStatus("Closed - Not Selected");
			jobAssignedCandidatesRepository.save(jac);
			return new ResponseEntity<>("saved", null, HttpStatus.OK);
		}

		public ResponseEntity<Object> getAllClientAssignedCandidates(long clientId, int pageNo, int pageSize) {
			// TODO Auto-generated method stub
			pageNo = pageNo - 1;
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
			Page<ClientAssignedCandidates> pagedResult = clientAssignedCandidatesRepository.findAllByClientId(clientId, paging);
			List<ClientAssignedCandidatesResponse> respList = new ArrayList();
			for(ClientAssignedCandidates cand : pagedResult.getContent()) {
				Optional<User> uOpt = userRepository.findById(cand.getCandidateId());
				ClientAssignedCandidatesResponse resp =  new ClientAssignedCandidatesResponse();
				User u = uOpt.get();
				resp.setEmail(u.getEmail());
				resp.setLinkedIn(u.getLinkedIn());	
				resp.setName(u.getUsername());
				resp.setPhone(u.getMobile());
				resp.setResume(u.getResumeName());
				
				respList.add(resp);
			}
			
			PaginatedResponse paginationResponse = PaginatedResponse.builder().data(respList)
					.pageNo(pagedResult.getNumber() + 1).pageSize(pageSize)
					.totalCount(pagedResult.getTotalElements()).message("found").result("success")
					.build();
			
			
			return new ResponseEntity<>(paginationResponse, null, HttpStatus.OK);
		}

		public ResponseEntity<Object> getAllJobsForCandidates(long candidateId, int pageNo, int pageSize) {
			// TODO Auto-generated method stub
			pageNo = pageNo - 1;
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
			Page<ClientJobDetails> pagedResult = clientJobDetailsRepository.findAll(paging);
			List<JobDetailsResponse> respList = new ArrayList();
			for(ClientJobDetails details : pagedResult.getContent()) {
				Optional<User> userOpt = userRepository.findById(details.getClientId());
				Optional<Client> cliOpt = clientRepository.findById(userOpt.get().getClientId());
				Optional<JobTemplate> jtOpt = jobTemplateRepository.findById(details.getJobDetailId());
				JobTemplate jt = null;
				if(jtOpt.isPresent()) jt = jtOpt.get();
				List<JobAssignedCandidates> candidatesList = jobAssignedCandidatesRepository.findAllByJobId(details.getId());
				long newlyCount = candidatesList.stream()
			            .filter(obj -> ChronoUnit.DAYS.between(obj.getCreatedAt(), LocalDateTime.now()) < 2)
			            .collect(Collectors.counting());
				long activeCount = candidatesList.stream()
			            .filter(obj -> obj.isActive())
			            .collect(Collectors.counting());
				long hiredCount = candidatesList.stream()
			            .filter(obj -> obj.isHired())
			            .collect(Collectors.counting()); 
				JobDetailsResponse resp = new JobDetailsResponse();
				resp.setId(details	.getId());
				resp.setApplicants(pageSize);
				resp.setCompanyName(cliOpt.isPresent() ? cliOpt.get().getCompanyName() : "");
				resp.setCompensation(jt == null ? 0 : jt.getSalary());
				resp.setHiringManager(cliOpt.isPresent() ? cliOpt.get().getName() : "");
				resp.setImage("https://picsum.photos/200");
				resp.setJobName(details.getJobTitle());
				resp.setJobProgress(getJobProgress(details));
				resp.setLocation(jt == null ? "NO Location" : jt.getJobLocation());
				resp.setJobDepartment(jt == null ? "not defined" : jt.getJobDepartment());
				resp.setPostedTime(getJobElapsedTime(details.getCreatedAt()));
				resp.setJobStatus(details.getJobStatus());
				resp.setJobSubStatus(details.getJobSubStatus());	
				resp.setTotal(candidatesList.size());
				resp.setNewly(newlyCount);
				resp.setActive(activeCount);
				resp.setHired(hiredCount);
				resp.setCreatedDate(formatter1.format(details.getCreatedAt()));
				resp.setJobType(jt == null ? "not defined" : jt.getJobFamily());
				resp.setNewJob(ChronoUnit.DAYS.between(details.getCreatedAt(), LocalDateTime.now()) < 2);
				List<String> hireType = new ArrayList();
				hireType.add(jt == null ? "not defined" : jt.getRoleType());
				resp.setTypeOfHire(hireType);
				resp.setJobDescription(jt == null ? " " : jt.getJobDepartment());
				resp.setRequirement(jt == null ? " " : jt.getResponsibilities());
				resp.setResponsibility(jt == null ? " " : jt.getResponsibilities());
				respList.add(resp);
			}
			PaginatedResponse paginationResponse = PaginatedResponse.builder().data(respList)
					.pageNo(pagedResult.getNumber() + 1).pageSize(pageSize)
					.totalCount(pagedResult.getTotalElements()).message("found").result("success")
					.build();
			
			return new ResponseEntity<>(paginationResponse, null, HttpStatus.OK);
		}
		
		
		
		
		
		


}		

