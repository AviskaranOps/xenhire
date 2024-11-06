package xenhire.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import xenhire.constants.AppConstants;
import xenhire.model.CandidateJobStatus;
import xenhire.model.Client;
import xenhire.model.ClientJobDetails;
import xenhire.model.JobAssignedCandidates;
import xenhire.model.JobTemplate;
import xenhire.model.User;
import xenhire.model.UserActivities;
import xenhire.repository.CandidateJobStatusRepository;
import xenhire.repository.ClientJobDetailsRepository;
import xenhire.repository.ClientRepository;
import xenhire.repository.JobAssignedCandidatesRepository;
import xenhire.repository.JobTemplateRepository;
import xenhire.repository.UserActivitiesRepository;
import xenhire.repository.UserRepository;
import xenhire.request.AdminJobRequest;
import xenhire.response.AdminDashboardProperties;
import xenhire.response.CommonResponse;
import xenhire.response.JobApplicantsResponse;
import xenhire.response.JobDetailsResponse;
import xenhire.response.PaginatedResponse;

@Service
@Slf4j
public class AdminService {

	DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);

	@Autowired
	ClientJobDetailsRepository clientJobDetailsRepository;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	CandidateJobStatusRepository candidateJobStatusRepository;

	@Autowired
	UserActivitiesRepository userActivitiesRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	JobTemplateRepository jobTemplateRepository;

	@Autowired
	JobAssignedCandidatesRepository jobAssignedCandidatesRepository;

	@Autowired
	ClientJobService clientJobService;

	public ResponseEntity<Object> getJobsAndActiveClientsAndApplicants() {
		long totalJobs = clientJobDetailsRepository.count();
		long clients = clientRepository.countByActive(true);
		long applicants = candidateJobStatusRepository.countByActive(true);
		AdminDashboardProperties resp = AdminDashboardProperties.builder().jobs(totalJobs).clients(clients)
				.applicants(applicants).build();
		CommonResponse cr = CommonResponse.builder().data(resp).message(AppConstants.FOUND).result(AppConstants.SUCCESS)
				.build();
		return new ResponseEntity<>(cr, HttpStatus.OK);
	}

	public ResponseEntity<Object> getApplicationRecentActivities(int pageNo, int pageSize) {

		pageNo = pageNo - 1;
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("createdAt").ascending());
		Page<UserActivities> pagedResult = userActivitiesRepository.findAll(paging);
		PaginatedResponse pr = PaginatedResponse.builder().data(pagedResult.getContent()).message(AppConstants.FOUND)
				.pageNo(pageNo).pageSize(pageSize).totalCount(pagedResult.getTotalElements())
				.result(AppConstants.SUCCESS).build();
		return new ResponseEntity<>(pr, HttpStatus.OK);
	}

	public ResponseEntity<Object> saveOrUpdateClient(Client client) {
		if (client.getId() != 0) {
			Client cli = clientRepository.getById(client.getId());
			if (cli == null) {
				return new ResponseEntity<>(client, HttpStatus.NOT_FOUND);
			}
			client.setCreatedAt(cli.getCreatedAt());
			client.setUpdatedAt(cli.getUpdatedAt());
			BeanUtils.copyProperties(client, cli);
			clientRepository.save(cli);
		} else {
			clientRepository.save(client);
		}
		return new ResponseEntity<>(client, HttpStatus.OK);

	}

	public ResponseEntity<Object> getAllClients(int pageNo, int pageSize) {
		pageNo = pageNo - 1;
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("createdAt").ascending());
		Page<Client> pagedResult = clientRepository.findAll(paging);
		PaginatedResponse pr = PaginatedResponse.builder().data(pagedResult.getContent()).message(AppConstants.FOUND)
				.pageNo(pageNo).pageSize(pageSize).totalCount(pagedResult.getTotalElements())
				.result(AppConstants.SUCCESS).build();
		return new ResponseEntity<>(pr, HttpStatus.OK);
	}

	public ResponseEntity<Object> getAllJobs(int pageNo, int pageSize) {
		pageNo = pageNo - 1;
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
		Page<ClientJobDetails> pagedResult = clientJobDetailsRepository.findAll(paging);
		List<JobDetailsResponse> respList = getAllJobDetails(pagedResult);
		PaginatedResponse paginationResponse = PaginatedResponse.builder().data(respList)
				.pageNo(pagedResult.getNumber() + 1).pageSize(pageSize).totalCount(pagedResult.getTotalElements())
				.message("found").result("success").build();

		return new ResponseEntity<>(paginationResponse, null, HttpStatus.OK);

	}

	public ResponseEntity<Object> getAllJobsByFilter(int pageNo, int pageSize, String filter) {
		pageNo = pageNo - 1;
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
		Page<ClientJobDetails> pagedResult = null;
		if (filter.equals("sourcingHelp"))
			pagedResult = clientJobDetailsRepository.findAllBySourcingHelp(paging, true);
		if (filter.equals("onboardHelp"))
			pagedResult = clientJobDetailsRepository.findAllByOnboardingHelp(paging, true);
		if (filter.equals("fullServiceHelp"))
			pagedResult = clientJobDetailsRepository.findAllByFullServiceStaffingHelp(paging, true);
		List<JobDetailsResponse> respList = getAllJobDetails(pagedResult);
		PaginatedResponse paginationResponse = PaginatedResponse.builder().data(respList)
				.pageNo(pagedResult.getNumber() + 1).pageSize(pageSize).totalCount(pagedResult.getTotalElements())
				.message("found").result("success").build();

		return new ResponseEntity<>(paginationResponse, null, HttpStatus.OK);

	}

	public ResponseEntity<Object> getAllApplicants(int pageNo, int pageSize) {
		pageNo = pageNo - 1;
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
		Page<CandidateJobStatus> pagedResult = candidateJobStatusRepository.findAll(paging);
		List<JobApplicantsResponse> respList = new ArrayList();
		for (CandidateJobStatus cjs : pagedResult.getContent()) {
			Optional<ClientJobDetails> jobDetail = clientJobDetailsRepository.findById(cjs.getJobId());
			Optional<User> user = userRepository.findById(cjs.getCandidateId());
			JobApplicantsResponse resp = JobApplicantsResponse.builder().applicantName(user.get().getUsername())
					.emailAddress(user.get().getEmail()).jobTitle(jobDetail.get().getJobTitle())
					.phoneNumber(user.get().getMobile()).status(cjs.getPhaseStatus()).build();
			respList.add(resp);

		}
		PaginatedResponse paginationResponse = PaginatedResponse.builder().data(respList)
				.pageNo(pagedResult.getNumber() + 1).pageSize(pageSize).totalCount(pagedResult.getTotalElements())
				.message("found").result("success").build();

		return new ResponseEntity<>(paginationResponse, null, HttpStatus.OK);
	}

	public ResponseEntity<Object> saveJob(AdminJobRequest req) {
		ClientJobDetails job = new ClientJobDetails();
		job.setJobTitle(req.getJobName());
		job.setDepartment(req.getDepartment());
		Client client = clientRepository.findByCompanyName(req.getClientName());
		job.setClientId(client.getId());
		job.setTotalPositions(req.getTotalPositions());
		job.setJobTitle(req.getJobType());
		job.setLocation(req.getLocation());
		clientJobDetailsRepository.save(job);
		return new ResponseEntity<>(job, HttpStatus.OK);
	}

	public List<JobDetailsResponse> getAllJobDetails(Page<ClientJobDetails> pagedResult) {
		List<JobDetailsResponse> respList = new ArrayList();
		for (ClientJobDetails details : pagedResult.getContent()) {
			Optional<User> userOpt = userRepository.findById(details.getClientId());
			Optional<Client> cliOpt = clientRepository.findById(userOpt.get().getClientId());
			Optional<JobTemplate> jtOpt = jobTemplateRepository.findById(details.getJobDetailId());
			JobTemplate jt = null;
			if (jtOpt.isPresent())
				jt = jtOpt.get();
			List<JobAssignedCandidates> candidatesList = jobAssignedCandidatesRepository
					.findAllByJobId(details.getId());
			long newlyCount = candidatesList.stream()
					.filter(obj -> ChronoUnit.DAYS.between(obj.getCreatedAt(), LocalDateTime.now()) < 2)
					.collect(Collectors.counting());
			long activeCount = candidatesList.stream().filter(obj -> obj.isActive()).collect(Collectors.counting());
			long hiredCount = candidatesList.stream().filter(obj -> obj.isHired()).collect(Collectors.counting());
			int applicants = candidateJobStatusRepository.countByJobId(details.getId());
			JobDetailsResponse resp = new JobDetailsResponse();
			resp.setId(details.getId());
			resp.setApplicants(applicants);
			resp.setCompanyName(cliOpt.isPresent() ? cliOpt.get().getCompanyName() : "");
			resp.setCompensation(jt == null ? 0 : jt.getSalary());
			resp.setHiringManager(cliOpt.isPresent() ? cliOpt.get().getName() : "");
			resp.setImage("https://picsum.photos/200");
			resp.setJobName(details.getJobTitle());
			resp.setJobProgress(clientJobService.getJobProgress(details));
			resp.setLocation(jt == null ? "NO Location" : jt.getJobLocation());
			resp.setJobDepartment(jt == null ? "not defined" : jt.getJobDepartment());
			resp.setPostedTime(clientJobService.getJobElapsedTime(details.getCreatedAt()));
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

		return respList;
	}

	public List<User> getAllUsers(Long userId) {
		List<User> users = userRepository.findAll();
		Optional<User> user = users.stream().filter(usr->usr.getId()==userId).findFirst();
		AtomicReference<List<User>> usersList = new AtomicReference<List<User>>();
		user.ifPresentOrElse((data) -> {
			List<String> roles = data.getRoles().stream().map(t -> t.getName()).toList();
			if (roles.contains("ADMIN")) {
				usersList.set(users);
			} else if (roles.contains("CLIENT")) {

				Optional<List<User>> filterList = Optional.ofNullable(users.stream().filter(t -> {
					List<String> clientRoles = t.getRoles().stream().map(role -> role.getName()).toList();
					if (clientRoles.contains("ADMIN")) {
						return false;
					} else {
						return true;
					}
				}).toList());

				usersList.set(filterList.get());
			} else {
				usersList.set(null);
			}
		}, () -> usersList.set(null));

		return usersList.get();
	}

}
